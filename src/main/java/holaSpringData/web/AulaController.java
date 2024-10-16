package holaSpringData.web;

import holaSpringData.clases.Archivos;
import holaSpringData.clases.Aula;
import holaSpringData.clases.Individuo;
import holaSpringData.dao.ArchivoDao;
import holaSpringData.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
public class AulaController {
    @Autowired
    private PdfService pdfService;

    @Autowired
    private AulaServicio aulaServicio;

    @Autowired
    private IndividuoServicio individuoServicio;

    @Autowired
    private UsuarioAutenticacionServicio usuarioAutenticacionServicio;


    @GetMapping("/")
    public String listarAulas(Model model, @AuthenticationPrincipal User usuario) {
        var aulas = aulaServicio.listarAulas();
        model.addAttribute("aulas", aulas);

        Individuo currentUser = individuoServicio.findByNomusuario(usuario.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        // Verifica si el usuario tiene el rol de administrador
        boolean esAdmin = usuario.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        model.addAttribute("esAdmin", esAdmin);

        return "aulas/home";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/aula/{id_aula}/registrarse")
    public String registrarEnAula(@PathVariable("id_aula") Long id_aula, @AuthenticationPrincipal User usuario) {
        Aula aula = aulaServicio.encontrarAula(id_aula);
        Individuo currentUser = individuoServicio.findByNomusuario(usuario.getUsername());

        // Verifica que el usuario no sea un administrador ni esté en la lista de usuarios
        if (!aula.getAdministradores().contains(currentUser) && !aula.getUsuarios().contains(currentUser)) {
            aulaServicio.agregarUsuario(id_aula, currentUser.getId_individuo());
        }

        return "redirect:/aula/" + id_aula;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/crear")
    public String crearAulaForm(Model model, @AuthenticationPrincipal User usuario) {
        model.addAttribute("aula", new Aula());
        model.addAttribute("currentUser", usuarioAutenticacionServicio.obtenerUsuarioActual(usuario));
        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        return "aulas/crear";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public String guardarAula(@ModelAttribute Aula aula, @AuthenticationPrincipal User usuario) {
        Individuo creador = individuoServicio.findByNomusuario(usuario.getUsername());
        if (aula.getAdministradores() == null) {
            aula.setAdministradores(new ArrayList<>());
        }
        aula.getAdministradores().add(creador);
        aulaServicio.guardar(aula);
        return "redirect:/";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id_aula}")
    public String editarAula(@PathVariable("id_aula") Long id_aula, Model model) {
        Aula aula = aulaServicio.encontrarAula(id_aula);
        model.addAttribute("aula", aula);
        return "aulas/crear";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarAula(@PathVariable("id") Long id) {
        Aula aula = aulaServicio.encontrarAula(id);
        if (aula != null) {
            aulaServicio.eliminar(aula);
        }
        return "redirect:/";
    }

    @PostMapping("/{id_aula}/agregarAdministrador")
    public String agregarAdministrador(@PathVariable("id_aula") Long id_aula, @RequestParam("id_individuo") Long id_individuo) {
        aulaServicio.agregarAdministrador(id_aula, id_individuo);
        return "redirect:/aulas/editar/" + id_aula;
    }

    @PostMapping("/aula/{id_aula}/agregarUsuario")
    public String agregarUsuario(@PathVariable("id_aula") Long id_aula, @RequestParam("id_individuo") Long id_individuo) {
        aulaServicio.agregarUsuario(id_aula, id_individuo);
        return "redirect:/aula/" + id_aula;
    }

    @PostMapping("/{id_aula}/eliminarAdministrador")
    public String eliminarAdministrador(@PathVariable("id_aula") Long id_aula, @RequestParam("id_individuo") Long id_individuo) {
        aulaServicio.eliminarAdministrador(id_aula, id_individuo);
        return "redirect:/aulas/editar/" + id_aula;
    }

    @PostMapping("/{id_aula}/eliminarUsuario")
    public String eliminarUsuario(@PathVariable("id_aula") Long id_aula, @RequestParam("id_individuo") Long id_individuo) {
        aulaServicio.eliminarUsuario(id_aula, id_individuo);
        return "redirect:/aulas/editar/" + id_aula;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/archivo/eliminar/{id}/{id_aula}")
    public String eliminarArchivo(@PathVariable Long id, @PathVariable Long id_aula, RedirectAttributes redirectAttributes) {
        try {
            pdfService.delete(id);  // Implementa este método en tu servicio
            redirectAttributes.addFlashAttribute("mensaje", "Archivo eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el archivo");
        }
        return "redirect:/aula/" + id_aula + "/subirmaterial";  // Redirige a la lista de archivos o la página que desees
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/archivo/editar/{id}/{id_aula}")
    public String editarArchivo(
            @PathVariable Long id,
            @PathVariable Long id_aula,
            Model model) {
        Archivos archivo = pdfService.get(id);
        model.addAttribute("archivo", archivo);
        model.addAttribute("id_aula", id_aula);
        return "redirect:/aula/" + id_aula +"/subirmaterial";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/archivo/editar/{id}/{id_aula}")
    public String actualizarArchivo(
            @PathVariable Long id,
            @PathVariable Long id_aula,
            @ModelAttribute Archivos archivo,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {
        try {
            pdfService.update(id, archivo, file, id_aula); // Incluye id_aula como cuarto parámetro
            redirectAttributes.addFlashAttribute("mensaje", "Archivo actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar el archivo");
        }
        return "redirect:/aula/" + id_aula +"/subirmaterial";
    }




    @GetMapping("/aula/{id_aula}")
    public String verAula(@PathVariable("id_aula") Long id_aula, Model model, @AuthenticationPrincipal User usuario) {
        Aula aula = aulaServicio.encontrarAula(id_aula);
        if (aula == null) {
            return "redirect:/"; // Redirige si el aula no existe
        }

        Individuo currentUser = individuoServicio.findByNomusuario(usuario.getUsername());
        boolean esAdmin = aula.getAdministradores().contains(currentUser);
        boolean esUsuario = aula.getUsuarios().contains(currentUser);

        if (!esAdmin && !esUsuario) {
            return "redirect:/aula/" + id_aula + "/registrarse";
        }

        model.addAttribute("aula", aula);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        return "aulas/visualizacion";
    }
    @GetMapping("/aula/{id}/foro")
    public String verForo(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User usuario) {
        Aula aula = aulaServicio.encontrarAula(id);
        if (aula == null) {
            return "redirect:/"; // Redirige si el aula no existe
        }

        Individuo currentUser = individuoServicio.findByNomusuario(usuario.getUsername());
        model.addAttribute("aula", aula);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        // Añade cualquier lógica específica para cargar el foro de esta aula

        return "aulas/foro"; // Asegúrate de tener la vista "foro.html"
    }

    @GetMapping("/aula/{id}/subirmaterial")
    public String mostrarSubirMaterial(@PathVariable Long id, Model model, @AuthenticationPrincipal User usuario) {
        model.addAttribute("currentUser", usuarioAutenticacionServicio.obtenerUsuarioActual(usuario));
        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        Aula aula = aulaServicio.encontrarAula(id);
        if (aula == null) {
            return "redirect:/"; // Si no se encuentra, redirige a una página de error
        }

        model.addAttribute("aula", aula);
        List<Archivos> archivos = pdfService.getArchivos(id);
        model.addAttribute("archivos", archivos);  // Asegúrate de pasar 'archivos' aquí
        model.addAttribute("id_aula", id); // Pasar también el 'id_aula' si es necesario

        return "aulas/subirmaterial"; // Cargar la vista correcta
    }

    @GetMapping("/aula/{id}/participantes")
    public String mostrarParticipantes(@PathVariable Long id, Model model, @AuthenticationPrincipal User usuario) {
        model.addAttribute("currentUser", usuarioAutenticacionServicio.obtenerUsuarioActual(usuario));
        model.addAttribute("nombreCompleto", usuarioAutenticacionServicio.obtenerNombreCompleto(usuario));

        Aula aula = aulaServicio.encontrarAula(id);
        if (aula == null) {
            return "redirect:/"; // Si no se encuentra, redirige a una página de error
        }

        model.addAttribute("aula", aula);
        List<Archivos> archivos = pdfService.getArchivos(id);
        model.addAttribute("archivos", archivos);  // Asegúrate de pasar 'archivos' aquí
        model.addAttribute("id_aula", id); // Pasar también el 'id_aula' si es necesario

        return "aulas/participantes"; // Cargar la vista correcta
    }



}
