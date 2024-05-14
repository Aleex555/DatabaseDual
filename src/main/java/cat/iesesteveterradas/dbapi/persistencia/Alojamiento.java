package cat.iesesteveterradas.dbapi.persistencia;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
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
    private String descripcion;
    private String direccion;
    private int capacidad;
    private String reglas;
    private double precioPorNoche;
    private int totalLikes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propietarioID")
    private Propietario propietario;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "foto_alojamiento", joinColumns = @JoinColumn(name = "alojamientoID"))
    @Column(name = "urlFoto")
    private Set<String> urlFotos = new HashSet<>();

    public Alojamiento(String nombre, String descripcion, String direccion, int capacidad, String reglas,
            double precioPorNoche, Set<String> urlFotos, int totalLikes, Propietario propietario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.reglas = reglas;
        this.precioPorNoche = precioPorNoche;
        this.urlFotos = urlFotos;
        this.totalLikes = totalLikes;
        this.propietario = propietario;
    }

    public Alojamiento() {
    }

    @ManyToMany(mappedBy = "alojamientosLiked")
    private Set<Usuario> usuariosLikes = new HashSet<>();

    public Set<Usuario> getUsuariosLikes() {
        return usuariosLikes;
    }

    public void setUsuariosLikes(Set<Usuario> usuariosLikes) {
        this.usuariosLikes = usuariosLikes;
    }

    public Long getAlojamientoID() {
        return alojamientoID;
    }

    public String getNombre() {
        return nombre;
    }

    // Setter para nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Getter para descripcion
    public String getDescripcion() {
        return descripcion;
    }

    // Setter para descripcion
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getter para direccion
    public String getDireccion() {
        return direccion;
    }

    // Setter para direccion
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Getter para capacidad
    public int getCapacidad() {
        return capacidad;
    }

    // Setter para capacidad
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    // Setter para capacidad
    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    // Getter para reglas
    public String getReglas() {
        return reglas;
    }

    // Setter para reglas
    public void setReglas(String reglas) {
        this.reglas = reglas;
    }

    // Getter para precioPorNoche
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    // Setter para precioPorNoche
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public Set<String> getUrlFotos() {
        return urlFotos;
    }

    public void setUrlFotos(Set<String> urlFotos) {
        this.urlFotos = urlFotos;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    // Setter for propietario
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    // Método para agregar una reserva
    public void addReserva(Reserva reserva) {
        reservas.add(reserva);
        reserva.setAlojamiento(this);
    }

    // Método para eliminar una reserva
    public void removeReserva(Reserva reserva) {
        reservas.remove(reserva);
        reserva.setAlojamiento(null);
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    // Métodos para actualizar los likes
    public void incrementLikes() {
        this.totalLikes += 1;
    }

    public void decrementLikes() {
        if (this.totalLikes > 0) {
            this.totalLikes -= 1;
        }
    }
}
