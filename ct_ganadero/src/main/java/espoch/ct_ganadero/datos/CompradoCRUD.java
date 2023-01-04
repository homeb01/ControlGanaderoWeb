package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Comprado;
import espoch.ct_ganadero.modelo.Procedencia;
import espoch.ct_ganadero.modelo.Raza;
import espoch.ct_ganadero.repositorios.RepositorioCabezaGanado;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompradoCRUD implements iCRUD<Comprado> {
    
    @Autowired
    private RepositorioCabezaGanado<Comprado> repComprado;

    @Override
    public List<Comprado> listar() {
        return (List<Comprado>) repComprado.findAll();
    }

    @Override
    public void guardar(Comprado elemento) {
        Raza raza = elemento.getRaza();
        Procedencia procedencia = elemento.getProcedencia();
        raza.suscribirCabezaGanado(elemento);
        procedencia.suscribirCabezaGanadoAjeno(elemento);
        repComprado.save(elemento);
    }

    @Override
    public boolean contiene(Comprado elemento) {
        Comprado cabezaGanadoCompradaBuscada = repComprado.findById(elemento.getId()).get();
        return !(cabezaGanadoCompradaBuscada == null);
    }
    
    public boolean contiene(String id) {
        Comprado cabezaGanadoCompradaBuscada = repComprado.findById(id).get();
        return !(cabezaGanadoCompradaBuscada == null);
    }

    @Override
    public void modificar(Comprado elemento, Comprado nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(Comprado elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        repComprado.delete(elemento);
    }

    @Override
    public Comprado buscar(Comprado elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        return repComprado.findById(elemento.getId()).get();
    }
    
    public Comprado buscar(String id) throws Exception {
        if (!contiene(id))
            throw new Exception("Elemento no fue encontrado!");
        return repComprado.findById(id).get();
    }
}
