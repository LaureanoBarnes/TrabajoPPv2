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

}
