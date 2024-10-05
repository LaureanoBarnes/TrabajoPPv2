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

    private final String url = "upload/";

    public String saveUpload(MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            byte [] bytes = file.getBytes();
            // Limpiar el nombre del archivo
            String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
            String sanitizedFileName = sanitizeFileName(originalFileName);
            // Codificar el nombre del archivo
            String encodedFileName = URLEncoder.encode(sanitizedFileName, StandardCharsets.UTF_8);

            Path path = Paths.get(url + encodedFileName);
            Files.write(path, bytes);
            return encodedFileName;
        }
        return null;
    }

    public void deleteUpload(String nombre) throws IOException{
        File file = new File(url + nombre);
        file.delete();
    }
}