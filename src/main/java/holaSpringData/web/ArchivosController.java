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
            archivo.setCategoria(categoria);  // El nombre del aula se asigna como la categoría
            pdfService.save(archivo, file);

            // Añadir mensaje de éxito
            redirectAttributes.addFlashAttribute("mensaje", "Archivo subido correctamente.");
            redirectAttributes.addFlashAttribute("tipoMensaje", "exito");

        } catch (IOException e) {
            // Añadir mensaje de error
            redirectAttributes.addFlashAttribute("mensaje", "No se pudo subir el archivo: " + e.getMessage());
            redirectAttributes.addFlashAttribute("tipoMensaje", "error");
        }

        // Redireccionar al aula correspondiente
        return new RedirectView("/aula/" + aulaId);
    }

    @GetMapping("/archivo")
    public ResponseEntity<List<Archivos>> getArchivos(){
        return new ResponseEntity<>(pdfService.getArchivos(), HttpStatus.OK);
    }

    @GetMapping("/archivo/{id}")
    public ResponseEntity<Archivos> get(@PathVariable Long id){
        return new ResponseEntity<>(pdfService.get(id), HttpStatus.OK);
    }

    @PutMapping("/archivo/{id}")
    public ResponseEntity<Archivos> updateArchivo(@PathVariable Long id, @RequestPart Archivos archivos
    , @RequestPart MultipartFile file) throws IOException{
        return new ResponseEntity<>(pdfService.update(id, archivos, file), HttpStatus.OK);
    }

    @DeleteMapping("/archivo/{id}")
    public ResponseEntity<?> deleteArchivo(@PathVariable Long id) throws IOException{
        pdfService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
