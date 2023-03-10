package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Ajeno;
import espoch.ct_ganadero.modelo.Procedencia;
import espoch.ct_ganadero.modelo.Raza;
import espoch.ct_ganadero.repositorios.RepositorioAjeno;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AjenoCRUD implements iCRUD<Ajeno> {
    
    @Autowired
    private RepositorioAjeno repAjeno;

    @Override
    public List<Ajeno> listar() {
        return (List<Ajeno>) repAjeno.findAll();
    }

    @Override
    public void guardar(Ajeno cabezaGanadoAjeno) {
        Raza raza = cabezaGanadoAjeno.getRaza();
        Procedencia procedencia = cabezaGanadoAjeno.getProcedencia();
        raza.suscribirCabezaGanado(cabezaGanadoAjeno);
        procedencia.suscribirCabezaGanadoAjeno(cabezaGanadoAjeno);
        repAjeno.save(cabezaGanadoAjeno);
    }

    @Override
    public boolean contiene(Ajeno elemento) {
        Ajeno elementoBuscado = repAjeno.findById(elemento.getId()).orElse(null);
        return !(elementoBuscado == null);
    }
    
    public boolean contiene(int id) {
        Ajeno elementoBuscado = repAjeno.findById(id).orElse(null);
        return !(elementoBuscado == null);
    }

    @Override
    public void modificar(Ajeno elemento, Ajeno nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }
    
    @Override
    public void eliminar(Ajeno elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        repAjeno.delete(elemento);
    }

    @Override
    public Ajeno buscar(Ajeno elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        return repAjeno.findById(elemento.getId()).get();
    }
    
    public Ajeno buscar(int id) throws Exception {
        if (!contiene(id))
            throw new Exception("Elemento no fue encontrado!");
        return repAjeno.findById(id).get();
    }
}
