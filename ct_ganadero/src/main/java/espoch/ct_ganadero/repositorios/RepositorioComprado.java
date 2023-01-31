package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Comprado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioComprado extends JpaRepository<Comprado, Integer> {
    
}
