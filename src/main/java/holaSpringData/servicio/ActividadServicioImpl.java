package holaSpringData.servicio;

import holaSpringData.clases.Actividad;
import holaSpringData.dao.ActividadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ActividadServicioImpl implements ActividadServicio {

    @Autowired
    private ActividadDao actividadDao;

    @Autowired
    private ActividadArchivoService archivoServicio;

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> listarActividadesPorAula(Long aulaId) {
        return actividadDao.findByAulaId(aulaId);
    }

    @Override
    @Transactional
    public Actividad crearActividad(Actividad actividad) {
        return actividadDao.save(actividad);
    }

    @Override
    @Transactional
    public Actividad actualizarActividad(Actividad actividad) {
        return actividadDao.save(actividad);
    }   

    @Override
    @Transactional
    public void eliminarActividad(Long actividadId) {
        Optional<Actividad> actividadOpt = actividadDao.findById(actividadId);

        if (actividadOpt.isPresent()) {
            Actividad actividad = actividadOpt.get();
            System.out.println("Eliminando actividad con ID: " + actividadId);

            // Verificar si existe un archivo asociado
            String rutaArchivo = actividad.getRutaArchivo();
            if (rutaArchivo != null && !rutaArchivo.isEmpty()) {
                System.out.println("Eliminando archivo asociado: " + rutaArchivo);
                try {
                    archivoServicio.deleteUpload(rutaArchivo);
                } catch (IOException e) {
                    System.out.println("Error eliminando archivo: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            // Eliminar la actividad de la base de datos
            actividadDao.deleteById(actividadId);
            System.out.println("Actividad con ID " + actividadId + " eliminada correctamente");
        } else {
            System.out.println("No se encontró la actividad con ID: " + actividadId);
            throw new IllegalArgumentException("Actividad no encontrada");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Actividad encontrarActividad(Long actividadId) {
        return actividadDao.findById(actividadId).orElse(null);
    }
}
