package holaSpringData.web;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class YourController {

    // Otros métodos de tu controlador

    public String formatFecha(LocalDateTime fecha) {
        if (fecha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return fecha.format(formatter);
        }
        return "Fecha no disponible";
    }

}