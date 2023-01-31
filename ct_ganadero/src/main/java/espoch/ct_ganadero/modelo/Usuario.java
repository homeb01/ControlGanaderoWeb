package espoch.ct_ganadero.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    
    @Id
    @Column(name = "usuario")
    String user;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cedula_ruc")
    Persona persona;
    
    @Column(name = "contrasena")
    String password;
    
    @Column(name = "rol")
    String rol;

    public Usuario() {
    }

    public Usuario(Persona persona, String user, String password, String rol) {
        this.persona = persona;
        this.user = user;
        this.password = password;
        this.rol = rol;
    }

    public Persona getPersona() {
        return persona;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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

        final Usuario other = (Usuario) obj;
        
        return Objects.equals(this.user, other.user);
    }
    
    
}
