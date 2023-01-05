package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Procedencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioProcedencia extends JpaRepository<Procedencia, Integer> {
    
}
