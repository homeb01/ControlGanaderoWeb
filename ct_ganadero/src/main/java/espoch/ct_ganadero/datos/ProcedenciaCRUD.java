package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Procedencia;
import espoch.ct_ganadero.repositorios.RepositorioProcedencia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedenciaCRUD implements iCRUD<Procedencia> {
    
    @Autowired
    private RepositorioProcedencia repProcedencia;

    @Override
    public List<Procedencia> listar() {
        return (List<Procedencia>) repProcedencia.findAll();
    }

    @Override
    public void guardar(Procedencia elemento) {
        repProcedencia.save(elemento);
    }

    @Override
    public boolean contiene(Procedencia elemento) {
        Procedencia procedenciaBuscada = repProcedencia.findById(elemento.getId()).get();
        return !(procedenciaBuscada == null);
    }
    
    public boolean contiene(int id) {
        Procedencia procedenciaBuscada = repProcedencia.findById(id).get();
        return !(procedenciaBuscada == null);
    }

    @Override
    public void modificar(Procedencia elemento, Procedencia nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(Procedencia elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        repProcedencia.delete(elemento);
    }

    @Override
    public Procedencia buscar(Procedencia elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        return repProcedencia.findById(elemento.getId()).get();
    }
    
    public Procedencia buscar(int id) throws Exception {
        if (!contiene(id))
            throw new Exception("Elemento no fue encontrado!");
        return repProcedencia.findById(id).get();
    }
}