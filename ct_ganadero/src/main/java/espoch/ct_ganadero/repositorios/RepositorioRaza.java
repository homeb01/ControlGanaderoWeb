package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Raza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRaza extends JpaRepository<Raza, String> {
    
}
