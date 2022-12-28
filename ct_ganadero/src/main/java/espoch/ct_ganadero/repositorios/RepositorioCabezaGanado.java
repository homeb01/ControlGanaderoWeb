package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.CabezaGanado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCabezaGanado<T extends CabezaGanado> extends JpaRepository<T, String> {
    
}
