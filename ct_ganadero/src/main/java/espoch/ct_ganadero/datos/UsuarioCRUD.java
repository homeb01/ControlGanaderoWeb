package espoch.ct_ganadero.datos;

import espoch.ct_ganadero.modelo.Usuario;
import espoch.ct_ganadero.repositorios.RepositorioUsuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioCRUD implements iCRUD<Usuario> {
    
    @Autowired
    private RepositorioUsuario repositorio;

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) repositorio.findAll();
    }

    @Override
    public void guardar(Usuario elemento) {
        repositorio.save(elemento);
    }

    @Override
    public boolean contiene(Usuario elemento) {
        Usuario buscado = repositorio.findById(elemento.getUser()).orElse(null);
        return !(buscado == null);
    }
    
    public boolean contiene(String id) {
        Usuario buscado = repositorio.findById(id).orElse(null);
        return !(buscado == null);
    }

    @Override
    public void modificar(Usuario elemento, Usuario nuevoElemento) throws Exception {
        eliminar(elemento);
        guardar(nuevoElemento);
    }

    @Override
    public void eliminar(Usuario elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        repositorio.delete(elemento);
    }

    @Override
    public Usuario buscar(Usuario elemento) throws Exception {
        if (!contiene(elemento)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(elemento.getUser()).get();
    }
    
    public Usuario buscar(String id) throws Exception {
        if (!contiene(id)) {
            throw new Exception("Elemento no fue encontrado!");
        }
        return repositorio.findById(id).get();
    }
}
