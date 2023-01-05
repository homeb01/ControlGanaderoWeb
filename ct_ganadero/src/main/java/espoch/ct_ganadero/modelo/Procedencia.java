package espoch.ct_ganadero.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "procedencia")
public class Procedencia implements Serializable {
    @Id
    @Column(name = "id_procedencia")
    private Integer id;
    
    @OneToMany(mappedBy = "procedencia", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Ajeno> cabezasAjenas;
    
    @Column(name = "procedencia")
    private String procedencia;

    public Procedencia() {
        this.cabezasAjenas = new ArrayList<>();
    }

    public Procedencia(Integer id, List<Ajeno> cabezasAjenas, String procedencia) {
        this.cabezasAjenas = new ArrayList<>();
        this.id = id;
        this.cabezasAjenas = cabezasAjenas;
        this.procedencia = procedencia;
    }
    
    public Procedencia(Integer id, String procedencia) {
        this.cabezasAjenas = new ArrayList<>();
        this.id = id;
        this.procedencia = procedencia;
    }

    public Integer getId() {
        return id;
    }

    public List<Ajeno> getCabezasAjenas() {
        return cabezasAjenas;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCabezasAjenas(List<Ajeno> cabezasAjenas) {
        this.cabezasAjenas = cabezasAjenas;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }
    
    public void suscribirCabezaGanadoAjeno(Ajeno cabezaGanadoAjeno) {
        this.cabezasAjenas.add(cabezaGanadoAjeno);
        cabezaGanadoAjeno.setProcedencia(this);
    }

    @Override
    public String toString() {
        return "Procedencia{" + "id=" + id + ", procedencia=" + procedencia + '}';
    }
}
