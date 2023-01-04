package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.controladores.PeticionRegistroGanadoPropio;
import espoch.ct_ganadero.datos.CabezaGanadoCRUD;
import espoch.ct_ganadero.datos.PropioCRUD;
import espoch.ct_ganadero.datos.RazaCRUD;
import espoch.ct_ganadero.logica.CabezaGanadoValidator;
import espoch.ct_ganadero.logica.FechaValidator;
import espoch.ct_ganadero.modelo.Propio;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RegistroGanadoPropio {
    
    private final CabezaGanadoCRUD cabezaGanadoCrud;
    private final PropioCRUD propioCrud;
    private final RazaCRUD razaCrud;
    
    public void registrar(PeticionRegistroGanadoPropio peticion) throws IllegalStateException, Exception {
        boolean idValido = CabezaGanadoValidator.idValido(peticion.getId());
        boolean nombreValido = CabezaGanadoValidator.nombreValido(peticion.getNombre());
        boolean sexoValido = CabezaGanadoValidator.sexoValido(peticion.getSexo());
        boolean estadoValido = CabezaGanadoValidator.estadoValido(peticion.getEstado());
        boolean fechaNacValida = FechaValidator.fechaValida(peticion.getFecha_nacimiento());
        
        if (!idValido)
            throw new IllegalStateException("El Id ingresado no es valido");
        if (!razaCrud.contiene(peticion.getId_raza()))
            throw new IllegalStateException("No se ha encontrado la raza ingresada");
        if (!nombreValido)
            throw new IllegalStateException("Se ha ingresado un nombre no permitido");
        if (!sexoValido)
            throw new IllegalStateException("El valor para el campo Sexo no es valido");
        if (!cabezaGanadoCrud.contiene(peticion.getId_padre()))
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como padre");
        if (!cabezaGanadoCrud.contiene(peticion.getId_madre()))
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como madre");
        if (!estadoValido)
            throw new IllegalStateException("El valor ingresado no es un estado valido");
        if (!fechaNacValida)
            throw new IllegalStateException("La fecha de nacimiento ingresada no es valida");
        
        Propio cabezaGanadoPropio = new Propio(
                cabezaGanadoCrud.buscar(peticion.getId_padre()), 
                cabezaGanadoCrud.buscar(peticion.getId_madre()), 
                peticion.getEstado(), 
                FechaValidator.StringACalendar(peticion.getFecha_nacimiento()), 
                peticion.getId(), 
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );
        
        propioCrud.guardar(cabezaGanadoPropio);
    }
    
    public List<Propio> listar() {
        return propioCrud.listar();
    }
}
