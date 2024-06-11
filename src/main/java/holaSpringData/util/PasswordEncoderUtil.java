package holaSpringData.util;

import holaSpringData.clases.Usuario;
import holaSpringData.dao.UsuarioDao;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
@RequiredArgsConstructor
@Component
public class PasswordEncoderUtil {


    @Autowired
    private final UsuarioDao usuarioDao;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void encodePasswords() {
        List<Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
        for (Usuario usuario : usuarios) {
            if (!usuario.getContrasena().startsWith("{bcrypt}")) {
                String encodedPassword = passwordEncoder.encode(usuario.getContrasena());
                usuario.setContrasena(encodedPassword);
                usuarioDao.save(usuario);
            }
        }
    }
}
