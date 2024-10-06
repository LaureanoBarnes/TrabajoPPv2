package holaSpringData.servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class UploadService {

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.]", "_");
    }

    private final String BASE_URL = "upload/";

    public String saveUpload(MultipartFile file, Long idAula) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
            String sanitizedFileName = sanitizeFileName(originalFileName);
            String encodedFileName = URLEncoder.encode(sanitizedFileName, StandardCharsets.UTF_8);

            // Crear directorio específico para el aula si no existe
            Path aulaDir = Paths.get(BASE_URL + idAula);
            if (!Files.exists(aulaDir)) {
                Files.createDirectories(aulaDir);
            }

            Path filePath = aulaDir.resolve(encodedFileName);
            Files.write(filePath, bytes);
            return idAula + "/" + encodedFileName; // Devolver la ruta relativa
        }
        return null;
    }

    public void deleteUpload(String filePath) throws IOException {
        File file = new File(BASE_URL + filePath);
        file.delete();
    }
}
