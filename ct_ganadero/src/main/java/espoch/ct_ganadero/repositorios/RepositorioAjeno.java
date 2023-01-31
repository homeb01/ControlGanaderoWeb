package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Ajeno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioAjeno extends JpaRepository<Ajeno, Integer> {
    
}
