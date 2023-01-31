package espoch.ct_ganadero.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    
    @ManyToOne
    @JoinColumn(name = "id_raza")
    Raza raza;
    
    @Column(name = "nombre")
    String nombre;
    
    @Column(name = "sexo")
    char sexo;
    
    @Column(name = "tipo")
    String tipo;
    
    @Column(name = "estado")
    String estado;

    public CabezaGanado() {
    }

    public CabezaGanado(Raza raza, String nombre, char sexo, String tipo) {
        this.raza = raza;
        this.nombre = nombre;
        this.sexo = sexo;
        this.tipo = tipo;
        this.estado = "VIVA";
    }

    public int getId() {
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
    
    public String getTipo() {
        return tipo;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setId(int id) {
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
