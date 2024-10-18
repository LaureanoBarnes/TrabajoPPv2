// src/main/java/holaSpringData/dao/EdicionDao.java
package holaSpringData.dao;

import holaSpringData.clases.Edicion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EdicionDao extends CrudRepository<Edicion, Long> {
    List<Edicion> findByMensajeId(Long mensajeId);
}
