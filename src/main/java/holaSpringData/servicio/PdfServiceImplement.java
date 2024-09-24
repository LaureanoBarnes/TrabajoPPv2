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

    String url = "http://localhost:8080/upload/";

    @Override
    public Archivos save(Archivos archivos, MultipartFile file) throws IOException {
        String nombre = uploadService.saveUpload(file);
        archivos.setNombre(nombre);
        return archivodao.save(archivos);
    }

    @Override
    public List<Archivos> getArchivos() {
        List <Archivos> archivos = archivodao.findAll();
        archivos = archivos.stream().map(archivo -> {archivo.setNombre(url + archivo.getNombre());
        return archivo;
        }).collect(Collectors.toList());

        return archivos;
    }

    @Override
    public Archivos get(Long id) {
        Archivos archivos = archivodao.findById(id).get();
        archivos.setNombre(url + archivos.getNombre());
        return archivos;
    }

    @Override
    public Archivos update(Long id, Archivos archivos, MultipartFile file) throws IOException {
        archivos.setId(id);
        Archivos archivos1 = archivodao.findById(id).get();
        String nombre = uploadService.saveUpload(file);
        if (!archivos1.getNombre().equals(nombre)){
            uploadService.deleteUpload(archivos1.getNombre());
        }
        archivos.setId(id);
        archivos.setNombre(nombre);
        return archivodao.save(archivos);
    }

    @Override
    public void delete(Long id) throws IOException {

        Archivos archivos = archivodao.findById(id).get();
        String nombre = archivos.getNombre();
        uploadService.deleteUpload(nombre);
        archivodao.delete(archivos);
    }
}
