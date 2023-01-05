package espoch.ct_ganadero.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "raza")
public class Raza implements Serializable {
    @Id
    @Column(name = "raza")
    String raza;

    @OneToMany(mappedBy = "raza", fetch = FetchType.EAGER)
    @JsonIgnore
    List<CabezaGanado> ganado;

    public Raza() {
        this.ganado = new ArrayList<>();
    }

    public Raza(String raza, List<CabezaGanado> ganado) {
        this.ganado = new ArrayList<>();
        this.raza = raza;
        this.ganado = ganado;
    }
    
    public Raza(String raza) {
        this.ganado = new ArrayList<>();
        this.raza = raza;
    }

    public String getRaza() {
        return raza;
    }

    public List<CabezaGanado> getGanado() {
        return ganado;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setGanado(List<CabezaGanado> ganado) {
        this.ganado = ganado;
    }
    
    public void suscribirCabezaGanado(CabezaGanado cabezaGanado) {
        this.ganado.add(cabezaGanado);
        cabezaGanado.setRaza(this);
    }

    @Override
    public String toString() {
        return "Raza{" + "raza=" + raza + '}';
    }
}
