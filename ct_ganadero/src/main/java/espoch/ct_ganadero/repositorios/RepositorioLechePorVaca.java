package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.LechePorVaca;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioLechePorVaca extends JpaRepository<LechePorVaca, Integer> {
    
    @Query(value = "SELECT * FROM leche_por_vaca WHERE id_cabeza_ganado = ?1 and id_registro = TO_DATE(?2,\'YYYY-MM-DD\') and turno = ?3", nativeQuery = true)
    LechePorVaca findByVacaAndRegistro(int idCabezaGanado, String idRegistro, String turno);
    
    @Query(value = "SELECT * FROM leche_por_vaca WHERE id_registro = TO_DATE(?1,\'YYYY-MM-DD\') ", nativeQuery = true)
    List<LechePorVaca> listByRegistro(String idRegistro);
    
    @Query(value = "SELECT SUM(total) FROM leche_por_vaca GROUP BY id_registro, turno HAVING id_registro=TO_DATE(?1,\'YYYY-MM-DD\') and turno='M'", nativeQuery = true)
    String sumarTotalManana(String idRegistro);
    
    @Query(value = "SELECT SUM(total) FROM leche_por_vaca GROUP BY id_registro, turno HAVING id_registro=TO_DATE(?1,\'YYYY-MM-DD\') and turno='T'", nativeQuery = true)
    String sumarTotalTarde(String idRegistro);
}
