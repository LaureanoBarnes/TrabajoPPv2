package holaSpringData.servicio;

import holaSpringData.clases.Rol;
import holaSpringData.dao.RolDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RolServicioImpl implements RolServicio{


    @Autowired
    private RolDao rolDao;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listaRol() {
        return (List<Rol>) rolDao.findAll();
    }

    @Override
    @Transactional
    public void salvar(Rol rol) {
        rolDao.save(rol);
    }

    @Override
    @Transactional
    public void borrar(Rol rol) {
        rolDao.delete(rol);

    }


    @Transactional(readOnly = true)
    public Rol localizarRol(String nombre) {
        return rolDao.findByNombre(nombre);

    }

}
