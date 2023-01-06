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
import espoch.ct_ganadero.logica.ValidacionEdad;
import espoch.ct_ganadero.logica.ValidacionFecha;
import espoch.ct_ganadero.modelo.Ajeno;
import espoch.ct_ganadero.modelo.CabezaGanado;
import espoch.ct_ganadero.modelo.Comprado;
import espoch.ct_ganadero.modelo.Propio;
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
        boolean idValido = ValidacionCabezaGanado.idValido(peticion.getId());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean estadoValido = ValidacionCabezaGanado.estadoValido(peticion.getEstado());
        boolean fechaNacValida = ValidacionFecha.fechaValida(peticion.getFecha_nacimiento());

        if (cabezaGanadoCrud.contiene(peticion.getId())) {
            throw new IllegalStateException("El Id ingresado ya se encuentra registrado");
        }
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
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
        if (!cabezaGanadoCrud.contiene(peticion.getId_padre())) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como padre");
        }
        if (!cabezaGanadoCrud.contiene(peticion.getId_madre())) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como madre");
        }
        if (!estadoValido) {
            throw new IllegalStateException("El valor ingresado no es un estado valido");
        }
        if (!fechaNacValida) {
            throw new IllegalStateException("La fecha de nacimiento ingresada no es valida");
        }

        Propio cabezaGanadoPropio = new Propio(
                cabezaGanadoCrud.buscar(peticion.getId_padre()),
                cabezaGanadoCrud.buscar(peticion.getId_madre()),
                peticion.getEstado(),
                ValidacionFecha.StringACalendar(peticion.getFecha_nacimiento()),
                peticion.getId(),
                razaCrud.buscar(peticion.getId_raza()),
                peticion.getNombre(),
                peticion.getSexo()
        );

        propioCrud.guardar(cabezaGanadoPropio);
        return cabezaGanadoPropio;
    }

    public Ajeno registrar(PeticionGanadoAjeno peticion) throws IllegalStateException, Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(peticion.getId());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionEdad.edadValida(Integer.parseInt(peticion.getEdad()));

        if (cabezaGanadoCrud.contiene(peticion.getId())) {
            throw new IllegalStateException("El Id ingresado ya se encuentra registrado");
        }
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
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
        if (!procedenciaCrud.contiene(Integer.parseInt(peticion.getId_procedencia()))) {
            throw new IllegalStateException("No se ha encontrado el lugar de procedencia");
        }
        if (!edadValida) {
            throw new IllegalStateException("La edad ingresada no es valida");
        }

        Ajeno cabezaGanadoAjeno = new Ajeno(
                procedenciaCrud.buscar(Integer.parseInt(peticion.getId_procedencia())), 
                Integer.parseInt(peticion.getEdad()), 
                peticion.getId(), 
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        ajenoCrud.guardar(cabezaGanadoAjeno);
        return cabezaGanadoAjeno;
    }

    public Comprado registrar(PeticionGanadoComprado peticion) throws IllegalStateException, Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(peticion.getId());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionEdad.edadValida(Integer.parseInt(peticion.getEdad()));
        boolean precioValido = Float.parseFloat(peticion.getPrecio()) >= 0;

        if (cabezaGanadoCrud.contiene(peticion.getId())) {
            throw new IllegalStateException("El Id ingresado ya se encuentra registrado");
        }
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
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
        if (!procedenciaCrud.contiene(Integer.parseInt(peticion.getId_procedencia()))) {
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
                procedenciaCrud.buscar(Integer.parseInt(peticion.getId_procedencia())), 
                Integer.parseInt(peticion.getEdad()), 
                peticion.getId(), 
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        compradoCrud.guardar(cabezaGanadoComprado);
        return cabezaGanadoComprado;
    }
    
    public Propio modificar(String id, PeticionGanadoPropio peticion) throws IllegalStateException, Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(peticion.getId());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean estadoValido = ValidacionCabezaGanado.estadoValido(peticion.getEstado());
        boolean fechaNacValida = ValidacionFecha.fechaValida(peticion.getFecha_nacimiento());

        if (!cabezaGanadoCrud.contiene(id)) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado a modificar");
        }
        
        if (cabezaGanadoCrud.contiene(peticion.getId())) {
            throw new IllegalStateException("El Id ingresado ya se encuentra registrado");
        }

        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
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
        if (!cabezaGanadoCrud.contiene(peticion.getId_padre())) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como padre");
        }
        if (!cabezaGanadoCrud.contiene(peticion.getId_madre())) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada como madre");
        }
        if (!estadoValido) {
            throw new IllegalStateException("El valor ingresado no es un estado valido");
        }
        if (!fechaNacValida) {
            throw new IllegalStateException("La fecha de nacimiento ingresada no es valida");
        }

        Propio cabezaGanadoPropio = new Propio(
                cabezaGanadoCrud.buscar(peticion.getId_padre()),
                cabezaGanadoCrud.buscar(peticion.getId_madre()),
                peticion.getEstado(),
                ValidacionFecha.StringACalendar(peticion.getFecha_nacimiento()),
                peticion.getId(),
                razaCrud.buscar(peticion.getId_raza()),
                peticion.getNombre(),
                peticion.getSexo()
        );

        propioCrud.modificar(propioCrud.buscar(id), cabezaGanadoPropio);
        return cabezaGanadoPropio;
    }
    
    public Ajeno modificar(String id, PeticionGanadoAjeno peticion) throws IllegalStateException, Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(peticion.getId());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionEdad.edadValida(Integer.parseInt(peticion.getEdad()));

        if (!cabezaGanadoCrud.contiene(id)) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado a modificar");
        }
        if (cabezaGanadoCrud.contiene(peticion.getId())) {
            throw new IllegalStateException("El Id ingresado ya se encuentra registrado");
        }
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
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
        if (!procedenciaCrud.contiene(Integer.parseInt(peticion.getId_procedencia()))) {
            throw new IllegalStateException("No se ha encontrado el lugar de procedencia");
        }
        if (!edadValida) {
            throw new IllegalStateException("La edad ingresada no es valida");
        }

        Ajeno cabezaGanadoAjeno = new Ajeno(
                procedenciaCrud.buscar(Integer.parseInt(peticion.getId_procedencia())), 
                Integer.parseInt(peticion.getEdad()), 
                peticion.getId(), 
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        ajenoCrud.modificar(ajenoCrud.buscar(id), cabezaGanadoAjeno);
        return cabezaGanadoAjeno;
    }

    public Comprado modificar(String id, PeticionGanadoComprado peticion) throws IllegalStateException, Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(peticion.getId());
        boolean nombreValido = ValidacionCabezaGanado.nombreValido(peticion.getNombre());
        boolean sexoValido = ValidacionCabezaGanado.sexoValido(peticion.getSexo());
        boolean edadValida = ValidacionEdad.edadValida(Integer.parseInt(peticion.getEdad()));
        boolean precioValido = Float.parseFloat(peticion.getPrecio()) >= 0;

        if (!cabezaGanadoCrud.contiene(id)) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado a modificar");
        }
        if (cabezaGanadoCrud.contiene(peticion.getId())) {
            throw new IllegalStateException("El Id ingresado ya se encuentra registrado");
        }
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
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
        if (!procedenciaCrud.contiene(Integer.parseInt(peticion.getId_procedencia()))) {
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
                procedenciaCrud.buscar(Integer.parseInt(peticion.getId_procedencia())), 
                Integer.parseInt(peticion.getEdad()), 
                peticion.getId(), 
                razaCrud.buscar(peticion.getId_raza()), 
                peticion.getNombre(), 
                peticion.getSexo()
        );

        compradoCrud.modificar(compradoCrud.buscar(id), cabezaGanadoComprado);
        return cabezaGanadoComprado;
    }

    public List<Propio> listar() {
        return propioCrud.listar();
    }

    public CabezaGanado ver(String id) throws Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(id);
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
        }
        return cabezaGanadoCrud.buscar(id);
    }
    
    public CabezaGanado eliminar(String id) throws Exception {
        boolean idValido = ValidacionCabezaGanado.idValido(id);
        if (!idValido) {
            throw new IllegalStateException("El Id ingresado no es valido");
        }
        return cabezaGanadoCrud.eliminar(id);
    }
}
