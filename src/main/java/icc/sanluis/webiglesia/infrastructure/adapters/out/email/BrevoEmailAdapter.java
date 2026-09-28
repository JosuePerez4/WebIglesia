package icc.sanluis.webiglesia.infrastructure.adapters.out.email;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.ports.out.EmailServicePort;
import tools.jackson.databind.ObjectMapper;

@Component
public class BrevoEmailAdapter implements EmailServicePort {

    private static final Logger log = LoggerFactory.getLogger(BrevoEmailAdapter.class);
    private static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(10);
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);
    private static final int MAX_ERROR_BODY_LENGTH = 500;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String apiUrl;
    private final String apiKey;
    private final String frontendUrl;
    private final String fromEmail;
    private final String fromName;

    public BrevoEmailAdapter(ObjectMapper objectMapper,
                             @Value("${brevo.api-url}") String apiUrl,
                             @Value("${brevo.api-key}") String apiKey,
                             @Value("${frontend.url:http://localhost:5173}") String frontendUrl,
                             @Value("${mail.from}") String fromEmail,
                             @Value("${mail.from-name:ICC San Luis}") String fromName) {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(CONNECT_TIMEOUT)
                .build();
        this.objectMapper = objectMapper;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.frontendUrl = frontendUrl;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
    }

    @Override
    public void enviarCorreoRecuperacion(String correoDestino, String nombreUsuario, String token) {
        String resetUrl = frontendUrl + "/restablecer-contrasena?token=" + token;
        String htmlContent = buildHtmlContent(nombreUsuario, resetUrl);

        Map<String, Object> requestBody = Map.of(
                "htmlContent", htmlContent,
                "sender", Map.of("email", fromEmail, "name", fromName),
                "subject", "Recuperación de contraseña - ICC San Luis",
                "to", List.of(Map.of("email", correoDestino, "name", nombreUsuario)));

        try {
            String serializedBody = objectMapper.writeValueAsString(requestBody);
            HttpRequest request = HttpRequest.newBuilder(URI.create(apiUrl))
                    .timeout(REQUEST_TIMEOUT)
                    .header("api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(serializedBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                String responseText = response.body() == null ? "" : response.body();
                log.error("Brevo email request failed with status {}: {}",
                        response.statusCode(), bounded(responseText));
                throw new RuntimeException("Error al enviar el correo de recuperación");
            }

            log.info("Email de recuperación enviado a {} para usuario {}", correoDestino, nombreUsuario);
        } catch (IOException e) {
            log.error("Brevo email request failed for {}: {}", correoDestino, e.getMessage());
            throw new RuntimeException("Error al enviar el correo de recuperación", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Brevo email request interrupted for {}", correoDestino);
            throw new RuntimeException("Error al enviar el correo de recuperación", e);
        } catch (IllegalArgumentException e) {
            log.error("Invalid Brevo email configuration: {}", e.getMessage());
            throw new RuntimeException("Error al enviar el correo de recuperación", e);
        }
    }

    private String bounded(String value) {
        return value.length() <= MAX_ERROR_BODY_LENGTH
                ? value
                : value.substring(0, MAX_ERROR_BODY_LENGTH) + "...";
    }

    private String buildHtmlContent(String nombreUsuario, String resetUrl) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                </head>
                <body style="margin: 0; padding: 0; background-color: #f3f0f7; font-family: Arial, Helvetica, sans-serif;">
                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f3f0f7; padding: 20px 0;">
                        <tr>
                            <td align="center">
                                <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 12px rgba(128, 90, 160, 0.10);">
                                    <tr><td style="background: linear-gradient(135deg, #9b72cf 0%%, #7c5cbf 100%%); padding: 28px 30px; text-align: center;"><h1 style="margin: 0; color: #ffffff; font-size: 22px; font-weight: 600; letter-spacing: 0.5px;">ICC San Luis</h1></td></tr>
                                    <tr><td style="padding: 35px 40px;"><h2 style="margin: 0 0 18px 0; color: #4a3560; font-size: 20px;">Recuperación de contraseña</h2><p style="margin: 0 0 14px 0; color: #444; font-size: 15px; line-height: 1.6;">Hola <strong style="color: #5a3d7a;">%s</strong>,</p><p style="margin: 0 0 24px 0; color: #444; font-size: 15px; line-height: 1.6;">Recibimos una solicitud para restablecer tu contraseña. Hacé clic en el siguiente botón para crear una nueva contraseña:</p><table cellpadding="0" cellspacing="0" style="margin: 0 auto 24px auto;"><tr><td align="center" style="background-color: #9b72cf; border-radius: 8px;"><a href="%s" target="_blank" style="display: inline-block; padding: 14px 36px; color: #ffffff; font-size: 16px; font-weight: bold; text-decoration: none; letter-spacing: 0.3px;">Restablecer contraseña</a></td></tr></table><table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f9f5fc; border: 1px solid #d4b8e8; border-radius: 8px; margin-bottom: 20px;"><tr><td style="padding: 14px 18px; font-size: 14px; color: #5a3d7a;"><strong>Este enlace expira en 1 hora.</strong></td></tr></table><p style="margin: 0 0 12px 0; color: #666; font-size: 14px; line-height: 1.6;">Si no solicitaste este cambio, podés ignorar este mensaje. Tu contraseña actual seguirá siendo la misma.</p><p style="margin: 0 0 8px 0; color: #666; font-size: 14px;">Si el botón no funciona, copiá y pegá este enlace en tu navegador:</p><p style="margin: 0; word-break: break-all; font-size: 12px; color: #9b72cf;">%s</p></td></tr>
                                    <tr><td style="background-color: #f9f5fc; padding: 18px 30px; text-align: center; border-top: 1px solid #e8ddf3;"><p style="margin: 0 0 4px 0; font-size: 12px; color: #8a7a9a;">Este es un correo automático, por favor no responder.</p><p style="margin: 0; font-size: 12px; color: #8a7a9a;">ICC San Luis - Sistema de Gestión</p></td></tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(nombreUsuario, resetUrl, resetUrl);
    }
}
