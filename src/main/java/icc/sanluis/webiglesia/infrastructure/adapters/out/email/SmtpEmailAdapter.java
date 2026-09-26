package icc.sanluis.webiglesia.infrastructure.adapters.out.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import icc.sanluis.webiglesia.domain.usuario.ports.out.EmailServicePort;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class SmtpEmailAdapter implements EmailServicePort {

    private static final Logger log = LoggerFactory.getLogger(SmtpEmailAdapter.class);

    private final JavaMailSender mailSender;

    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Value("${mail.from}")
    private String fromEmail;

    public SmtpEmailAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void enviarCorreoRecuperacion(String correoDestino, String nombreUsuario, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(correoDestino);
            helper.setSubject("Recuperación de contraseña - ICC San Luis");

            String resetUrl = frontendUrl + "/restablecer-contrasena?token=" + token;

            String htmlContent = """
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
                                    <!-- Header -->
                                    <tr>
                                        <td style="background: linear-gradient(135deg, #9b72cf 0%%, #7c5cbf 100%%); padding: 28px 30px; text-align: center;">
                                            <h1 style="margin: 0; color: #ffffff; font-size: 22px; font-weight: 600; letter-spacing: 0.5px;">ICC San Luis</h1>
                                        </td>
                                    </tr>
                                    <!-- Content -->
                                    <tr>
                                        <td style="padding: 35px 40px;">
                                            <h2 style="margin: 0 0 18px 0; color: #4a3560; font-size: 20px;">Recuperación de contraseña</h2>
                                            <p style="margin: 0 0 14px 0; color: #444; font-size: 15px; line-height: 1.6;">Hola <strong style="color: #5a3d7a;">%s</strong>,</p>
                                            <p style="margin: 0 0 24px 0; color: #444; font-size: 15px; line-height: 1.6;">Recibimos una solicitud para restablecer tu contraseña. Hacé clic en el siguiente botón para crear una nueva contraseña:</p>
                                            <!-- Button -->
                                            <table cellpadding="0" cellspacing="0" style="margin: 0 auto 24px auto;">
                                                <tr>
                                                    <td align="center" style="background-color: #9b72cf; border-radius: 8px;">
                                                        <a href="%s" target="_blank" style="display: inline-block; padding: 14px 36px; color: #ffffff; font-size: 16px; font-weight: bold; text-decoration: none; letter-spacing: 0.3px;">Restablecer contraseña</a>
                                                    </td>
                                                </tr>
                                            </table>
                                            <!-- Warning -->
                                            <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f9f5fc; border: 1px solid #d4b8e8; border-radius: 8px; margin-bottom: 20px;">
                                                <tr>
                                                    <td style="padding: 14px 18px; font-size: 14px; color: #5a3d7a;">
                                                        <strong>Este enlace expira en 1 hora.</strong>
                                                    </td>
                                                </tr>
                                            </table>
                                            <p style="margin: 0 0 12px 0; color: #666; font-size: 14px; line-height: 1.6;">Si no solicitaste este cambio, podés ignorar este mensaje. Tu contraseña actual seguirá siendo la misma.</p>
                                            <p style="margin: 0 0 8px 0; color: #666; font-size: 14px;">Si el botón no funciona, copiá y pegá este enlace en tu navegador:</p>
                                            <p style="margin: 0; word-break: break-all; font-size: 12px; color: #9b72cf;">%s</p>
                                        </td>
                                    </tr>
                                    <!-- Footer -->
                                    <tr>
                                        <td style="background-color: #f9f5fc; padding: 18px 30px; text-align: center; border-top: 1px solid #e8ddf3;">
                                            <p style="margin: 0 0 4px 0; font-size: 12px; color: #8a7a9a;">Este es un correo automático, por favor no responder.</p>
                                            <p style="margin: 0; font-size: 12px; color: #8a7a9a;">ICC San Luis - Sistema de Gestión</p>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(nombreUsuario, resetUrl, resetUrl);

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email de recuperación enviado a {} para usuario {}", correoDestino, nombreUsuario);

        } catch (MessagingException e) {
            log.error("Error al enviar email de recuperación a {}: {}", correoDestino, e.getMessage(), e);
            throw new RuntimeException("Error al enviar el correo de recuperación", e);
        }
    }
}
