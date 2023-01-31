package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.datos.PrenezCRUD;
import espoch.ct_ganadero.datos.PropioCRUD;
import espoch.ct_ganadero.logica.ValidacionCabezaGanado;
import espoch.ct_ganadero.logica.ValidacionFecha;
import espoch.ct_ganadero.logica.ValidacionPrenez;
import espoch.ct_ganadero.modelo.Prenez;
import espoch.ct_ganadero.peticiones.PeticionPrenez;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RegistroPrenez {
    
    private final PrenezCRUD prenezCrud;
    private final PropioCRUD propioCrud;
    
    public Prenez registrar(PeticionPrenez peticion) throws Exception {
        boolean idGanadoValida = ValidacionCabezaGanado.idValido(peticion.getIdCabezaGanado());
        boolean fechaInseValida = ValidacionFecha.fechaValida(peticion.getFechaInseminacion());
        
        if (!idGanadoValida)
            throw new IllegalStateException("El Id de ganado ingresado no es valido");
        if (!propioCrud.contiene(Integer.parseInt(peticion.getIdCabezaGanado())))
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado especificada");
        if (!fechaInseValida)
            throw new IllegalStateException("La fecha de inseminacion no es valida");
        
        Prenez prenez = new Prenez(
                propioCrud.buscar(Integer.parseInt(peticion.getIdCabezaGanado())), 
                ValidacionFecha.StringACalendar(peticion.getFechaInseminacion()), 
                null, 
                ValidacionPrenez.PRENADA
        );
        
        prenezCrud.guardar(prenez);
        return prenez;
    }
    
    public Prenez actualizarProduccion(PeticionPrenez peticion) throws Exception {
        boolean fechaPartoValida = ValidacionFecha.fechaValida(peticion.getFechaParto());
        if (!fechaPartoValida)
            throw new IllegalStateException("La fecha de parto no es valida");
        
        Prenez prenez = prenezCrud.buscar(peticion.getId());
        prenez.setEstado(ValidacionPrenez.PRODUCCION);
        prenez.setFechaParto(ValidacionFecha.StringACalendar(peticion.getFechaParto()));
        prenezCrud.actualizarFechaYEstado(peticion.getFechaParto(), ValidacionPrenez.PRODUCCION, peticion.getId());
        return prenez;
    }
    
    public Prenez actualizarAborto(PeticionPrenez peticion) throws Exception {
        boolean fechaPartoValida = ValidacionFecha.fechaValida(peticion.getFechaParto());
        if (!fechaPartoValida)
            throw new IllegalStateException("La fecha de parto no es valida");
        
        Prenez prenez = prenezCrud.buscar(peticion.getId());
        prenez.setEstado(ValidacionPrenez.ABORTO);
        prenez.setFechaParto(ValidacionFecha.StringACalendar(peticion.getFechaParto()));
        prenezCrud.actualizarFechaYEstado(peticion.getFechaParto(), ValidacionPrenez.ABORTO, peticion.getId());
        return prenez;
    }
    
    public Prenez eliminar(int id) throws Exception {
        return prenezCrud.eliminar(id);
    }
    
    public List<Prenez> listar() {
        return prenezCrud.listar();
    }
    
    public Prenez ver(int id) throws Exception {
        return prenezCrud.buscar(id);
    }
    
    public boolean estaPrenada(int id) {
        return prenezCrud.estaPrenada(id);
    }
}
