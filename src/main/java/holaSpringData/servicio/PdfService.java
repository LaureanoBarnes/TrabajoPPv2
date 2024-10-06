package holaSpringData.servicio;

import holaSpringData.clases.Archivos;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PdfService {

    Archivos save(Archivos archivos, MultipartFile file, Long idAula) throws IOException;

    List<Archivos> getArchivos(Long idAula);

    Archivos get(Long id);

    Archivos update(Long id, Archivos archivos, MultipartFile file, Long idAula) throws IOException;

    void delete(Long id) throws IOException;
}
