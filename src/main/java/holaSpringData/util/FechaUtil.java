package holaSpringData.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaUtil {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String formatearFecha(LocalDateTime fecha) {
        if (fecha != null) {
            return fecha.format(FORMATO_FECHA);
        }
        return "";
    }
}
