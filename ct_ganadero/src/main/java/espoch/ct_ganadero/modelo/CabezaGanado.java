package espoch.ct_ganadero.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cabeza_de_ganado")
public class CabezaGanado implements Serializable {
    @Id
    @Column(name = "id_cabeza_ganado")
    String id;
    
    @ManyToOne
    @JoinColumn(name = "id_raza")
    Raza raza;
    
    @Column(name = "nombre")
    String nombre;
    
    @Column(name = "sexo")
    char sexo;

    public CabezaGanado() {
    }

    public CabezaGanado(String id, Raza raza, String nombre, char sexo) {
        this.id = id;
        this.raza = raza;
        this.nombre = nombre;
        this.sexo = sexo;
    }

    public String getId() {
        return id;
    }

    public Raza getRaza() {
        return raza;
    }

    public String getNombre() {
        return nombre;
    }

    public char getSexo() {
        return sexo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "CabezaGanado{" + "id=" + id + ", raza=" + raza.raza + ", nombre=" + nombre + ", sexo=" + sexo + '}';
    }
}
