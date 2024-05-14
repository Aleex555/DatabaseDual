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

import org.hibernate.sql.ast.spi.StringBuilderSqlAppender;

@Entity
@Table(name = "propietario")
public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long propietarioID;

    private String nombre;
    private String emailContacto;
    private String telefonoContacto;
    private String contrasena;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Alojamiento> alojamientos = new HashSet<>();

    public Propietario(String nombre, String email, String telefono, String contrasena) {
        this.nombre = nombre;
        this.emailContacto = email;
        this.telefonoContacto = telefono;
        this.contrasena = contrasena;
    }

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

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
