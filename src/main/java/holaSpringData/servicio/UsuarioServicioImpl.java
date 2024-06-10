package holaSpringData.servicio;

import holaSpringData.clases.Usuario;
import holaSpringData.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UsuarioServicioImpl implements UsuarioServicio{


    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listaUsuario() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    @Transactional
    public void salvar(Usuario usuario) {
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
