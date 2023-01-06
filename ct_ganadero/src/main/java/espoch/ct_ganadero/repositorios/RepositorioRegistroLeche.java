package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.RegistroLeche;
import java.util.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioRegistroLeche extends JpaRepository<RegistroLeche, Calendar> {
    @Modifying
    @Query(value = "UPDATE registro_leche SET total_terneros=?1, total_sobrante=?2 WHERE id_registro = TO_DATE(?3,\'YYYY-MM-DD\')", nativeQuery = true)
    void actualizarTotalTerneros(float totalTerneros, float totalSobrante, String idRegistro);
}
