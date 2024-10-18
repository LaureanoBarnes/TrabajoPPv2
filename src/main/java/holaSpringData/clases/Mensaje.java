// src/main/java/holaSpringData/clases/Mensaje.java
package holaSpringData.clases;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "foro_id", nullable = false)
    private Foro foro;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Individuo autor;

    @ManyToOne
    @JoinColumn(name = "mensaje_padre_id")
    private Mensaje mensajePadre; // Para respuestas anidadas

    @OneToMany(mappedBy = "mensajePadre", cascade = CascadeType.ALL)
    private List<Mensaje> respuestas = new ArrayList<>();

    private String contenido;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edicion> ediciones;

    @OneToMany(mappedBy = "mensaje", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reaccion> reacciones;


}
