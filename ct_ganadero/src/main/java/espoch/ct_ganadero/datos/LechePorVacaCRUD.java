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
    
    public boolean contiene(int id) {
        LechePorVaca buscado = repositorio.findById(id).orElse(null);
        return !(buscado == null);
    }

    @Override
    public void modificar(LechePorVaca elemento, LechePorVaca nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }
    
    public void modificar(int id, LechePorVaca nuevoElemento) throws Exception {
        eliminar(id);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(LechePorVaca elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.delete(elemento);
    }
    
    public LechePorVaca eliminar(int id) throws Exception {
        if (!contiene(id)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        LechePorVaca registro = buscar(id);
        repositorio.deleteById(id);
        return registro;
    }

    @Override
    public LechePorVaca buscar(LechePorVaca elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(elemento.getId()).get();
    }
    
    public LechePorVaca buscar(int id) throws Exception {
        if (!contiene(id)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(id).get();
    }
    
    public boolean existeRegistro(int idCabezaGanado, String idRegistro, String turno) throws Exception {
        LechePorVaca registro = repositorio.findByVacaAndRegistro(idCabezaGanado, idRegistro, turno);
        return registro != null;
    }
    
    public List<LechePorVaca> listar(String idRegistro) {
        return repositorio.listByRegistro(idRegistro);
    }
    
    public String totalManana(String idRegistro) {
        return repositorio.sumarTotalManana(idRegistro);
    }
    
    public String totalTarde(String idRegistro) {
        return repositorio.sumarTotalTarde(idRegistro);
    }
}
