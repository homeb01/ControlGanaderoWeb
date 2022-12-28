package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Prenez;
import espoch.ct_ganadero.modelo.Propio;
import espoch.ct_ganadero.repositorios.RepositorioPrenez;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenezCRUD implements iCRUD<Prenez> {
    
    @Autowired
    private RepositorioPrenez repositorio;

    @Override
    public List<Prenez> listar() {
        return (List<Prenez>) repositorio.findAll();
    }

    @Override
    public void guardar(Prenez elemento) {
        Propio cabezaGanado = elemento.getCabezaGanado();
        cabezaGanado.suscribirPrenez(elemento);
        repositorio.save(elemento);
    }

    @Override
    public boolean contiene(Prenez elemento) {
        Prenez buscado = repositorio.findById(elemento.getId()).get();
        return !(buscado == null);
    }

    @Override
    public void modificar(Prenez elemento, Prenez nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(Prenez elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.delete(elemento);
    }

    @Override
    public Prenez buscar(Prenez elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(elemento.getId()).get();
    }
}
