package espoch.ct_ganadero.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ajeno")
@Inheritance(strategy = InheritanceType.JOINED)
public class Ajeno extends CabezaGanado {
    @ManyToOne
    @JoinColumn(name = "id_procedencia")
    Procedencia procedencia;
    
    @Column(name = "edad")
    int edad;

    public Ajeno() {
    }

    public Ajeno(Procedencia procedencia, int edad) {
        this.procedencia = procedencia;
        this.edad = edad;
    }

    public Ajeno(Procedencia procedencia, int edad, String id, Raza raza, String nombre, char sexo) {
        super(id, raza, nombre, sexo);
        this.procedencia = procedencia;
        this.edad = edad;
    }

    public Procedencia getProcedencia() {
        return procedencia;
    }

    public int getEdad() {
        return edad;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Raza getRaza() {
        return raza;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public char getSexo() {
        return sexo;
    }

    public void setProcedencia(Procedencia procedencia) {
        this.procedencia = procedencia;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Ajeno{" + "procedencia=" + procedencia.getProcedencia() + ", edad=" + edad + '}';
    }
    
    
}
