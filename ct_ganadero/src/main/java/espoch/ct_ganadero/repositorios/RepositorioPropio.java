package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Propio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioPropio extends JpaRepository<Propio, Integer> {
    
}
