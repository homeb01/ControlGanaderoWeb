package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.CabezaGanado;
import espoch.ct_ganadero.modelo.Raza;
import espoch.ct_ganadero.repositorios.RepositorioCabezaGanado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CabezaGanadoCRUD implements iCRUD<CabezaGanado>{
    
    @Autowired
    private RepositorioCabezaGanado<CabezaGanado> repCabezaGanado;
    
    @Override
    public List<CabezaGanado> listar() {
        return (List<CabezaGanado>) repCabezaGanado.findAll();
    }

    @Override
    public void guardar(CabezaGanado elemento) {
        Raza raza = elemento.getRaza();
        raza.suscribirCabezaGanado(elemento);
        repCabezaGanado.save(elemento);
    }

    @Override
    public boolean contiene(CabezaGanado elemento) {
        CabezaGanado cabezaGanadoCompradaBuscada = repCabezaGanado.findById(elemento.getId()).orElse(null);
        return !(cabezaGanadoCompradaBuscada == null);
    }
    
    public boolean contiene(String id) {
        CabezaGanado cabezaGanadoCompradaBuscada = repCabezaGanado.findById(id).orElse(null);
        return !(cabezaGanadoCompradaBuscada == null);
    }

    @Override
    public void modificar(CabezaGanado elemento, CabezaGanado nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(CabezaGanado elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        repCabezaGanado.delete(elemento);
    }
    
    public CabezaGanado eliminar(String id) throws Exception {
        if (!contiene(id))
            throw new Exception("Elemento no fue encontrado!");
        CabezaGanado cabezaGanado = buscar(id);
        repCabezaGanado.deleteById(id);
        return cabezaGanado;
    }

    @Override
    public CabezaGanado buscar(CabezaGanado elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        return repCabezaGanado.findById(elemento.getId()).get();
    }
    
    public CabezaGanado buscar(String id) throws Exception {
        if (!contiene(id))
            throw new Exception("Elemento no fue encontrado!");
        return repCabezaGanado.findById(id).get();
    }
}
