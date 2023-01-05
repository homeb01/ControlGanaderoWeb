package espoch.ct_ganadero.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "registro_leche")
public class RegistroLeche implements Serializable {
    
    @Id
    @Column(name = "id_registro")   
    @Temporal(TemporalType.DATE)
    Calendar fechaRegistro;
    
    @Column(name = "total_terneros")
    int totalTernero;
    
    @Column(name = "total_sobrante")
    int totalSobrante;
    
    @OneToMany(mappedBy = "registro")
    List<LechePorVaca> registro;

    public RegistroLeche() {
    }

    public RegistroLeche(Calendar fechaRegistro, int totalTernero, int totalSobrante) {
        this.fechaRegistro = fechaRegistro;
        this.totalTernero = totalTernero;
        this.totalSobrante = totalSobrante;
    }

    public Calendar getFechaRegistro() {
        return fechaRegistro;
    }

    public int getTotalTernero() {
        return totalTernero;
    }

    public int getTotalSobrante() {
        return totalSobrante;
    }

    public void setFechaRegistro(Calendar fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setTotalTernero(int totalTernero) {
        this.totalTernero = totalTernero;
    }

    public void setTotalSobrante(int totalSobrante) {
        this.totalSobrante = totalSobrante;
    }

    public List<LechePorVaca> getRegistro() {
        return registro;
    }

    public void setRegistro(List<LechePorVaca> registro) {
        this.registro = registro;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
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
        final RegistroLeche other = (RegistroLeche) obj;
        return Objects.equals(this.fechaRegistro, other.fechaRegistro);
    }

    @Override
    public String toString() {
        return "RegistroLeche{ fechaRegistro=" + fechaRegistro + ", totalTernero=" + totalTernero + ", totalSobrante=" + totalSobrante + '}';
    }
    
    public void suscribirLecheXVaca(LechePorVaca lechePorVaca) {
        this.registro.add(lechePorVaca);
        lechePorVaca.setRegistro(this);
    }
}
