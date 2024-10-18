// src/main/java/holaSpringData/clases/Edicion.java
package holaSpringData.clases;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ediciones")
public class Edicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mensaje_id", nullable = false)
    private Mensaje mensaje;

    private String contenidoAnterior;
    private LocalDateTime fechaEdicion = LocalDateTime.now();
}
