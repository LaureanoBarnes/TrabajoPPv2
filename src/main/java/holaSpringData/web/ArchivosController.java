package holaSpringData.web;

import holaSpringData.clases.Archivos;
import holaSpringData.servicio.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArchivosController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/archivo")
    public RedirectView saveArchivos(
            @RequestParam MultipartFile file,
            @RequestParam String categoria,
            @RequestParam Long aulaId,
            RedirectAttributes redirectAttributes) {

        try {
            Archivos archivo = new Archivos();
            archivo.setCategoria(categoria);
            pdfService.save(archivo, file, aulaId);

            redirectAttributes.addFlashAttribute("mensaje", "Archivo subido correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "exito");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("mensaje", "No se pudo subir el archivo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        return new RedirectView("/aula/" + aulaId);
    }

    @GetMapping("/archivo/{aulaId}")
    public ResponseEntity<List<Archivos>> getArchivos(@PathVariable Long aulaId){
        return new ResponseEntity<>(pdfService.getArchivos(aulaId), HttpStatus.OK);
    }

    @GetMapping("/archivo/{id}")
    public ResponseEntity<Archivos> get(@PathVariable Long id){
        return new ResponseEntity<>(pdfService.get(id), HttpStatus.OK);
    }

    @PutMapping("/archivo/{id}")
    public ResponseEntity<Archivos> updateArchivo(
            @PathVariable Long id,
            @RequestParam Long aulaId, // Agrega `aulaId` como parámetro
            @RequestPart Archivos archivos,
            @RequestPart MultipartFile file) throws IOException {
        return new ResponseEntity<>(pdfService.update(id, archivos, file, aulaId), HttpStatus.OK);
    }

    @DeleteMapping("/archivo/{id}")
    public ResponseEntity<?> deleteArchivo(@PathVariable Long id) throws IOException{
        pdfService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
