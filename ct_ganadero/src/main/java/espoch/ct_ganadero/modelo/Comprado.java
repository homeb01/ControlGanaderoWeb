package espoch.ct_ganadero.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "comprado")
public class Comprado extends Ajeno {
    @Column(name = "precio")
    float precio;

    public Comprado() {
    }

    public Comprado(float precio, Procedencia procedencia, int edad, Raza raza, String nombre, char sexo) {
        super(procedencia, edad, raza, nombre, sexo, "COMPRADO");
        this.precio = precio;
    }
    
    public float getPrecio() {
        return precio;
    }

    @Override
    public Procedencia getProcedencia() {
        return procedencia;
    }

    @Override
    public int getEdad() {
        return edad;
    }

    @Override
    public int getId() {
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

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public void setProcedencia(Procedencia procedencia) {
        this.procedencia = procedencia;
    }

    @Override
    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public void setId(int id) {
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
        return "Comprado{" + "precio=" + precio + '}';
    }
}
