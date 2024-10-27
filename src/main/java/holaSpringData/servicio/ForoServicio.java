// src/main/java/holaSpringData/servicio/ForoServicio.java
package holaSpringData.servicio;

import holaSpringData.clases.Aula;
import holaSpringData.clases.Foro;
import holaSpringData.clases.Individuo;
import holaSpringData.clases.Mensaje;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ForoServicio {
    List<Foro> listarForosPorAula(Long aulaId);


    Foro crearForo(Foro foro);

    @Transactional(readOnly = true)
    Foro encontrarForo(Long foroId);

    @Transactional(readOnly = true)
    Aula encontrarAula(Long aulaId);

    @Transactional(readOnly = true)
    Individuo encontrarIndividuoPorUsername(String username);

    List<Mensaje> listarMensajesPorForo(Long foroId);

    Mensaje crearMensaje(Mensaje mensaje);

    void editarMensaje(Long mensajeId, String nuevoContenido);

    void eliminarMensaje(Long mensajeId);

    void reaccionarMensaje(Long mensajeId, Long individuoId, String tipoReaccion);

    void eliminarForo(Long foroId);

    Foro actualizarForo(Foro foro);
}
