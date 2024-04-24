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
    private String descripcion;
    private String direccion;
    private int capacidad;
    private String reglas;
    private double precioPorNoche;
    private String urlFoto;
    private int puntuaje;
    private int puntos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "propietarioID")
    private Propietario propietario;


    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reserva> reservas = new HashSet<>();

    public Alojamiento() {
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

    public int getPuntuaje() {
        return puntuaje;
    }

    // Setter para capacidad
    public void setPuntuaje(int puntuaje) {
        this.puntuaje = puntuaje;
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

    // Getter para urlFoto
    public String getUrlFoto() {
        return urlFoto;
    }

    // Setter para urlFoto
    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    // Setter for propietario
    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
