// src/main/java/holaSpringData/dao/ForoDao.java
package holaSpringData.dao;

import holaSpringData.clases.Foro;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForoDao extends CrudRepository<Foro, Long> {
    @Query("SELECT f FROM Foro f WHERE f.aula.id_aula = :idAula")
    List<Foro> findForosByAulaId(@Param("idAula") Long idAula);

}
