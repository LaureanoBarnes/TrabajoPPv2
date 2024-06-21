package holaSpringData.clases;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;



@Data
@Entity
@Table(name = "aula")
public class Aula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_aula;

    @NotEmpty
    @Column(name = "nombreAula")
    private String nombreAula;

    @Column(name = "cursoAula")
    @NotEmpty
    private String cursoAula;


    @JoinTable(
            name = "aula_administradores",
            joinColumns = @JoinColumn(name = "aula_id"),
            inverseJoinColumns = @JoinColumn(name = "individuo_id")
    )

    @ManyToMany(cascade = CascadeType.ALL)

    private List<Individuo> administradores;


    @JoinTable(
            name = "aula_usuarios",
            joinColumns = @JoinColumn(name = "aula_id"),
            inverseJoinColumns = @JoinColumn(name = "individuo_id")
    )
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Individuo> usuarios;

}