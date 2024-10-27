// src/main/java/holaSpringData/servicio/ActividadServicio.java
package holaSpringData.servicio;

import holaSpringData.clases.Actividad;
import java.util.List;

public interface ActividadServicio {
    List<Actividad> listarActividadesPorAula(Long aulaId);
    Actividad crearActividad(Actividad actividad);
    Actividad actualizarActividad(Actividad actividad);
    void eliminarActividad(Long actividadId);


    Actividad encontrarActividad(Long actividadId);
}