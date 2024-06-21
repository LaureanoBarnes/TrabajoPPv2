package holaSpringData.servicio;

import holaSpringData.clases.Aula;
import holaSpringData.clases.Individuo;
import holaSpringData.dao.AulaDao;

import holaSpringData.dao.IndividuoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AulaServicio {

    @Autowired
    private AulaDao aulaDao;
    @Autowired
    private IndividuoDao individuoDao;


    @Transactional(readOnly = true)
    public List<Aula> listarAulas() {
        return (List<Aula>) aulaDao.findAll();
    }

    @Transactional
    public void guardar(Aula aula) {
        aulaDao.save(aula);
    }

    @Transactional
    public void eliminar(Aula aula) {
        aulaDao.delete(aula);
    }


    @Transactional(readOnly = true)
    public Aula encontrarAula(Long id) {
        return aulaDao.findById(id).orElse(null);
    }


    @Transactional
    public void agregarAdministrador(Long id_aula, Long id_individuo) {
        Aula aula = aulaDao.findById(id_aula).orElse(null);
        Individuo administrador = individuoDao.findById(id_individuo).orElse(null);
        if (aula != null && administrador != null) {
            aula.getAdministradores().add(administrador);
            aulaDao.save(aula);
        }
    }

    @Transactional
    public void agregarUsuario(Long id_aula, Long id_individuo) {
        Aula aula = aulaDao.findById(id_aula).orElseThrow(()->
                new RuntimeException("no se encontro el aula: "+ id_aula));
        Individuo usuario = individuoDao.findById(id_individuo).orElseThrow(()->
                new RuntimeException("no se encontro la persona: "+id_individuo));
        if (aula != null && usuario != null) {
            aula.getUsuarios().add(usuario);
            aulaDao.save(aula);
        }
    }

    @Transactional
    public void eliminarAdministrador(Long id_aula, Long id_individuo) {
        Aula aula = aulaDao.findById(id_aula).orElse(null);
        Individuo administrador = individuoDao.findById(id_individuo).orElse(null);
        if (aula != null && administrador != null) {
            aula.getAdministradores().remove(administrador);
            aulaDao.save(aula);
        }
    }

    @Transactional
    public void eliminarUsuario(Long id_aula, Long id_individuo) {
        Aula aula = aulaDao.findById(id_aula).orElse(null);
        Individuo usuario = individuoDao.findById(id_individuo).orElse(null);
        if (aula != null && usuario != null) {
            aula.getUsuarios().remove(usuario);
            aulaDao.save(aula);
        }
    }
}
