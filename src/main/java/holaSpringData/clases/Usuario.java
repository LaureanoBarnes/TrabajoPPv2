package holaSpringData.clases;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    @Column(unique = true)
    @NotEmpty
    private String nomusuario;
    @NotEmpty
    private String contrasena;
    @ManyToOne
    @JoinColumn(name="rol_id_rol")
    private Rol unRol;
}
