package cat.iesesteveterradas.dbapi.persistencia;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "propietario")
public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propietarioID;
    
    private String nombre;
    private String emailContacto;
    private String teléfonoContacto;
    private String dirección;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Alojamiento> alojamientos = new HashSet<>();

    public Propietario() {
    }

    // Getters y Setters
    public Long getPropietarioID() {
        return propietarioID;
    }

    public void setPropietarioID(Long propietarioID) {
        this.propietarioID = propietarioID;
    }

    public String getNombre() {
        return nombre;
    }

    
}
