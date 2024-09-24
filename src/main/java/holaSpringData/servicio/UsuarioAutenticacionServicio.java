package holaSpringData.servicio;

import holaSpringData.clases.Individuo;
import holaSpringData.dao.IndividuoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAutenticacionServicio {

    @Autowired
    private IndividuoDao individuoDao;

    public Individuo obtenerUsuarioActual(User usuario) {
        return individuoDao.findByUnUsuarioNomusuario(usuario.getUsername());
    }

    public String obtenerNombreCompleto(User usuario) {
        Individuo currentUser = obtenerUsuarioActual(usuario);
        return currentUser.getNombre() + " " + currentUser.getApellido();
    }
}
