// src/main/java/holaSpringData/web/EntregaActividadController.java
package holaSpringData.web;

import holaSpringData.clases.Actividad;
import holaSpringData.clases.Aula;
import holaSpringData.clases.EntregaActividad;
import holaSpringData.clases.Individuo;
import holaSpringData.servicio.ActividadServicio;
import holaSpringData.servicio.AulaServicio;
import holaSpringData.servicio.EntregaActividadServicio;
import holaSpringData.servicio.UsuarioAutenticacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/aula/{id_aula}/actividades/{id_actividad}/entregas")
public class EntregaActividadController {

    private final EntregaActividadServicio entregaActividadServicio;
    private final UsuarioAutenticacionServicio usuarioAutenticacionServicio;
    @Autowired
    private ActividadServicio actividadServicio;
    @Autowired
    private AulaServicio aulaServicio;

    public EntregaActividadController(EntregaActividadServicio entregaActividadServicio, UsuarioAutenticacionServicio usuarioAutenticacionServicio) {
        this.entregaActividadServicio = entregaActividadServicio;
        this.usuarioAutenticacionServicio = usuarioAutenticacionServicio;
    }

    @GetMapping
    public String listarEntregas(@PathVariable Long id_aula, @PathVariable Long id_actividad, Model model, @AuthenticationPrincipal User usuario) {
        // Busca la actividad por su ID
        Actividad actividad = actividadServicio.encontrarActividad(id_actividad);

        // Si la actividad no se encuentra, manejar el error adecuadamente
        if (actividad == null) {
            // Manejo de error, redirigir a una página de error o lanzar una excepción
            return "error/404";  // Por ejemplo, una página de error personalizada
        }
        Aula aula = aulaServicio.encontrarAula(id_aula);

        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        // Añadir la actividad al modelo
        model.addAttribute("actividad", actividad);

        // Añadir también otros atributos como la lista de entregas
        List<EntregaActividad> entregas = entregaActividadServicio.listarEntregasPorActividad(id_actividad);
        if (entregas == null) {
            entregas = new ArrayList<>();
        }        model.addAttribute("entregas", entregas);
        model.addAttribute("id_aula", id_aula);
        model.addAttribute("id_actividad", id_actividad);
        model.addAttribute("aula", aula);



        return "aulas/detalleEntrega";  // Reemplaza con el nombre correcto de tu vista
    }



    @PostMapping("/subir")
    public String subirEntrega(@PathVariable Long id_aula, @PathVariable Long id_actividad,
                               @RequestParam MultipartFile archivoEntregado,
                               @AuthenticationPrincipal User usuario) throws Exception {
        Individuo estudiante = usuarioAutenticacionServicio.obtenerUsuarioActual(usuario);

        // Recuperar la actividad por su ID
        Actividad actividad = actividadServicio.encontrarActividad(id_actividad);

        // Crear una nueva entrega y asignar la actividad y el estudiante
        EntregaActividad entrega = new EntregaActividad();
        entrega.setIndividuo(estudiante);
        entrega.setFechaEntrega(java.time.LocalDateTime.now());
        entrega.setActividad(actividad);  // Asignar la actividad a la entrega

        // Crear entrega y guardar el archivo
        entregaActividadServicio.crearEntrega(entrega, archivoEntregado);

        return "redirect:/aula/" + id_aula + "/actividades";
    }

    @PostMapping("/{entrega_id}/calificar")
    public ResponseEntity<String> calificarEntrega(@PathVariable Long id_aula, @PathVariable Long id_actividad,
                                                   @PathVariable Long entrega_id,
                                                   @RequestParam int calificacion,
                                                   @RequestParam String feedback,
                                                   @RequestParam String estado) {
        // Asegurarse de que el estado esté en una de las opciones válidas
        if (!List.of("APROBADO", "DESAPROBADO", "REENVIAR").contains(estado.toUpperCase())) {
            throw new IllegalArgumentException("Estado inválido");
        }

        // Calificar la entrega y actualizar el estado
        entregaActividadServicio.calificarEntrega(entrega_id, calificacion, feedback, estado);
        return ResponseEntity.ok("Calificación actualizada");

    }


}