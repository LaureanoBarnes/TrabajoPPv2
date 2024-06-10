package holaSpringData.servicio;

import holaSpringData.clases.Individuo;
import holaSpringData.dao.IndividuoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class IndividuoServicioImp implements IndividuoServicio{


    @Autowired
    private IndividuoDao individuoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Individuo> listaindividuos() {
        return (List<Individuo>) individuoDao.findAll();
    }

    @Override
    @Transactional
    public void salvar(Individuo individuo) {
        individuoDao.save(individuo);
    }

    @Override
    @Transactional
    public void borrar(Individuo individuo) {
        individuoDao.delete(individuo);

    }

    @Override
    @Transactional(readOnly = true)
    public Individuo localizarIndividuo(Individuo individuo) {
        return individuoDao.findById(individuo.getId_individuo()).orElse(null);

    }
}
