package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.RegistroLeche;
import espoch.ct_ganadero.repositorios.RepositorioRegistroLeche;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroLecheCRUD implements iCRUD<RegistroLeche> {
    
    @Autowired
    private RepositorioRegistroLeche repositorio;

    @Override
    public List<RegistroLeche> listar() {
        return (List<RegistroLeche>) repositorio.findAllByOrderByFechaRegistroDesc();
    }

    @Override
    public void guardar(RegistroLeche elemento) {
        repositorio.save(elemento);
    }

    @Override
    public boolean contiene(RegistroLeche elemento) {
        RegistroLeche buscado = repositorio.findById(elemento.getFechaRegistro()).orElse(null);
        return !(buscado == null);
    }
    
    public boolean contiene(Calendar id) {
        RegistroLeche buscado = repositorio.findById(id).orElse(null);
        return !(buscado == null);
    }

    @Override
    public void modificar(RegistroLeche elemento, RegistroLeche nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }
    
    public void modificar(Calendar id, RegistroLeche nuevoElemento) throws Exception {
        eliminar(id);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(RegistroLeche elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.delete(elemento);
    }
    
    public void eliminar(Calendar id) throws Exception {
        if (!contiene(id)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.deleteById(id);
    }

    @Override
    public RegistroLeche buscar(RegistroLeche elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(elemento.getFechaRegistro()).get();
    }
    
    public RegistroLeche buscar(Calendar id) throws Exception {
        if (!contiene(id)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(id).get();
    }
    
    public void actualizarTotalTerneros(float totalTerneros, float totalSobrante, String idRegistro) {
        repositorio.actualizarTotalTerneros(totalTerneros, totalSobrante, idRegistro);
    }
}
