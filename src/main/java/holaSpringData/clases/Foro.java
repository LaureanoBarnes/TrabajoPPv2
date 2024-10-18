// src/main/java/holaSpringData/clases/Foro.java
package holaSpringData.clases;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Entity
@Table(name = "foros")
public class Foro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aula_id", nullable = false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Individuo autor;
    private String descripcion;  // Nuevo campo para descripción del foro

    private String titulo;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "foro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensaje> mensajes;
}
