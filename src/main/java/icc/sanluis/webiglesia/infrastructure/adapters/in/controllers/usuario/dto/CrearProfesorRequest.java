package icc.sanluis.webiglesia.infrastructure.adapters.in.controllers.usuario.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearProfesorRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El apellido es obligatorio") String apellido,
        @NotBlank(message = "El teléfono es obligatorio") String telefono,
        @NotNull(message = "La fecha de nacimiento es obligatoria") LocalDate fechaDeNacimiento,
        @NotBlank(message = "El correo es obligatorio") @Email(message = "El correo debe ser válido") String correo
) {
}
