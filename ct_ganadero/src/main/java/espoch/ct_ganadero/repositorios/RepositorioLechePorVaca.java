package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.LechePorVaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLechePorVaca extends JpaRepository<LechePorVaca, Integer> {
    
    @Query(value = "SELECT * FROM leche_por_vaca WHERE id_cabeza_ganado = ?1 and id_registro = TO_DATE(?2,\'YYYY-MM-DD\')", nativeQuery = true)
    LechePorVaca findByVacaAndRegistro(String idCabezaGanado, String idRegistro);
}
