package holaSpringData.servicio;

import holaSpringData.clases.Archivos;
import holaSpringData.dao.ArchivoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfServiceImplement implements PdfService {

    @Autowired
    private ArchivoDao archivodao;
    @Autowired
    private UploadService uploadService;

    String url = "/upload/";

    @Override
    public Archivos save(Archivos archivos, MultipartFile file, Long idAula) throws IOException {
        String filePath = uploadService.saveUpload(file, idAula);
        archivos.setNombre(filePath);
        return archivodao.save(archivos);
    }

    @Override
    public List<Archivos> getArchivos(Long idAula) {
        List<Archivos> archivos = archivodao.findAll();
        archivos = archivos.stream()
                .filter(archivo -> archivo.getNombre().startsWith(idAula + "/"))
                .map(archivo -> {
                    archivo.setNombre(url + archivo.getNombre());
                    return archivo;
                })
                .collect(Collectors.toList());
        return archivos;
    }

    @Override
    public Archivos get(Long id) {
        Archivos archivos = archivodao.findById(id).get();
        archivos.setNombre(url + archivos.getNombre());
        return archivos;
    }

    @Override
    public Archivos update(Long id, Archivos archivos, MultipartFile file, Long idAula) throws IOException {
        archivos.setId(id);
        Archivos oldArchivo = archivodao.findById(id).get();
        String filePath = uploadService.saveUpload(file, idAula);
        if (!oldArchivo.getNombre().equals(filePath)) {
            uploadService.deleteUpload(oldArchivo.getNombre());
        }
        archivos.setNombre(filePath);
        return archivodao.save(archivos);
    }

    @Override
    public void delete(Long id) throws IOException {
        Archivos archivos = archivodao.findById(id).get();
        uploadService.deleteUpload(archivos.getNombre());
        archivodao.delete(archivos);
    }
}
