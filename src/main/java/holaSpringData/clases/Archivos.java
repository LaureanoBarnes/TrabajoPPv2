package holaSpringData.clases;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "archivos")
public class Archivos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String categoria;

}
