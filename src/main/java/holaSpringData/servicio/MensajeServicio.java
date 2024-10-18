// src/main/java/holaSpringData/servicio/MensajeServicio.java
package holaSpringData.servicio;

import holaSpringData.clases.Mensaje;

import java.util.List;

public interface MensajeServicio {
    Mensaje crearMensaje(Mensaje mensaje);
    Mensaje responderMensaje(Long mensajePadreId, String contenido, Long autorId);
    void editarMensaje(Long mensajeId, String nuevoContenido);
    void eliminarMensaje(Long mensajeId);
    List<Mensaje> listarMensajesPorForo(Long foroId);

    // Nuevo método para encontrar mensaje por ID
    Mensaje encontrarMensajePorId(Long mensajeId);
}
