package icc.sanluis.webiglesia.application.usuario.services;

import java.time.LocalDate;
import java.util.UUID;

public final class UsernameGenerator {

    private UsernameGenerator() {
    }

    public static String generar(String nombre, LocalDate fechaDeNacimiento, UUID id) {
        String base = normalizar(nombre);
        if (fechaDeNacimiento != null) {
            return base + fechaDeNacimiento.getDayOfMonth() + fechaDeNacimiento.getMonthValue();
        }
        // Sin fecha de nacimiento no hay sufijo natural: se usa parte del id para evitar choques de username.
        return base + id.toString().substring(0, 6);
    }

    private static String normalizar(String nombre) {
        return nombre.trim().toLowerCase()
                .replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u")
                .replaceAll("[^a-z0-9]", "");
    }
}
