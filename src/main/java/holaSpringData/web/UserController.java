package holaSpringData.web;

import holaSpringData.clases.Individuo;
import holaSpringData.clases.Rol;
import holaSpringData.clases.Usuario;
import holaSpringData.servicio.IndividuoServicio;
import holaSpringData.servicio.RolServicio;
import holaSpringData.servicio.UsuarioServicio;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@Slf4j


public class UserController {

    @Autowired
    private IndividuoServicio individuoServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;
    
    @Autowired
    private RolServicio rolServicio;


//    @GetMapping("/")
//    public String comienzo(Model model, @AuthenticationPrincipal User usuario) {
//        List<Individuo> individuos = individuoServicio.listaindividuos();
//
//
//        log.info("estoy ejecutando el controlador MVP");
//        log.info("El usuario que ingreso es: " + usuario);
//        model.addAttribute("individuos", individuos);
//        return "indice";
//    }

    @GetMapping("/anexar")
    public String anexar(Model model) {
        model.addAttribute("individuo", new Individuo());
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", List.of("ROLE_ADMIN", "ROLE_USER"));
        return "registro";

    }

    @PostMapping("/salvar")
    public String salvar(@Valid Individuo individuo, Errors errorIndividuo,
                         @Valid @ModelAttribute Usuario usuario, Errors errorUsuario,
                         @RequestParam("rol") String rolNombre
                         ) {
        if (errorIndividuo.hasErrors() || errorUsuario.hasErrors()) {
            return "registro";
        }

        Rol rol = rolServicio.localizarRol(rolNombre);
        usuario.setUnRol(rol);
        usuarioServicio.salvar(usuario);

        individuo.setUnUsuario(usuario);
        individuoServicio.salvar(individuo);
        return "redirect:/ingreso";

    }

//    @GetMapping("/cambiar/{id_individuo}")
//    public String cambiar(Individuo individuo, Model model) {
//        individuo = individuoServicio.localizarIndividuo(individuo);
//        model.addAttribute("individuo", individuo);
//        return "cambiar";
//
//    }

    @GetMapping("/borrar")
    public String borrar(Individuo individuo) {
        individuoServicio.borrar(individuo);
        return "redirect:/ingreso";

    }





}