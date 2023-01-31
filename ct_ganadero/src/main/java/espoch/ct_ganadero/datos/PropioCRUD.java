package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Propio;
import espoch.ct_ganadero.modelo.Raza;
import espoch.ct_ganadero.repositorios.RepositorioPropio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PropioCRUD implements iCRUD<Propio> {

    @Autowired
    private RepositorioPropio repositorio;

    @Override
    public List<Propio> listar() {
        return (List<Propio>) repositorio.findAll();
    }

    @Override
    public void guardar(Propio elemento) {
        Raza raza = elemento.getRaza();
        raza.suscribirCabezaGanado(elemento);
        repositorio.save(elemento);
    }

    @Override
    public boolean contiene(Propio elemento) {
        Propio buscado = repositorio.findById(elemento.getId()).orElse(null);
        return !(buscado == null);
    }
    
    public boolean contiene(int id) {
        Propio buscado = repositorio.findById(id).orElse(null);
        return !(buscado == null);
    }

    @Override
    public void modificar(Propio elemento, Propio nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(Propio elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.delete(elemento);
    }

    @Override
    public Propio buscar(Propio elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(elemento.getId()).get();
    }
    
    public Propio buscar(int id) throws Exception {
        if (!contiene(id)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(id).get();
    }
}
