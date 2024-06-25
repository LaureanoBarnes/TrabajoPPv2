package holaSpringData.iniciacion;

import holaSpringData.clases.Rol;
import holaSpringData.dao.RolDao;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class Roliniciacion {

    @Autowired
    private RolDao rolDao;

    @PostConstruct
    @Transactional
    public void init() {
        if (rolDao.findById(1L).isEmpty()) {
            Rol adminRole = new Rol();
            adminRole.setId_rol(1L);
            adminRole.setNombre("ROLE_ADMIN");
            rolDao.save(adminRole);
        }

        if (rolDao.findById(2L).isEmpty()) {
            Rol userRole = new Rol();
            userRole.setId_rol(2L);
            userRole.setNombre("ROLE_USER");
            rolDao.save(userRole);
        }
    }
}
