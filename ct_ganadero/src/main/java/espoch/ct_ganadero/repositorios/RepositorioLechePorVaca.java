package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.LechePorVaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLechePorVaca extends JpaRepository<LechePorVaca, Integer> {
    
}
