package holaSpringData.dao;

import holaSpringData.clases.Aula;
import org.springframework.data.repository.CrudRepository;

public interface AulaDao extends CrudRepository<Aula, Long> {
    Aula findByNombreAula(String nombreAula);
}
