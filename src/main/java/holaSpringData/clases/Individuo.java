package holaSpringData.clases;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name= "individuo")
public class Individuo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_individuo;

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;

    @NotEmpty
    @Email(regexp = ".+@.+\\..+",message = "Email debe ser válido")
    private String correo;
    @NotEmpty
    private String edad;

    private String telefono;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_usuario")
    private Usuario unUsuario;


    @ManyToMany
    @JoinTable(
            name = "aula_usuarios",
            joinColumns = @JoinColumn(name = "individuo_id"),
            inverseJoinColumns = @JoinColumn(name = "aula_id")
    )
    private List<Aula> aulas;

}
