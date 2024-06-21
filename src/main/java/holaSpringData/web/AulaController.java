package holaSpringData.web;

import holaSpringData.clases.Aula;
import holaSpringData.clases.Individuo;
import holaSpringData.servicio.AulaServicio;
import holaSpringData.servicio.IndividuoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
public class AulaController {

    @Autowired
    private AulaServicio aulaServicio;

    @Autowired
    private IndividuoServicio individuoServicio;


    @GetMapping("/")
    public String listarAulas(Model model, @AuthenticationPrincipal User usuario) {
        var aulas = aulaServicio.listarAulas();
        model.addAttribute("aulas", aulas);

        Individuo currentUser = individuoServicio.findByNomusuario(usuario.getUsername());
        model.addAttribute("currentUser", currentUser);
        return "aulas/listar";
    }

    @GetMapping("/crear")
    public String crearAulaForm(Model model) {
        model.addAttribute("aula", new Aula());
        return "aulas/crear";
    }

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

    @GetMapping("/editar/{id_aula}")
    public String editarAula(@PathVariable("id_aula") Long id_aula, Model model) {
        Aula aula = aulaServicio.encontrarAula(id_aula);
        model.addAttribute("aula", aula);
        return "aulas/crear";
    }

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
        return "redirect:/aula/" + id_aula ;
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



    @GetMapping("/aula/{id_aula}")
    public String verAula(@PathVariable("id_aula") Long id_aula, Model model, @AuthenticationPrincipal User usuario) {
        Aula aula = aulaServicio.encontrarAula(id_aula);
        model.addAttribute("aula", aula);

        Individuo currentUser = individuoServicio.findByNomusuario(usuario.getUsername());
        model.addAttribute("currentUser", currentUser);

        return "aulas/visualizacion";
    }
}
