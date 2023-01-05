package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Raza;
import espoch.ct_ganadero.repositorios.RepositorioRaza;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RazaCRUD implements iCRUD<Raza> {

    @Autowired
    private RepositorioRaza repRaza;

    @Override
    public List<Raza> listar() {
        return (List<Raza>) repRaza.findAll();
    }

    @Override
    public void guardar(Raza elemento) {
        repRaza.save(elemento);
    }

    @Override
    public boolean contiene(Raza elemento) {
        Raza razaBuscada = repRaza.findById(elemento.getRaza()).orElse(null);
        return !(razaBuscada == null);
    }
    
    public boolean contiene(String raza) {
        Raza razaBuscada = repRaza.findById(raza).orElse(null);
        return !(razaBuscada == null);
    }

    @Override
    public void modificar(Raza elemento, Raza nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(Raza elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        repRaza.delete(elemento);
    }

    @Override
    public Raza buscar(Raza elemento) throws Exception {
        if (!contiene(elemento))
            throw new Exception("Elemento no fue encontrado!");
        return repRaza.findById(elemento.getRaza()).get();
    }
    
    public Raza buscar(String raza) throws Exception {
        if (!contiene(raza))
            throw new Exception("Elemento no fue encontrado!");
        return repRaza.findById(raza).get();
    }
}
