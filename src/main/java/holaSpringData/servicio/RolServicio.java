package holaSpringData.servicio;


import holaSpringData.clases.Rol;

import java.util.List;

public interface RolServicio {

    public List<Rol> listaRol();

    public void salvar(Rol rol);

    public void borrar(Rol rol);

    public Rol localizarRol(String nombre);


}
