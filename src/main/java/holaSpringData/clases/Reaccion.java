// src/main/java/holaSpringData/clases/Reaccion.java
package holaSpringData.clases;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reacciones")
public class Reaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mensaje_id", nullable = false)
    private Mensaje mensaje;

    @ManyToOne
    @JoinColumn(name = "individuo_id", nullable = false)
    private Individuo individuo;

    private String tipo; // Ej. "LIKE", "DISLIKE", etc.
}
