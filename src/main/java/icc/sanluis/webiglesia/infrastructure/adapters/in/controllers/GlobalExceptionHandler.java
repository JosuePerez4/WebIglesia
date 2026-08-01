package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import icc.sanluis.webiglesia.domain.usuario.exceptions.EstudianteNoPuedeIniciarSesionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public record ErrorResponse(String message, Map<String, String> errors) {
        static ErrorResponse of(String message) {
            return new ErrorResponse(message, Map.of());
        }
    }

    // Validaciones @Valid fallidas: 400 con el detalle por campo para que el frontend lo muestre en los formularios.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(new ErrorResponse("Datos inválidos", errores));
    }

    // Reglas de negocio violadas (nombre obligatorio, entidad no encontrada, etc.).
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ErrorResponse.of(ex.getMessage()));
    }

    // El estudiante no tiene acceso a inicio de sesión: regla de negocio, no un error interno.
    @ExceptionHandler(EstudianteNoPuedeIniciarSesionException.class)
    public ResponseEntity<ErrorResponse> handleEstudianteLogin(EstudianteNoPuedeIniciarSesionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.of(ex.getMessage()));
    }

    // Catch-all: nunca exponer el mensaje interno al cliente, solo loguearlo.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        log.error("Error no controlado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of("Error interno del servidor"));
    }
}
