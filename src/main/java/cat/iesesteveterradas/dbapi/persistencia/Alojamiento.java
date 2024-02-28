package cat.iesesteveterradas.dbapi.persistencia;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alojamiento")
public class Alojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alojamientoID;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresaID")
    private Empresa empresa;
    
    private String nombre;
    private String descripción;
    private String dirección;
    private int capacidad;
    private String reglas;
    private double precioPorNoche;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas = new HashSet<>();

    public Alojamiento() {
    }

    // Getters y Setters
    public Long getAlojamientoID() {
        return alojamientoID;
    }

    // Continúa con los demás getters y setters...
}
