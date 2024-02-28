package cat.iesesteveterradas.dbapi.persistencia;

import javax.persistence.*;
import java.util.Date;

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