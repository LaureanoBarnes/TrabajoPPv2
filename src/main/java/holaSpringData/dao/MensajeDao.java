// src/main/java/holaSpringData/dao/MensajeDao.java
package holaSpringData.dao;

import holaSpringData.clases.Mensaje;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MensajeDao extends CrudRepository<Mensaje, Long> {
    List<Mensaje> findByForoId(Long foroId);
}
