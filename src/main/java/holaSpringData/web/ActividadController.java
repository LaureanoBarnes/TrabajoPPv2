package holaSpringData.web;

import holaSpringData.clases.Actividad;
import holaSpringData.clases.Aula;
import holaSpringData.servicio.ActividadArchivoService;
import holaSpringData.servicio.ActividadServicio;
import holaSpringData.servicio.AulaServicio;
import holaSpringData.servicio.UsuarioAutenticacionServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/aula/{id_aula}/actividades")
public class ActividadController {

    private static final Logger logger = LoggerFactory.getLogger(ActividadController.class);

    @Autowired
    private ActividadServicio actividadServicio;

    @Autowired
    private AulaServicio aulaServicio;

    @Autowired
    private ActividadArchivoService actividadArchivoService;

    private final UsuarioAutenticacionServicio usuarioAutenticacionServicio;

    public ActividadController(UsuarioAutenticacionServicio usuarioAutenticacionServicio) {
        this.usuarioAutenticacionServicio = usuarioAutenticacionServicio;
    }

    // Listar actividades
    @GetMapping
    public String listarActividades(@PathVariable Long id_aula, Model model, @AuthenticationPrincipal User usuario) {
        logger.info("Listando actividades para el aula con id: {}", id_aula);
        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        List<Actividad> actividades = actividadServicio.listarActividadesPorAula(id_aula);
        Aula aula = aulaServicio.encontrarAula(id_aula);
        model.addAttribute("actividades", actividades);
        model.addAttribute("aula", aula);
        model.addAttribute("id_aula", id_aula); // Añadir id_aula al modelo
        return "aulas/listaActividades";
    }

    // Formulario de crear actividad
    @GetMapping("/crear")
    public String mostrarFormularioCrear(@PathVariable Long id_aula, Model model) {
        logger.info("Mostrando formulario para crear actividad en el aula con id: {}", id_aula);
        model.addAttribute("actividad", new Actividad());
        model.addAttribute("id_aula", id_aula);
        return "aulas/formularioActividad";
    }

    // Crear nueva actividad
    @PostMapping("/crear")
    public String crearActividad(@PathVariable Long id_aula,
                                 @ModelAttribute Actividad actividad,
                                 @RequestParam("archivo") MultipartFile archivo) throws IOException {
        logger.info("Creando nueva actividad en aula con id: {}", id_aula);

        if (!archivo.isEmpty()) {
            String rutaArchivo = actividadArchivoService.saveUpload(archivo, id_aula);
            logger.info("Archivo subido a la ruta: {}", rutaArchivo);
            actividad.setRutaArchivo(rutaArchivo);
        }

        Aula aula = aulaServicio.encontrarAula(id_aula);
        actividad.setAula(aula);

        actividadServicio.crearActividad(actividad);
        logger.info("Actividad creada con id: {} en aula con id: {}", actividad.getId(), id_aula);
        return "redirect:/aula/" + id_aula + "/actividades";
    }

    // Mostrar formulario de edición
    @GetMapping("/{id_actividad}/editar")
    public String mostrarFormularioEditar(@PathVariable Long id_aula, @PathVariable Long id_actividad, Model model) {
        Actividad actividad = actividadServicio.encontrarActividad(id_actividad);
        model.addAttribute("actividad", actividad);
        model.addAttribute("id_aula", id_aula);
        return "aulas/formularioActividad";
    }

    // Editar actividad con PUT
    @PutMapping("/{id_actividad}/editar")
    public String editarActividad(@PathVariable Long id_aula,
                                  @PathVariable Long id_actividad,
                                  @ModelAttribute Actividad actividad,
                                  @RequestParam("archivo") MultipartFile archivo) throws IOException {

        Actividad actividadExistente = actividadServicio.encontrarActividad(id_actividad);

        // Solo actualizar el archivo si uno nuevo es subido
        if (!archivo.isEmpty()) {
            String rutaArchivo = actividadArchivoService.saveUpload(archivo, id_aula);
            actividadExistente.setRutaArchivo(rutaArchivo);
        }

        // Actualizar otros campos
        actividadExistente.setTitulo(actividad.getTitulo());
        actividadExistente.setDescripcion(actividad.getDescripcion());
        actividadExistente.setPlazoEntrega(actividad.getPlazoEntrega());

        actividadServicio.actualizarActividad(actividadExistente);
        return "redirect:/aula/" + id_aula + "/actividades";
    }
    // Eliminar actividad
    @DeleteMapping("/{id_actividad}/eliminar")
    public String eliminarActividad(@PathVariable Long id_aula, @PathVariable Long id_actividad) {
        logger.info("Iniciando proceso de eliminación para la actividad con id: {}", id_actividad);
        actividadServicio.eliminarActividad(id_actividad);
        logger.info("Actividad con id: {} eliminada correctamente", id_actividad);
        return "redirect:/aula/" + id_aula + "/actividades";
    }
}
