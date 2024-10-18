// src/main/java/holaSpringData/servicio/ReaccionServicio.java
package holaSpringData.servicio;

public interface ReaccionServicio {
    Long reaccionar(Long mensajeId, Long individuoId, String tipoReaccion);
    int alternarReaccion(Long mensajeId, Long individuoId, String tipo);

}
