package holaSpringData.dao;

import holaSpringData.clases.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {
    Usuario findByNomusuario(String nomusuario);
}