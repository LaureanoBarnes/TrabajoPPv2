package holaSpringData.servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class EntregaArchivoService {

    private final String BASE_URL = "upload/";

    // Método para limpiar el nombre del archivo
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.]", "_");
    }

    // Guardar el archivo entregado por el estudiante
    public String saveUpload(MultipartFile file, Long idAula, Long idActividad) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
            String sanitizedFileName = sanitizeFileName(originalFileName);

            // Crear directorio específico para las entregas si no existe
            Path entregaDir = Paths.get(BASE_URL + idAula + "/actividades/" + idActividad + "/entregas/");
            if (!Files.exists(entregaDir)) {
                Files.createDirectories(entregaDir);
            }

            Path filePath = entregaDir.resolve(sanitizedFileName);
            Files.write(filePath, bytes);
            System.out.println("Archivo guardado en: " + filePath.toAbsolutePath());

            // Devolver la ruta relativa donde se ha guardado el archivo
            return idAula + "/actividades/" + idActividad + "/entregas/" + sanitizedFileName;
        }
        return null;
    }

    // Eliminar un archivo entregado
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
