package holaSpringData.servicio;

import holaSpringData.clases.Usuario;
import holaSpringData.dao.UsuarioDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UsuarioServicioImpl implements UsuarioServicio{

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listaUsuario() {
        return (List<Usuario>) usuarioDao.findAll();

    }

    @Override
    @Transactional
    public void salvar(Usuario usuario) {
        if (!usuario.getContrasena().startsWith("{bcrypt}")) {
            String encodedPassword = passwordEncoder.encode(usuario.getContrasena());
            usuario.setContrasena(encodedPassword);
        }
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional
    public void borrar(Usuario usuario) {
        usuarioDao.delete(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario localizarUsuario(Usuario usuario) {
        return usuarioDao.findById(usuario.getId_usuario()).orElse(null);

    }
}
