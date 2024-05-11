package cat.iesesteveterradas.dbapi.persistencia;

import java.util.Date;

import jakarta.persistence.*;

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

    public Long getReservaID() {
        return reservaID;
    }

    public void setReservaID(Long reservaID) {
        this.reservaID = reservaID;
    }

    public Alojamiento getAlojamiento() {
        return alojamiento;
    }

    public void setAlojamiento(Alojamiento alojamiento) {
        this.alojamiento = alojamiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}