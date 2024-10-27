// src/main/java/holaSpringData/clases/EntregaActividad.java
package holaSpringData.clases;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "entrega_actividad")
public class EntregaActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "actividad_id", nullable = false)
    private Actividad actividad;

    @ManyToOne
    @JoinColumn(name = "individuo_id", nullable = false)
    private Individuo individuo;  // Estudiante que realiza la entrega

    private String archivoEntregado;  // Ruta al archivo entregado
    private LocalDateTime fechaEntrega;
    private String estado;  // PENDIENTE, EN_REVISION, CALIFICADA

    private int calificacion;  // Calificación de 1-10 o puede ser 0 (sin calificación)
    private String feedback;  // Feedback de al menos 500 caracteres
}