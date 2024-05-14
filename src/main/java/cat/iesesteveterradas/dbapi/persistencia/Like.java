package cat.iesesteveterradas.dbapi.persistencia;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "usuario_alojamiento_like")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuarioID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "alojamientoID")
    private Alojamiento alojamiento;

    // Constructors, getters, and setters
    public Like() {
    }

    public Like(Usuario usuario, Alojamiento alojamiento) {
        this.usuario = usuario;
        this.alojamiento = alojamiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }
}
