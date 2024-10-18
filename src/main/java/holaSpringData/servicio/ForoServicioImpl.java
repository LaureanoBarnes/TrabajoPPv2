// src/main/java/holaSpringData/servicio/ForoServicioImpl.java
package holaSpringData.servicio;

import holaSpringData.clases.Foro;
import holaSpringData.clases.Aula;
import holaSpringData.clases.Individuo;
import holaSpringData.clases.Mensaje;
import holaSpringData.dao.ForoDao;
import holaSpringData.dao.AulaDao;
import holaSpringData.dao.IndividuoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForoServicioImpl implements ForoServicio {

    @Autowired
    private ForoDao foroDao;

    @Autowired
    private AulaDao aulaDao;

    @Autowired
    private IndividuoDao individuoDao;

    @Autowired
    private MensajeServicio mensajeServicio;

    @Autowired
    private ReaccionServicio reaccionServicio;

    @Override
    @Transactional(readOnly = true)
    public List<Foro> listarForosPorAula(Long aulaId) {

        return foroDao.findForosByAulaId(aulaId);
    }


    @Override
    @Transactional
    public Foro crearForo(Foro foro) {
        return foroDao.save(foro);
    }

    @Override
    @Transactional(readOnly = true)
    public Foro encontrarForo(Long foroId) {
        return foroDao.findById(foroId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Aula encontrarAula(Long aulaId) {
        return aulaDao.findById(aulaId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Individuo encontrarIndividuoPorUsername(String username) {
        return individuoDao.findByUnUsuarioNomusuario(username);
    }

    // Método para listar mensajes de un foro específico
    @Override
    @Transactional(readOnly = true)
    public List<Mensaje> listarMensajesPorForo(Long foroId) {
        return mensajeServicio.listarMensajesPorForo(foroId);
    }

    // Método para crear un mensaje en un foro
    @Override
    @Transactional
    public Mensaje crearMensaje(Mensaje mensaje) {
        return mensajeServicio.crearMensaje(mensaje);
    }

    // Método para editar un mensaje dentro del límite de tiempo
    @Override
    @Transactional
    public void editarMensaje(Long mensajeId, String nuevoContenido) {
        mensajeServicio.editarMensaje(mensajeId, nuevoContenido);
    }

    // Método para eliminar un mensaje
    @Override
    @Transactional
    public void eliminarMensaje(Long mensajeId) {
        mensajeServicio.eliminarMensaje(mensajeId);
    }

    // Método para reaccionar a un mensaje
    @Override
    @Transactional
    public void reaccionarMensaje(Long mensajeId, Long individuoId, String tipoReaccion) {
        reaccionServicio.reaccionar(mensajeId, individuoId, tipoReaccion);
    }

}
