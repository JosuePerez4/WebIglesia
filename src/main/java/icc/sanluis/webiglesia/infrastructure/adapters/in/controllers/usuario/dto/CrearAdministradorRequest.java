package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CrearAdministradorRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El apellido es obligatorio") String apellido,
        String telefono,
        LocalDate fechaDeNacimiento,
        @Email(message = "El correo debe ser válido") String correo,
        @NotBlank(message = "El nombre de usuario es obligatorio") String username,
        @NotBlank(message = "La contraseña es obligatoria") String contrasena
) {
}
