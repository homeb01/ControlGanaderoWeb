package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Prenez;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPrenez extends JpaRepository<Prenez, Integer> {
    
}
