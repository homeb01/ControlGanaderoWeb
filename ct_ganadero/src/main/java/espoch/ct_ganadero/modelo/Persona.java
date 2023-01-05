package espoch.ct_ganadero.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "persona")
public class Persona implements Serializable {
    
    @Id
    @Column(name = "cedula_ruc")
    String cedulaRUC;
    
    @Column(name = "nombre")
    String nombre;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    Calendar fechaNacimiento;
    
    @OneToOne(mappedBy = "persona")
    Usuario usuario;
    
    @Column(name = "sexo")
    char sexo;
    
    @Column(name = "ciudad")
    String ciudadProcedencia;

    public Persona() {
    }

    public Persona(String cedulaRUC, String nombre, Calendar fechaNacimiento, char sexo, String ciudadProcedencia) {
        this.cedulaRUC = cedulaRUC;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.ciudadProcedencia = ciudadProcedencia;
        this.usuario = usuario;
    }

    public String getCedulaRUC() {
        return cedulaRUC;
    }

    public String getNombre() {
        return nombre;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    public char getSexo() {
        return sexo;
    }

    public String getCiudadProcedencia() {
        return ciudadProcedencia;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setCedulaRUC(String cedulaRUC) {
        this.cedulaRUC = cedulaRUC;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setCiudadProcedencia(String ciudadProcedencia) {
        this.ciudadProcedencia = ciudadProcedencia;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        final Persona other = (Persona) obj;

        return Objects.equals(this.cedulaRUC, other.cedulaRUC);
    }

    @Override
    public String toString() {
        return "Persona{" + "cedulaRUC=" + cedulaRUC + ", nombre=" + nombre + ", fechaNacimiento=" + fechaNacimiento + ", sexo=" + sexo + ", ciudadProcedencia=" + ciudadProcedencia + '}';
    }
}
