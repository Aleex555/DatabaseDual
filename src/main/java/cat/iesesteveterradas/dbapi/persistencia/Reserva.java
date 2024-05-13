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

    private String fechaInicio;
    private String fechaFin;
    private double total;

    public Reserva() {
    }

    public Reserva(Alojamiento alojamiento, Usuario usuario, String fechaInicio, String fechaFin, double total) {
        this.alojamiento = alojamiento;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.total = total;
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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}