package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.datos.CabezaGanadoCRUD;
import espoch.ct_ganadero.datos.CompradoCRUD;
import espoch.ct_ganadero.datos.LechePorVacaCRUD;
import espoch.ct_ganadero.datos.PropioCRUD;
import espoch.ct_ganadero.datos.RegistroLecheCRUD;
import espoch.ct_ganadero.datos.UsuarioCRUD;
import espoch.ct_ganadero.logica.ValidacionCabezaGanado;
import espoch.ct_ganadero.logica.ValidacionFecha;
import espoch.ct_ganadero.logica.ValidacionRegistroLeche;
import espoch.ct_ganadero.logica.ValidacionUsuario;
import espoch.ct_ganadero.modelo.CabezaGanado;
import espoch.ct_ganadero.modelo.LechePorVaca;
import espoch.ct_ganadero.modelo.RegistroLeche;
import espoch.ct_ganadero.peticiones.PeticionLechePorVaca;
import espoch.ct_ganadero.peticiones.PeticionTotalTerneros;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RegistroLecheDiario {

    private final RegistroLecheCRUD registroLecheCrud;
    private final LechePorVacaCRUD lecheXVacaCrud;
    private final PropioCRUD cabezaGanadoCrud;
    private final CabezaGanadoCRUD ganado;
    private final CompradoCRUD compradoCrud;
    private final UsuarioCRUD usuarioCrud;

    public LechePorVaca registrar(PeticionLechePorVaca peticion) throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String idRegistro = currentDateTime.format(customFormat);
        String turno = ValidacionRegistroLeche.obtenerTurno(currentDateTime);

        boolean idCabezaGanadoValido = ValidacionCabezaGanado.idValido(peticion.getIdCabezaGanado().toUpperCase());
        boolean idUsuarioValido = ValidacionUsuario.usernameValido(peticion.getIdUsuario());
        boolean totalValido = ValidacionRegistroLeche.totalValido(peticion.getTotal());

        if (!idCabezaGanadoValido) {
            throw new IllegalStateException("El Id de la cabeza de ganado ingresada no es valida");
        }

        if (!cabezaGanadoCrud.contiene(Integer.parseInt(peticion.getIdCabezaGanado())) && !compradoCrud.contiene(Integer.parseInt(peticion.getIdCabezaGanado()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada");
        }

        CabezaGanado cabezaGanado = ganado.buscar(Integer.parseInt(peticion.getIdCabezaGanado()));
        if (!ValidacionCabezaGanado.estaViva(cabezaGanado.getEstado())) {
            throw new IllegalStateException("La cabeza de ganado seleccionada no está viva o ha sido vendida");
        }

        if (lecheXVacaCrud.existeRegistro(Integer.parseInt(peticion.getIdCabezaGanado()), idRegistro, turno)) {
            throw new IllegalStateException("Ya existe un registro para esta vaca");
        }

        if (!registroLecheCrud.contiene(ValidacionFecha.StringACalendar(idRegistro))) {
            registroLecheCrud.guardar(new RegistroLeche(
                    ValidacionFecha.StringACalendar(idRegistro),
                    0f,
                    0f));
        }

        if (!idUsuarioValido) {
            throw new IllegalStateException("El usuario ingresado no es valido");
        }

        if (!usuarioCrud.contiene(peticion.getIdUsuario())) {
            throw new IllegalStateException("No se ha encontrado el usuario especificado");
        }

        if (!totalValido) {
            throw new IllegalStateException("El total ingresado no es valido");
        }

        RegistroLeche registroLeche = registroLecheCrud.buscar(ValidacionFecha.StringACalendar(idRegistro));
        registroLeche.setTotalSobrante(registroLeche.getTotalSobrante() + Float.parseFloat(peticion.getTotal()));

        LechePorVaca lechePorVaca = new LechePorVaca(
                registroLeche,
                ganado.buscar(Integer.parseInt(peticion.getIdCabezaGanado())),
                usuarioCrud.buscar(peticion.getIdUsuario()),
                turno,
                Float.parseFloat(peticion.getTotal())
        );

        lecheXVacaCrud.guardar(lechePorVaca);
        return lechePorVaca;
    }

    public LechePorVaca modificar(int id, PeticionLechePorVaca peticion) throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String idRegistro = currentDateTime.format(customFormat);

        boolean idCabezaGanadoValido = ValidacionCabezaGanado.idValido(peticion.getIdCabezaGanado().toUpperCase());
        boolean totalValido = ValidacionRegistroLeche.totalValido(peticion.getTotal());
        boolean turnoValido = ValidacionRegistroLeche.turnoValido(peticion.getTurno());

        RegistroLeche registroLeche = registroLecheCrud.buscar(ValidacionFecha.StringACalendar(idRegistro));
        LechePorVaca registroVaca = lecheXVacaCrud.buscar(id);

        if (!lecheXVacaCrud.contiene(id)) {
            throw new IllegalStateException("El Id que se desea modificar no existe");
        }

        if (!idCabezaGanadoValido) {
            throw new IllegalStateException("La cabeza de ganado ingresada no es valida");
        }

        if (!turnoValido) {
            throw new IllegalStateException("El turno ingresado no es valido");
        }

        if (!ganado.contiene(Integer.parseInt(peticion.getIdCabezaGanado()))) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada");
        }

        if (!totalValido) {
            throw new IllegalStateException("El total ingresado no es valido");
        }

        if (lecheXVacaCrud.existeRegistro(Integer.parseInt(peticion.getIdCabezaGanado()), idRegistro, peticion.getTurno())) {
            if (!(registroVaca.getCabezaGanado().getId() == (Integer.parseInt(peticion.getIdCabezaGanado())))
                    || !registroVaca.getTurno().equals(peticion.getTurno())) {
                throw new IllegalStateException("Ya existe un registro para esta vaca");
            }
        }

        if (registroVaca.getTotal() != Float.parseFloat(peticion.getTotal())) {
            registroLeche.setTotalSobrante(registroLeche.getTotalSobrante() + registroLeche.getTotalTernero());
            registroLeche.setTotalTernero(0);
            registroLeche.setTotalSobrante(registroLeche.getTotalSobrante() - registroVaca.getTotal());
            registroLeche.setTotalSobrante(registroLeche.getTotalSobrante() + Float.parseFloat(peticion.getTotal()));
        }

        LechePorVaca lechePorVaca = new LechePorVaca(
                registroLeche,
                ganado.buscar(Integer.parseInt(peticion.getIdCabezaGanado())),
                registroVaca.getUsuario(),
                peticion.getTurno(),
                Float.parseFloat(peticion.getTotal())
        );

        lecheXVacaCrud.modificar(id, lechePorVaca);
        return lechePorVaca;
    }

    public LechePorVaca eliminar(int id) throws Exception {
        LechePorVaca registroVaca = lecheXVacaCrud.buscar(id);
        Calendar idRegistro = registroVaca.getRegistro().getFechaRegistro();
        RegistroLeche registroLeche = registroLecheCrud.buscar(idRegistro);
        registroLeche.setTotalSobrante(registroLeche.getTotalTernero() + registroLeche.getTotalSobrante());
        registroLeche.setTotalTernero(0);
        registroLeche.setTotalSobrante(registroLeche.getTotalSobrante() - registroVaca.getTotal());
        return lecheXVacaCrud.eliminar(id);
    }

    public List<LechePorVaca> listar(String idRegistro) {
        boolean fechaValida = ValidacionFecha.fechaValida(idRegistro);
        if (!fechaValida) {
            throw new IllegalStateException("La fecha no es valida");
        }
        return lecheXVacaCrud.listar(idRegistro);
    }

    public List<RegistroLeche> listar() {
        return registroLecheCrud.listar();
    }

    public LechePorVaca ver(int id) throws Exception {
        return lecheXVacaCrud.buscar(id);
    }

    public RegistroLeche ver(String id) throws Exception {
        boolean fechaValida = ValidacionFecha.fechaValida(id);
        if (!fechaValida) {
            throw new IllegalStateException("La fecha no es valida");
        }
        return registroLecheCrud.buscar(ValidacionFecha.StringACalendar(id));
    }

    public RegistroLeche actualizarTotalTerneros(PeticionTotalTerneros peticion) throws Exception {
        boolean idRegistroValido = ValidacionFecha.fechaValida(peticion.getIdRegistro());

        if (!idRegistroValido) {
            throw new IllegalStateException("El Id de registro no es valido");
        }

        RegistroLeche registroLeche = registroLecheCrud.buscar(ValidacionFecha.StringACalendar(peticion.getIdRegistro()));

        boolean totalValido = ValidacionRegistroLeche.totalValido(peticion.getTotalTerneros(), registroLeche.getTotalSobrante(), registroLeche.getTotalTernero());

        if (!totalValido) {
            throw new IllegalStateException("El total de leche para los terneros no es valido");
        }
        if (!registroLecheCrud.contiene(ValidacionFecha.StringACalendar(peticion.getIdRegistro()))) {
            throw new IllegalStateException("No se ha encontrado el registro de leche a actualizar");
        }

        RegistroLeche nuevoRegistro = new RegistroLeche();
        nuevoRegistro.setFechaRegistro(registroLeche.getFechaRegistro());
        nuevoRegistro.setTotalTernero(Float.parseFloat(peticion.getTotalTerneros()));
        nuevoRegistro.setTotalSobrante((registroLeche.getTotalSobrante() + registroLeche.getTotalTernero()) - nuevoRegistro.getTotalTernero());
        registroLecheCrud.actualizarTotalTerneros(nuevoRegistro.getTotalTernero(), nuevoRegistro.getTotalSobrante(), peticion.getIdRegistro());
        return nuevoRegistro;
    }

    public String totalManana(String idRegistro) {
        return lecheXVacaCrud.totalManana(idRegistro) == null ? "0" : lecheXVacaCrud.totalManana(idRegistro);
    }

    public String totalTarde(String idRegistro) {
        return lecheXVacaCrud.totalTarde(idRegistro) == null ? "0" : lecheXVacaCrud.totalTarde(idRegistro);
    }

    public List<LechePorVaca> listarHoy() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String idRegistro = currentDateTime.format(customFormat);
        return lecheXVacaCrud.listar(idRegistro);
    }
}
