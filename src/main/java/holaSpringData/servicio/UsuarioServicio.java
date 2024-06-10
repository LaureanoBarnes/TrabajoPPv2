package holaSpringData.servicio;

import holaSpringData.clases.Usuario;

import java.util.List;

public interface UsuarioServicio {

    public List<Usuario> listaUsuario();

    public void salvar(Usuario usuario);

    public void borrar(Usuario usuario);

    public Usuario localizarUsuario(Usuario usuario);


}