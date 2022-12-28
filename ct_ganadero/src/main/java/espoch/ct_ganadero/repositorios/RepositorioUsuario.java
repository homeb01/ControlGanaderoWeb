package espoch.ct_ganadero.repositorios;

import espoch.ct_ganadero.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, String> {
}
