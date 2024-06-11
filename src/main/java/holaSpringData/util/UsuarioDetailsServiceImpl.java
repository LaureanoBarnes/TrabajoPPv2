// src/main/java/holaSpringData/servicio/UsuarioDetailsServiceImpl.java

package holaSpringData.util;

import holaSpringData.clases.Usuario;
import holaSpringData.dao.UsuarioDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByNomusuario(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        return User.withUsername(usuario.getNomusuario())
                .password(usuario.getContrasena())
                .roles(usuario.getUnRol().getNombre().replace("ROLE_", ""))
                .build();
    }
}
