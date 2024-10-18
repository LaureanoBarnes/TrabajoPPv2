// src/main/java/holaSpringData/servicio/EdicionServicio.java
package holaSpringData.servicio;

import holaSpringData.clases.Edicion;
import holaSpringData.clases.Mensaje;

import java.util.List;

public interface EdicionServicio {
    void guardarEdicion(Mensaje mensaje);
    List<Edicion> obtenerEdicionesPorMensaje(Long mensajeId);
}
