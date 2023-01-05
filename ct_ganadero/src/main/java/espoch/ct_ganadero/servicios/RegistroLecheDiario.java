package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.datos.CabezaGanadoCRUD;
import espoch.ct_ganadero.datos.LechePorVacaCRUD;
import espoch.ct_ganadero.datos.RegistroLecheCRUD;
import espoch.ct_ganadero.datos.UsuarioCRUD;
import espoch.ct_ganadero.logica.ValidacionCabezaGanado;
import espoch.ct_ganadero.logica.ValidacionFecha;
import espoch.ct_ganadero.logica.ValidacionRegistroLeche;
import espoch.ct_ganadero.logica.ValidacionUsuario;
import espoch.ct_ganadero.modelo.LechePorVaca;
import espoch.ct_ganadero.modelo.Propio;
import espoch.ct_ganadero.modelo.RegistroLeche;
import espoch.ct_ganadero.peticiones.PeticionLechePorVaca;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class RegistroLecheDiario {

    private final RegistroLecheCRUD registroLecheCrud;
    private final LechePorVacaCRUD lecheXVacaCrud;
    private final CabezaGanadoCRUD cabezaGanadoCrud;
    private final UsuarioCRUD usuarioCrud;

    public LechePorVaca registrar(PeticionLechePorVaca peticion) throws Exception {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String idRegistro = currentDateTime.format(customFormat);

        String turno = ValidacionRegistroLeche.obtenerTurno(currentDateTime);

        boolean idCabezaGanadoValido = ValidacionCabezaGanado.idValido(peticion.getIdCabezaGanado());
        boolean idUsuarioValido = ValidacionUsuario.usernameValido(peticion.getIdUsuario());
        boolean totalValido = ValidacionRegistroLeche.totalValido(peticion.getTotal());

        if (lecheXVacaCrud.existeRegistro(peticion.getIdCabezaGanado(), idRegistro)) {
            throw new IllegalStateException("Ya existe un registro para esta vaca");
        }

        if (!registroLecheCrud.contiene(ValidacionFecha.StringACalendar(idRegistro))) {
            registroLecheCrud.guardar(new RegistroLeche(
                    ValidacionFecha.StringACalendar(idRegistro),
                    0,
                    0));
        }

        if (!idCabezaGanadoValido) {
            throw new IllegalStateException("La cabeza de ganado ingresada no es valida");
        }
        if (!cabezaGanadoCrud.contiene(peticion.getIdCabezaGanado())) {
            throw new IllegalStateException("No se ha encontrado la cabeza de ganado ingresada");
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
        /*RegistroLeche registroLeche = new RegistroLeche(
                ValidacionFecha.StringACalendar(idRegistro),
                0,
                0);*/

        LechePorVaca lechePorVaca = new LechePorVaca(
                registroLeche,
                (Propio) cabezaGanadoCrud.buscar(peticion.getIdCabezaGanado()),
                usuarioCrud.buscar(peticion.getIdUsuario()),
                turno,
                Integer.parseInt(peticion.getTotal())
        );

        lecheXVacaCrud.guardar(lechePorVaca);
        return lechePorVaca;
    }
}
