package cat.iesesteveterradas.dbapi.persistencia;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "alojamiento")
public class Alojamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alojamientoID;
    
    private String nombre;
    private String descripción;
    private String dirección;
    private int capacidad;
    private String reglas;
    private double precioPorNoche;
    private String urlFoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietarioID")
    private Propietario propietario;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas = new HashSet<>();

    public Alojamiento() {
    }

    public Long getAlojamientoID() {
        return alojamientoID;
    }
}
