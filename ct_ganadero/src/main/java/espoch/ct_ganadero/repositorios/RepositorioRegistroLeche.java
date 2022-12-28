package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.RegistroLeche;
import java.util.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRegistroLeche extends JpaRepository<RegistroLeche, Calendar> {
    
}
