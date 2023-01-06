package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.datos.ProcedenciaCRUD;
import espoch.ct_ganadero.logica.ValidacionProcedencia;
import espoch.ct_ganadero.modelo.Procedencia;
import espoch.ct_ganadero.peticiones.PeticionProcedencia;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RegistroProcedencias {
    
    private final ProcedenciaCRUD procedenciaCrud;
    
    public Procedencia registrar(PeticionProcedencia peticion) {
        boolean procedenciaValida = ValidacionProcedencia.procedenciaValida(peticion.getProcedencia());
        
        if (!procedenciaValida)
            throw new IllegalStateException("El nombre de procedencia ingresado no es valido");
        
        Procedencia procedencia = new Procedencia(peticion.getProcedencia());
        procedenciaCrud.guardar(procedencia);
        return procedencia;
    }
    
    public Procedencia modificar(int id, PeticionProcedencia peticion) throws Exception {
        boolean procedenciaValida = ValidacionProcedencia.procedenciaValida(peticion.getProcedencia());
        
        if (!procedenciaValida)
            throw new IllegalStateException("El nombre de procedencia ingresado no es valido");
        
        Procedencia procedencia = new Procedencia(peticion.getProcedencia());
        procedenciaCrud.modificar(id, procedencia);
        return procedencia;
    }
    
    public List<Procedencia> listar() {
        return procedenciaCrud.listar();
    }
        
    public Procedencia ver(int id) throws Exception {
        return procedenciaCrud.buscar(id);
    }
    
    public Procedencia eliminar(int id) throws Exception {
        return procedenciaCrud.eliminar(id);
    }
}
