package holaSpringData.dao;

import holaSpringData.clases.Rol;
import org.springframework.data.repository.CrudRepository;


public interface RolDao extends CrudRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}