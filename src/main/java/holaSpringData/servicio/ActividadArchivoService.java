package holaSpringData.servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class ActividadArchivoService {

    private final String BASE_URL = "upload/";

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.]", "_");
    }

    public String saveUpload(MultipartFile file, Long idAula) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
            String sanitizedFileName = sanitizeFileName(originalFileName);

            // Crear directorio específico para las actividades si no existe
            Path aulaDir = Paths.get(BASE_URL + idAula + "/actividades/");
            if (!Files.exists(aulaDir)) {
                Files.createDirectories(aulaDir);
            }

            Path filePath = aulaDir.resolve(sanitizedFileName);
            Files.write(filePath, bytes);
            System.out.println("Archivo guardado en: " + filePath.toAbsolutePath());
            return idAula + "/actividades/" + sanitizedFileName; // Devolver la ruta relativa
        }
        return null;
    }

    public void deleteUpload(String filePath) throws IOException {
        Path path = Paths.get(BASE_URL + filePath);
        System.out.println("Intentando eliminar el archivo: " + path.toAbsolutePath());

        if (Files.exists(path)) {
            System.out.println("Archivo encontrado, eliminando: " + path.toAbsolutePath());
            Files.delete(path);  // Elimina el archivo
        } else {
            System.out.println("Archivo no encontrado, no se puede eliminar: " + path.toAbsolutePath());
        }
    }
}
