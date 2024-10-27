// src/main/java/holaSpringData/dao/ActividadDao.java
package holaSpringData.dao;

import holaSpringData.clases.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActividadDao extends JpaRepository<Actividad, Long> {
    @Query("SELECT a FROM Actividad a WHERE a.aula.id_aula = :aulaId")
    List<Actividad> findByAulaId(@Param("aulaId") Long aulaId);}