package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPersona extends JpaRepository<Persona, String> {
    
}
