// src/main/java/holaSpringData/servicio/EntregaActividadServicio.java
package holaSpringData.servicio;

import holaSpringData.clases.EntregaActividad;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface EntregaActividadServicio {

    List<EntregaActividad> listarEntregasPorActividad(Long actividadId);

    EntregaActividad encontrarEntregaPorId(Long entregaId);

    EntregaActividad crearEntrega(EntregaActividad entrega, MultipartFile archivoEntregado) throws Exception;

    void eliminarEntrega(Long entregaId);

    void calificarEntrega(Long entregaId, int calificacion, String feedback, String estado);

}
