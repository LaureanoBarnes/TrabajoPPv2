// src/main/java/holaSpringData/web/ForoController.java
package holaSpringData.web;

import holaSpringData.clases.Aula;
import holaSpringData.clases.Foro;
import holaSpringData.clases.Individuo;
import holaSpringData.clases.Mensaje;
import holaSpringData.servicio.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/aula/{id_aula}/foro")
public class ForoController {

    private final ForoServicio foroServicio;

    private final MensajeServicio mensajeServicio;
    private final ReaccionServicioImpl reaccionServicioImpl;
    private final AulaServicio aulaServicio;

    private final UsuarioAutenticacionServicio usuarioAutenticacionServicio;

    public ForoController(ForoServicio foroServicio, MensajeServicio mensajeServicio, ReaccionServicioImpl reaccionServicioImpl, AulaServicio aulaServicio, UsuarioAutenticacionServicio usuarioAutenticacionServicio) {
        this.foroServicio = foroServicio;
        this.mensajeServicio = mensajeServicio;
        this.reaccionServicioImpl = reaccionServicioImpl;
        this.aulaServicio = aulaServicio;
        this.usuarioAutenticacionServicio = usuarioAutenticacionServicio;
    }

    @GetMapping
    public String verForos(@PathVariable Long id_aula, Model model, @AuthenticationPrincipal User usuario) {
        // Obtener los foros asociados al aula
        var foros = foroServicio.listarForosPorAula(id_aula);

        // Obtener el aula
        Aula aula = aulaServicio.encontrarAula(id_aula);
        if (aula == null) {
            return "redirect:/"; // Si no se encuentra el aula, redirige a una página de error
        }

        // Obtener el usuario actual y su nombre completo
        String nombreCompleto = usuarioAutenticacionServicio.obtenerNombreCompleto(usuario);
        Individuo currentUser = usuarioAutenticacionServicio.obtenerUsuarioActual(usuario);

        // Añadir atributos al modelo
        model.addAttribute("foros", foros);  // Lista de foros
        model.addAttribute("id_aula", id_aula);  // ID del aula
        model.addAttribute("aula", aula);  // Objeto 'aula' para obtener más información como su nombre
        model.addAttribute("nombreCompleto", nombreCompleto);  // Nombre completo del usuario
        model.addAttribute("currentUser", currentUser);  // Usuario actual

        return "aulas/listaForos";  // Renderiza la vista listaForos.html
    }

    @GetMapping("/{foro_id}")
    public String verForo(@PathVariable Long id_aula, @PathVariable Long foro_id, Model model,
                          @AuthenticationPrincipal User usuario) {
        // Obtener el foro
        Foro foro = foroServicio.encontrarForo(foro_id);

        // Obtener el aula
        Aula aula = aulaServicio.encontrarAula(id_aula);
        if (aula == null) {
            return "redirect:/"; // Si no se encuentra, redirige a una página de error
        }

        // Obtener el usuario actual y su nombre completo
        String nombreCompleto = usuarioAutenticacionServicio.obtenerNombreCompleto(usuario);
        Individuo currentUser = usuarioAutenticacionServicio.obtenerUsuarioActual(usuario);

        // Añadir atributos al modelo
        model.addAttribute("foro", foro);
        model.addAttribute("id_aula", id_aula);
        model.addAttribute("aula", aula);  // Pasar el objeto 'aula' para usar su nombre
        model.addAttribute("nombreCompleto", nombreCompleto);  // Pasar el nombre completo del usuario
        model.addAttribute("currentUser", currentUser);  // Pasar el usuario actual

        return "aulas/detalleForo";  // Renderizar la vista 'detalleForo.html'
    }

    @GetMapping("/{foro_id}/eliminar")
    public String eliminarForo(@PathVariable Long id_aula, @PathVariable Long foro_id) {
        foroServicio.eliminarForo(foro_id);
        return "redirect:/aula/" + id_aula + "/foro";
    }

    @GetMapping("/{foro_id}/actualizar")
    public String actualizarForo(@PathVariable Long id_aula, @PathVariable Long foro_id, Model model) {
        Foro foro = foroServicio.encontrarForo(foro_id);
        model.addAttribute("foro", foro);
        return "aulas/actualizarForo";
    }

    @PostMapping("/{foro_id}/actualizar")
    public String actualizarForo(@PathVariable Long id_aula, @PathVariable Long foro_id, @ModelAttribute Foro foro) {
        foroServicio.actualizarForo(foro);
        return "redirect:/aula/" + id_aula + "/foro";
    }




    @PostMapping("/crear")
    public String crearForo(@PathVariable Long id_aula, @RequestParam String titulo,
                            @RequestParam String descripcion, @AuthenticationPrincipal User usuario) {
        Foro foro = new Foro();
        foro.setAula(foroServicio.encontrarAula(id_aula));
        foro.setAutor(foroServicio.encontrarIndividuoPorUsername(usuario.getUsername()));
        foro.setTitulo(titulo);
        foro.setDescripcion(descripcion);  // Se añade la descripción
        foroServicio.crearForo(foro);
        return "redirect:/aula/" + id_aula + "/foro";
    }


    @PostMapping("/{foro_id}/mensaje")
    public String agregarMensaje(@PathVariable Long id_aula,@PathVariable Long foro_id, @RequestParam String contenido,
                                 @AuthenticationPrincipal User usuario, RedirectAttributes redirectAttributes) {
        if (contenido.length() > 350) {
            redirectAttributes.addFlashAttribute("error", "El contenido del mensaje excede los 350 caracteres.");
            return "redirect:/aula/" + foro_id + "/foro";
        }
        Mensaje mensaje = new Mensaje();
        mensaje.setForo(foroServicio.encontrarForo(foro_id));
        mensaje.setAutor(foroServicio.encontrarIndividuoPorUsername(usuario.getUsername()));
        mensaje.setContenido(contenido);
        mensaje.setFechaCreacion(LocalDateTime.now());
        foroServicio.crearMensaje(mensaje);
        return "redirect:/aula/" +id_aula +"/foro/" + foro_id;
    }

    @PostMapping("/{foro_id}/mensaje/{mensaje_id}/respuesta")
    public String responderMensaje(@PathVariable Long id_aula,
                                   @PathVariable Long foro_id,
                                   @PathVariable Long mensaje_id,
                                   @RequestParam String contenido,
                                   @AuthenticationPrincipal User usuario) {
        // Crea la respuesta y asigna el mensaje principal como su padre
        Mensaje mensajePadre = mensajeServicio.encontrarMensajePorId(mensaje_id);

        Mensaje respuesta = new Mensaje();
        respuesta.setMensajePadre(mensajePadre);  // Asocia la respuesta con el mensaje principal
        respuesta.setForo(foroServicio.encontrarForo(foro_id));
        respuesta.setAutor(foroServicio.encontrarIndividuoPorUsername(usuario.getUsername()));
        respuesta.setContenido(contenido);
        respuesta.setFechaCreacion(LocalDateTime.now());

        // Guarda la respuesta como mensaje anidado, no como un nuevo mensaje principal
        foroServicio.crearMensaje(respuesta);

        return "redirect:/aula/" + id_aula + "/foro/" + foro_id;
    }

    @PostMapping("/{foro_id}/mensaje/{mensaje_id}/reaccion")
    public ResponseEntity<Integer> reaccionarMensaje(
            @PathVariable Long id_aula,
            @PathVariable Long foro_id,
            @PathVariable Long mensaje_id,
            @RequestParam String tipo,
            @AuthenticationPrincipal User usuario) {

        Long individuoId = foroServicio.encontrarIndividuoPorUsername(usuario.getUsername()).getId_individuo();
        int nuevaCantidad = reaccionServicioImpl.alternarReaccion(mensaje_id, individuoId, tipo);  // Llama al servicio y obtiene el conteo

        return ResponseEntity.ok(nuevaCantidad);
    }
}
