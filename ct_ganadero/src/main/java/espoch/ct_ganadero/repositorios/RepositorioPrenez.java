package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Prenez;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPrenez extends JpaRepository<Prenez, Integer> {
    @Modifying
    @Query(value = "UPDATE prenez SET fecha_parto=TO_DATE(?1,\'YYYY-MM-DD\'), estado=?2 WHERE id_prenez=?3", nativeQuery = true)
    void actualizarFechaParto(String fechaParto, String estado, int id);
    
    @Query(value = "SELECT COUNT(*) as ct FROM public.prenez WHERE id_cabeza_ganado = ?1 AND estado like 'PRENADA'", nativeQuery = true)
    int ctProcesosPrenez(int id);
}
