package espoch.ct_ganadero.logica;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

public class ValidacionFecha {

    public static boolean fechaValida(String fecha) {
        try {
            LocalDate.parse(fecha, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return true;
    }

    public static Calendar StringACalendar(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(fecha));
        } catch (ParseException ex) {
            return null;
        }
        return calendar;
    }

    public static String idAFormatoAceptado(String id) throws ParseException {
        SimpleDateFormat formatoId = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatoAceptado = new SimpleDateFormat("yyyy-MM-dd");
        return formatoAceptado.format(formatoId.parse(id));
    }

    public static boolean fechaMenorIgualA(Calendar a, Calendar b) {
        return a.compareTo(b) <= 0;
    }

    public static Calendar fechaActual() {
        return Calendar.getInstance();
    }
}
