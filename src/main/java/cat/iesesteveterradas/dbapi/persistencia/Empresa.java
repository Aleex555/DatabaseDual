package cat.iesesteveterradas.dbapi.persistencia;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empresaID;
    
    private String nombre;
    private String emailContacto;
    private String teléfonoContacto;
    private String dirección;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Alojamiento> alojamientos = new HashSet<>();

    public Empresa() {
    }

    // Getters y Setters
    public Long getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(Long empresaID) {
        this.empresaID = empresaID;
    }

    public String getNombre() {
        return nombre;
    }

    // Continúa con los demás getters y setters...
}
