// src/main/java/holaSpringData/servicio/MensajeServicioImpl.java
package holaSpringData.servicio;

import holaSpringData.clases.Mensaje;
import holaSpringData.dao.MensajeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class MensajeServicioImpl implements MensajeServicio {

    @Autowired
    private MensajeDao mensajeDao;

    @Autowired
    private EdicionServicio edicionServicio;

    @Override
    @Transactional
    public Mensaje crearMensaje(Mensaje mensaje) {
        return mensajeDao.save(mensaje);
    }
    @Override
    @Transactional(readOnly = true)
    public Mensaje encontrarMensajePorId(Long mensajeId) {
        return mensajeDao.findById(mensajeId)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
    }

    @Override
    @Transactional
    public Mensaje responderMensaje(Long mensajePadreId, String contenido, Long autorId) {
        Mensaje mensajePadre = mensajeDao.findById(mensajePadreId).orElseThrow(() -> new RuntimeException("Mensaje padre no encontrado"));
        Mensaje respuesta = new Mensaje();
        respuesta.setMensajePadre(mensajePadre);
        respuesta.setForo(mensajePadre.getForo());
        respuesta.setAutor(mensajePadre.getAutor());
        respuesta.setContenido(contenido);
        respuesta.setFechaCreacion(LocalDateTime.now());
        return mensajeDao.save(respuesta);
    }

    @Override
    @Transactional
    public void editarMensaje(Long mensajeId, String nuevoContenido) {
        Mensaje mensaje = mensajeDao.findById(mensajeId).orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        if (ChronoUnit.MINUTES.between(mensaje.getFechaCreacion(), LocalDateTime.now()) <= 30) {
            // Guardar la edición
            edicionServicio.guardarEdicion(mensaje);
            mensaje.setContenido(nuevoContenido);
            mensajeDao.save(mensaje);
        } else {
            throw new RuntimeException("El mensaje solo se puede editar dentro de los primeros 30 minutos.");
        }
    }

    @Override
    @Transactional
    public void eliminarMensaje(Long mensajeId) {
        mensajeDao.deleteById(mensajeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> listarMensajesPorForo(Long foroId) {
        return mensajeDao.findByForoId(foroId);
    }
}
