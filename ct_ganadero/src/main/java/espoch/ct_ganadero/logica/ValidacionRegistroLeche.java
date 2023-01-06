package espoch.ct_ganadero.logica;

import java.time.LocalDateTime;

public class ValidacionRegistroLeche {

    public static boolean turnoValido(String turno) {
        return turno.equals("M") || turno.equals("T");
    }
    
    public static boolean totalValido(String total) {
        try {
            float totalF = Float.parseFloat(total);
            return totalF > 0 && totalF < 100;
        } catch (Exception e) {
            return false;
        }
    }

    public static String obtenerTurno(LocalDateTime currentDateTime) {
        int hora = currentDateTime.getHour();
        return hora >= 6 && hora < 12 ? "M" : "T"; 
    }
    
}
