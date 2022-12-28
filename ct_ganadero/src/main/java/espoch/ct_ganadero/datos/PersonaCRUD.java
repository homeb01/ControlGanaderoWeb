package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Persona;
import espoch.ct_ganadero.repositorios.RepositorioPersona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaCRUD implements iCRUD<Persona> {
    
    @Autowired
    RepositorioPersona repositorio;

    @Override
    public List<Persona> listar() {
        return (List<Persona>) repositorio.findAll();
    }

    @Override
    public void guardar(Persona elemento) {
        repositorio.save(elemento);
    }

    @Override
    public boolean contiene(Persona elemento) {
        Persona buscado = repositorio.findById(elemento.getCedulaRUC()).get();
        return !(buscado == null);
    }

    @Override
    public void modificar(Persona elemento, Persona nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(Persona elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.delete(elemento);
    }

    @Override
    public Persona buscar(Persona elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(elemento.getCedulaRUC()).get();
    }
}
