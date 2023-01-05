package espoch.ct_ganadero.modelo;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leche_por_vaca")
public class LechePorVaca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leche_por_vaca")
    Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_registro")
    RegistroLeche registro;

    @ManyToOne
    @JoinColumn(name = "id_cabeza_ganado")
    Propio cabezaGanado;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    Usuario usuario;
    
    @Column(name = "turno")
    String turno;
    
    @Column(name = "total")
    int total;

    public LechePorVaca() {
    }

    public LechePorVaca(RegistroLeche registro, Propio cabezaGanado, Usuario usuario, String turno, int total) {
        this.registro = registro;
        this.cabezaGanado = cabezaGanado;
        this.usuario = usuario;
        this.turno = turno;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public RegistroLeche getRegistro() {
        return registro;
    }

    public Propio getCabezaGanado() {
        return cabezaGanado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getTurno() {
        return turno;
    }

    public int getTotal() {
        return total;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRegistro(RegistroLeche registro) {
        this.registro = registro;
    }

    public void setCabezaGanado(Propio cabezaGanado) {
        this.cabezaGanado = cabezaGanado;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setTotal(int total) {
        this.total = total;
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

        final LechePorVaca other = (LechePorVaca) obj;
        
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "LechePorVaca{" + "id=" + id + ", turno=" + turno + ", total=" + total + '}';
    }
}
