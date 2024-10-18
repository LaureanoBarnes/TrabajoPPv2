// src/main/java/holaSpringData/servicio/ReaccionServicioImpl.java
package holaSpringData.servicio;

import holaSpringData.clases.Reaccion;
import holaSpringData.clases.Individuo;
import holaSpringData.dao.ReaccionDao;
import holaSpringData.dao.MensajeDao;
import holaSpringData.dao.IndividuoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReaccionServicioImpl implements ReaccionServicio {

    @Autowired
    private ReaccionDao reaccionDao;

    @Autowired
    private MensajeDao mensajeDao;

    @Autowired
    private IndividuoDao individuoDao;

    @Autowired
    private MensajeServicio mensajeServicio;
    @Autowired
    private IndividuoServicio individuoServicio;



    @Override
    @Transactional
    public Long reaccionar(Long mensajeId, Long individuoId, String tipoReaccion) {
        // Cargar el objeto Individuo
        Individuo individuo = individuoDao.findById(individuoId)
                .orElseThrow(() -> new RuntimeException("Individuo no encontrado"));

        // Verificar si ya existe una reacción
        var reaccionExistente = reaccionDao.findByMensajeIdAndIndividuoId(mensajeId, individuoId);

        if (reaccionExistente.isPresent()) {
            // Quitar "Me gusta" existente
            reaccionDao.delete(reaccionExistente.get());
        } else {
            // Añadir "Me gusta"
            Reaccion nuevaReaccion = new Reaccion();
            nuevaReaccion.setMensaje(mensajeDao.findById(mensajeId).orElseThrow(() -> new RuntimeException("Mensaje no encontrado")));
            nuevaReaccion.setIndividuo(individuo);  // Aquí se pasa el objeto Individuo
            nuevaReaccion.setTipo(tipoReaccion);
            reaccionDao.save(nuevaReaccion);
        }

        // Retornar la cantidad actualizada de reacciones
        return reaccionDao.countByMensajeId(mensajeId);
    }

    @Override
    public int alternarReaccion(Long mensajeId, Long individuoId, String tipo) {
        Optional<Reaccion> reaccionExistente = reaccionDao.findByMensajeIdAndIndividuoId(mensajeId, individuoId);

        if (reaccionExistente.isPresent()) {
            // Si ya existe, la eliminamos (quitar "Me gusta")
            reaccionDao.delete(reaccionExistente.get());
        } else {
            // Si no existe, obtenemos el objeto Individuo
            Individuo individuo = individuoServicio.encontrarPorId(individuoId);
            Reaccion nuevaReaccion = new Reaccion();
            nuevaReaccion.setMensaje(mensajeServicio.encontrarMensajePorId(mensajeId));
            nuevaReaccion.setIndividuo(individuo);  // Asigna el objeto completo
            nuevaReaccion.setTipo(tipo);
            reaccionDao.save(nuevaReaccion);
        }

        // Después de alternar, devolvemos la cantidad total de reacciones "Me gusta" para el mensaje
        return reaccionDao.countByMensajeIdAndTipo(mensajeId, tipo);
    }

}
