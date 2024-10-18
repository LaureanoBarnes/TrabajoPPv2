// src/main/java/holaSpringData/dao/ReaccionDao.java
package holaSpringData.dao;

import holaSpringData.clases.Reaccion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReaccionDao extends CrudRepository<Reaccion, Long> {

    @Query("SELECT r FROM Reaccion r WHERE r.mensaje.id = :mensajeId AND r.individuo.id_individuo = :idIndividuo")
    Optional<Reaccion> findByMensajeIdAndIndividuoId(@Param("mensajeId") Long mensajeId, @Param("idIndividuo") Long idIndividuo);
    Long countByMensajeId(Long mensajeId);

    @Query ("SELECT r FROM Reaccion r WHERE r.individuo.id_individuo = :idIndividuo")
    List<Reaccion> findByIndividuoId(@Param("idIndividuo") Long idIndividuo);

    int countByMensajeIdAndTipo(Long mensajeId, String tipo);

}
