package espoch.ct_ganadero.datos;

import java.util.List;

public interface iCRUD<T> {
    public List<T> listar();
    public void guardar(T elemento);
    public boolean contiene(T elemento);
    public void modificar(T elemento, T nuevoElemento) throws Exception;
    public void eliminar(T elemento) throws Exception;
    public T buscar(T elemento) throws Exception;
}
