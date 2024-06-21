package holaSpringData.servicio;

import holaSpringData.clases.Individuo;

import java.util.List;

public interface IndividuoServicio {

    public List<Individuo> listaindividuos();

    public void salvar(Individuo individuo);

    public void borrar(Individuo individuo);

    public Individuo localizarIndividuo(Individuo individuo);

    public Individuo findByNomusuario(String nomusuario);
}
