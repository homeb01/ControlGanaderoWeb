package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.peticiones.PeticionGanadoAjeno;
import espoch.ct_ganadero.peticiones.PeticionGanadoComprado;
import espoch.ct_ganadero.peticiones.PeticionGanadoPropio;
import espoch.ct_ganadero.datos.AjenoCRUD;
import espoch.ct_ganadero.datos.CabezaGanadoCRUD;
import espoch.ct_ganadero.datos.CompradoCRUD;
import espoch.ct_ganadero.datos.ProcedenciaCRUD;
import espoch.ct_ganadero.datos.PropioCRUD;
import espoch.ct_ganadero.datos.RazaCRUD;
import espoch.ct_ganadero.logica.ValidacionCabezaGanado;
import espoch.ct_ganadero.logica.ValidacionFecha;
import espoch.ct_ganadero.modelo.Ajeno;
import espoch.ct_ganadero.modelo.CabezaGanado;
import espoch.ct_ganadero.modelo.Comprado;
import espoch.ct_ganadero.modelo.Propio;
import espoch.ct_ganadero.modelo.Raza;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RegistroGanado {

    private final CabezaGanadoCRUD cabezaGanadoCrud;
    private final PropioCRUD propioCrud;
    private final AjenoCRUD ajenoCrud;
    private final CompradoCRUD compradoCrud;
    
    private final RazaCRUD razaCrud;
    private final ProcedenciaCRUD procedenciaCrud;

    public Propio registrar(PeticionGanadoPropio peticion) throws IllegalStateException, Exception {
        boolean idPadreValido = ValidacionCabezaGanado.idValido(peticion.getId_padre());
        boolean idMadreValido = ValidacionCabezaGanado.idValido(peticion.getId_madre());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean fechaNacValida = ValidacionFecha.fechaValida(peticion.getFecha_nacimiento());
        
        if (!nombreValido) {
            throw new IllegalStateException("Se ha ingresado un nombre no permitido");
        }
        
        if (!sexoValido) {
            throw new IllegalStateException("El valor para el campo Sexo no es valido");
        }
        
        if (!razaCrud.contiene(peticion.getId_raza())) {
            throw new IllegalStateException("No se ha encontrado la raza ingresada");
        }
        
        if (!fechaNacValida) {
            throw new IllegalStateException("La fecha de nacimiento ingresada no es valida");
        }
        
        if (!idMadreValido) {
            throw new IllegalStateException("El Id de la madre ingresado no es valido");
        }
        
        if (!cabezaGanadoCrud.contiene(Integer.parseInt(peticion.getId_madre()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como madre");
        }
        
        CabezaGanado madre = cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_madre()));
        if (ValidacionCabezaGanado.esMacho(madre.getSexo()))
            throw new IllegalStateException("La cabeza de ganado seleccionada como madre no es hembra");
        
        if (!idPadreValido) {
            throw new IllegalStateException("El Id del padre ingresado no es valido");
        }
        
        if (!cabezaGanadoCrud.contiene(Integer.parseInt(peticion.getId_padre()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como padre");
        }
        
        CabezaGanado padre = cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_padre()));
        if (!ValidacionCabezaGanado.esMacho(padre.getSexo()))
            throw new IllegalStateException("La cabeza de ganado seleccionada como padre no es macho");

        if (!ValidacionFecha.fechaMenorIgualA(ValidacionFecha.StringACalendar(peticion.getFecha_nacimiento()), ValidacionFecha.fechaActual()))
            throw new IllegalStateException("La fecha ingresada no puede ser mayor a la fecha actual");

        Propio cabezaGanadoPropio = new Propio(
                cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_padre())),
                cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_madre())),
                ValidacionFecha.StringACalendar(peticion.getFecha_nacimiento()),
                razaCrud.buscar(peticion.getId_raza()),
                peticion.getNombre(),
                peticion.getSexo()
        );

        propioCrud.guardar(cabezaGanadoPropio);
        return cabezaGanadoPropio;
    }

    public Ajeno registrar(PeticionGanadoAjeno peticion) throws IllegalStateException, Exception {
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionCabezaGanado.edadValida(Integer.parseInt(peticion.getEdad()));
        boolean razaValida = ValidacionCabezaGanado.razaValida(peticion.getId_raza());
        boolean procedenciaValida = ValidacionCabezaGanado.procedenciaValida(peticion.getId_procedencia());
        
        if(!razaValida) {
            throw new IllegalStateException("La raza ingresada no es válida");
        }
        
        if (!razaCrud.contiene(peticion.getId_raza())) {
            throw new IllegalStateException("No se ha encontrado la raza ingresada");
        }
        
        if (!nombreValido) {
            throw new IllegalStateException("Se ha ingresado un nombre no permitido");
        }
        
        if (!sexoValido) {
            throw new IllegalStateException("El valor para el campo Sexo no es valido");
        }
        
        if (!procedenciaValida) {
            throw new IllegalStateException("El procedencia ingresada no es válida");
        }
        
        if (!procedenciaCrud.contiene(peticion.getId_procedencia())) {
            throw new IllegalStateException("No se ha encontrado el lugar de procedencia");
        }
        
        if (!edadValida) {
            throw new IllegalStateException("La edad ingresada no es valida");
        }

        Ajeno cabezaGanadoAjeno = new Ajeno(
                procedenciaCrud.buscar(peticion.getId_procedencia()), 
                Integer.parseInt(peticion.getEdad()), 
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        ajenoCrud.guardar(cabezaGanadoAjeno);
        return cabezaGanadoAjeno;
    }

    public Comprado registrar(PeticionGanadoComprado peticion) throws IllegalStateException, Exception {
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionCabezaGanado.edadValida(Integer.parseInt(peticion.getEdad()));
        boolean precioValido = Float.parseFloat(peticion.getPrecio()) >= 0;
        boolean razaValida = ValidacionCabezaGanado.razaValida(peticion.getId_raza());
        //boolean procedenciaValida = ValidacionCabezaGanado.procedenciaValida(peticion.getId_procedencia());
        
        if (!razaValida) {
            throw new IllegalStateException("La raza ingresada no es válida");
        }
        
        if (!razaCrud.contiene(peticion.getId_raza())) {
            throw new IllegalStateException("No se ha encontrado la raza ingresada");
        }
        
        if (!nombreValido) {
            throw new IllegalStateException("Se ha ingresado un nombre no permitido");
        }
        
        if (!sexoValido) {
            throw new IllegalStateException("El valor para el campo Sexo no es valido");
        }
        
        /*if (!procedenciaValida) {
            throw new IllegalStateException("El procedencia ingresada no es válida");
        }*/
        
        if (!procedenciaCrud.contiene(peticion.getId_procedencia())) {
            throw new IllegalStateException("No se ha encontrado el lugar de procedencia");
        }
        
        if (!edadValida) {
            throw new IllegalStateException("La edad ingresada no es valida");
        }
        if (!precioValido) {
            throw new IllegalStateException("Se ha ingresado un precio no válido");
        }

        Comprado cabezaGanadoComprado = new Comprado(
                Float.parseFloat(peticion.getPrecio()),
                procedenciaCrud.buscar(peticion.getId_procedencia()), 
                Integer.parseInt(peticion.getEdad()),  
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        compradoCrud.guardar(cabezaGanadoComprado);
        return cabezaGanadoComprado;
    }
    
    public Propio modificar(String id, PeticionGanadoPropio peticion) throws IllegalStateException, Exception {
        boolean idPadreValido = ValidacionCabezaGanado.idValido(peticion.getId_padre());
        boolean idMadreValido = ValidacionCabezaGanado.idValido(peticion.getId_madre());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean estadoValido = ValidacionCabezaGanado.estadoValido(peticion.getEstado());
        boolean fechaNacValida = ValidacionFecha.fechaValida(peticion.getFecha_nacimiento());
        boolean razaValida = ValidacionCabezaGanado.razaValida(peticion.getId_raza());

        if (!cabezaGanadoCrud.contiene(Integer.parseInt(id))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado a modificar");
        }
        
        if (!idMadreValido) {
            throw new IllegalStateException("El Id de la madre ingresado no es valido");
        }
        
        if (!cabezaGanadoCrud.contiene(Integer.parseInt(peticion.getId_madre()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como madre");
        }
        
        CabezaGanado madre = cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_madre()));
        if (ValidacionCabezaGanado.esMacho(madre.getSexo()))
            throw new IllegalStateException("La cabeza de ganado seleccionada como madre no es hembra");
        
        if (!idPadreValido) {
            throw new IllegalStateException("El Id del padre ingresado no es valido");
        }
        
        if (!cabezaGanadoCrud.contiene(Integer.parseInt(peticion.getId_padre()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como padre");
        }
        
        CabezaGanado padre = cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_padre()));
        if (!ValidacionCabezaGanado.esMacho(padre.getSexo()))
            throw new IllegalStateException("La cabeza de ganado seleccionada como padre no es macho");
        
        if (!razaValida) {
            throw new IllegalStateException("El raza ingresada no es valido");
        }
        
        if (!razaCrud.contiene(peticion.getId_raza())) {
            throw new IllegalStateException("No se ha encontrado la raza ingresada");
        }
        
        if (!nombreValido) {
            throw new IllegalStateException("Se ha ingresado un nombre no permitido");
        }
        
        if (!sexoValido) {
            throw new IllegalStateException("El valor para el campo Sexo no es valido");
        }
        
        if (!cabezaGanadoCrud.contiene(Integer.parseInt(peticion.getId_padre()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como padre");
        }
        
        if (!cabezaGanadoCrud.contiene(Integer.parseInt(peticion.getId_madre()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como madre");
        }
        
        if (!fechaNacValida) {
            throw new IllegalStateException("La fecha de nacimiento ingresada no es valida");
        }

        Propio cabezaGanadoPropio = new Propio(
                cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_padre())),
                cabezaGanadoCrud.buscar(Integer.parseInt(peticion.getId_madre())),
                ValidacionFecha.StringACalendar(peticion.getFecha_nacimiento()),
                razaCrud.buscar(peticion.getId_raza()),
                peticion.getNombre(),
                peticion.getSexo()
        );
        
        cabezaGanadoPropio.setEstado(peticion.getEstado());

        propioCrud.modificar(propioCrud.buscar(Integer.parseInt(id)), cabezaGanadoPropio);
        return cabezaGanadoPropio;
    }
    
    public Ajeno modificar(String id, PeticionGanadoAjeno peticion) throws IllegalStateException, Exception {
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionCabezaGanado.edadValida(Integer.parseInt(peticion.getEdad()));

        if (!cabezaGanadoCrud.contiene(Integer.parseInt(id))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado a modificar");
        }
        if (!razaCrud.contiene(peticion.getId_raza())) {
            throw new IllegalStateException("No se ha encontrado la raza ingresada");
        }
        if (!nombreValido) {
            throw new IllegalStateException("Se ha ingresado un nombre no permitido");
        }
        if (!sexoValido) {
            throw new IllegalStateException("El valor para el campo Sexo no es valido");
        }
        if (!procedenciaCrud.contiene(peticion.getId_procedencia())) {
            throw new IllegalStateException("No se ha encontrado el lugar de procedencia");
        }
        if (!edadValida) {
            throw new IllegalStateException("La edad ingresada no es valida");
        }

        Ajeno cabezaGanadoAjeno = new Ajeno(
                procedenciaCrud.buscar(peticion.getId_procedencia()), 
                Integer.parseInt(peticion.getEdad()),  
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        ajenoCrud.modificar(ajenoCrud.buscar(Integer.parseInt(id)), cabezaGanadoAjeno);
        return cabezaGanadoAjeno;
    }

    public Comprado modificar(String id, PeticionGanadoComprado peticion) throws IllegalStateException, Exception {
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionCabezaGanado.edadValida(Integer.parseInt(peticion.getEdad()));
        boolean precioValido = Float.parseFloat(peticion.getPrecio()) >= 0;
        boolean razaValida = ValidacionCabezaGanado.razaValida(peticion.getId_raza());
        boolean procedenciaValida = ValidacionCabezaGanado.procedenciaValida(peticion.getId_procedencia());

        if (!cabezaGanadoCrud.contiene(Integer.parseInt(id))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado a modificar");
        }
        
        if (!razaValida) {
            throw new IllegalStateException("El raza ingresada no es valido");
        }
        
        if (!razaCrud.contiene(peticion.getId_raza())) {
            throw new IllegalStateException("No se ha encontrado la raza ingresada");
        }
        
        if (!nombreValido) {
            throw new IllegalStateException("Se ha ingresado un nombre no permitido");
        }
        
        if (!sexoValido) {
            throw new IllegalStateException("El valor para el campo Sexo no es valido");
        }
        
        if (!procedenciaValida) {
            throw new IllegalStateException("El procedencia ingresada no es válida");
        }
        
        if (!procedenciaCrud.contiene(peticion.getId_procedencia())) {
            throw new IllegalStateException("No se ha encontrado el lugar de procedencia");
        }
        
        if (!edadValida) {
            throw new IllegalStateException("La edad ingresada no es valida");
        }
        
        if (!precioValido) {
            throw new IllegalStateException("Se ha ingresado un precio no válido");
        }

        Comprado cabezaGanadoComprado = new Comprado(
                Float.parseFloat(peticion.getPrecio()),
                procedenciaCrud.buscar(peticion.getId_procedencia()), 
                Integer.parseInt(peticion.getEdad()), 
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        compradoCrud.modificar(compradoCrud.buscar(Integer.parseInt(id)), cabezaGanadoComprado);
        return cabezaGanadoComprado;
    }

    public List<CabezaGanado> listar() {
        return cabezaGanadoCrud.listar();
    }
    
    public List<Raza> listarRazas() {
        return razaCrud.listar();
    }

    public CabezaGanado ver(String id) throws Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(id);
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
        }
        return cabezaGanadoCrud.buscar(Integer.parseInt(id));
    }
    
    public CabezaGanado eliminar(String id) throws Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(id);
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
        }
        return cabezaGanadoCrud.eliminar(Integer.parseInt(id));
    }
}
