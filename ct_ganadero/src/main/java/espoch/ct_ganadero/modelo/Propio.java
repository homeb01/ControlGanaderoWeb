package espoch.ct_ganadero.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Calendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "propio")
public class Propio extends CabezaGanado {
    
    @ManyToOne()
    @JoinColumn(name = "id_padre")
    CabezaGanado padre;
    
    @ManyToOne()
    @JoinColumn(name = "id_madre")
    CabezaGanado madre;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    Calendar fechaNacimiento;
    
    @OneToMany(mappedBy = "cabezaGanado")
    @JsonIgnore
    List<Prenez> prenez;

    public Propio() {
    }

    public Propio(CabezaGanado padre, CabezaGanado madre, Calendar fechaNacimiento, Raza raza, String nombre, char sexo) {
        super(raza, nombre, sexo, "PROPIO");
        this.padre = padre;
        this.madre = madre;
        this.fechaNacimiento = fechaNacimiento;
    }

    public CabezaGanado getPadre() {
        return padre;
    }

    public CabezaGanado getMadre() {
        return madre;
    }

    public String getEstado() {
        return estado;
    }

    public Calendar getFechaNacimiento() {
        return fechaNacimiento;
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

    public void setPadre(CabezaGanado padre) {
        this.padre = padre;
    }

    public void setMadre(CabezaGanado madre) {
        this.madre = madre;
    }

    @Override
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaNacimiento(Calendar fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Prenez> getPrenez() {
        return prenez;
    }

    public void setPrenez(List<Prenez> prenez) {
        this.prenez = prenez;
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
        return "Propio{" + "padre=" + padre + ", madre=" + madre + ", estado=" + estado + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
    
    public void suscribirPrenez(Prenez prenez) {
        this.prenez.add(prenez);
        prenez.setCabezaGanado(this);
    }
}
