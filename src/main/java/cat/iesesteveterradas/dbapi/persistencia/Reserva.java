package cat.iesesteveterradas.dbapi.persistencia;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservaID;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alojamientoID")
    private Alojamiento alojamiento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private Usuario usuario;
    
    private Date fechaInicio;
    private Date fechaFin;
    private double total;

    public Reserva() {
    }

    // Getters y Setters
    public Long getReservaID() {
        return reservaID;
    }

    // Continúa con los demás getters y setters...
}