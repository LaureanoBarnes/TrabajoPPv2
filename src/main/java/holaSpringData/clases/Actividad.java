// src/main/java/holaSpringData/clases/Actividad.java
package holaSpringData.clases;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    private String titulo;
    private String descripcion;


    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private LocalDateTime plazoEntrega;

    private String estado;  // PENDIENTE, EN_REVISION, CALIFICADA

//    private String archivo;  // Ruta al archivo opcional


    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntregaActividad> entregas;
    private String rutaArchivo;



    // Getters y setters...
}
