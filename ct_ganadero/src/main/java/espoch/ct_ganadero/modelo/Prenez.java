package espoch.ct_ganadero.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "prenez")
public class Prenez implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prenez")
    Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_cabeza_ganado")
    Propio cabezaGanado;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inseminacion")
    Calendar fechaInseminacion;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_parto")
    Calendar fechaParto;
    
    @Column(name = "estado")
    String estado;

    public Prenez() {
    }

    public Prenez(Propio cabezaGanado, Calendar fechaInseminacion, Calendar fechaParto, String estado) {
        this.cabezaGanado = cabezaGanado;
        this.fechaInseminacion = fechaInseminacion;
        this.fechaParto = fechaParto;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public Propio getCabezaGanado() {
        return cabezaGanado;
    }

    public Calendar getFechaInseminacion() {
        return fechaInseminacion;
    }

    public Calendar getFechaParto() {
        return fechaParto;
    }

    public void setCabezaGanado(Propio cabezaGanado) {
        this.cabezaGanado = cabezaGanado;
    }

    public void setFechaInseminacion(Calendar fechaInseminacion) {
        this.fechaInseminacion = fechaInseminacion;
    }

    public void setFechaParto(Calendar fechaParto) {
        this.fechaParto = fechaParto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
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

        final Prenez other = (Prenez) obj;
        return Objects.equals(this.id, other.id);
    }
    
    

}
