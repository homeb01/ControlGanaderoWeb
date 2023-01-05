package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.LechePorVaca;
import espoch.ct_ganadero.modelo.RegistroLeche;
import espoch.ct_ganadero.repositorios.RepositorioLechePorVaca;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LechePorVacaCRUD implements iCRUD<LechePorVaca> {

    @Autowired
    private RepositorioLechePorVaca repositorio;

    @Override
    public List<LechePorVaca> listar() {
        return (List<LechePorVaca>) repositorio.findAll();
    }

    @Override
    public void guardar(LechePorVaca elemento) {
        RegistroLeche registro = elemento.getRegistro();
        registro.suscribirLecheXVaca(elemento);
        repositorio.save(elemento);
    }

    @Override
    public boolean contiene(LechePorVaca elemento) {
        LechePorVaca buscado = repositorio.findById(elemento.getId()).orElse(null);
        return !(buscado == null);
    }

    @Override
    public void modificar(LechePorVaca elemento, LechePorVaca nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(LechePorVaca elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.delete(elemento);
    }

    @Override
    public LechePorVaca buscar(LechePorVaca elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(elemento.getId()).get();
    }
    
    public boolean existeRegistro(String idCabezaGanado, String idRegistro) throws Exception {
        LechePorVaca registro = repositorio.findByVacaAndRegistro(idCabezaGanado, idRegistro);
        return registro != null;
    }
}
