// src/main/java/holaSpringData/servicio/EdicionServicioImpl.java
package holaSpringData.servicio;

import holaSpringData.clases.Edicion;
import holaSpringData.clases.Mensaje;
import holaSpringData.dao.EdicionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EdicionServicioImpl implements EdicionServicio {

    @Autowired
    private EdicionDao edicionDao;

    @Override
    @Transactional
    public void guardarEdicion(Mensaje mensaje) {
        Edicion edicion = new Edicion();
        edicion.setMensaje(mensaje);
        edicion.setContenidoAnterior(mensaje.getContenido());
        edicionDao.save(edicion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Edicion> obtenerEdicionesPorMensaje(Long mensajeId) {
        return edicionDao.findByMensajeId(mensajeId);
    }
}
