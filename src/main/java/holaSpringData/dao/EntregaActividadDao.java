// src/main/java/holaSpringData/dao/EntregaActividadDao.java
package holaSpringData.dao;

import holaSpringData.clases.Actividad;
import holaSpringData.clases.EntregaActividad;
import holaSpringData.clases.Individuo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntregaActividadDao extends JpaRepository<EntregaActividad, Long> {
    List<EntregaActividad> findByActividadId(Long actividadId);
    @Query("SELECT e FROM EntregaActividad e WHERE e.individuo.id_individuo = :individuoId AND e.actividad.id = :actividadId")
    List<EntregaActividad> findByIndividuoIdAndActividadId
            (@Param("individuoId") Long individuoId, @Param("actividadId") Long actividadId);


    List<EntregaActividad> findByIndividuoAndActividad(
            @Param("individuo") Individuo individuo,
            @Param("actividad") Actividad actividad
    );
}


