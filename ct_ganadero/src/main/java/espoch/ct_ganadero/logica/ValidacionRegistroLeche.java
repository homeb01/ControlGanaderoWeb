package espoch.ct_ganadero.logica;

import java.time.LocalDateTime;

public class ValidacionRegistroLeche {

    public static boolean turnoValido(String turno) {
        return turno.equals("M") || turno.equals("T");
    }
    
    public static boolean totalValido(String total, float sobrante, float ternero) {
        try {
            float totalF = Float.parseFloat(total);
            return totalF > ternero && totalF <= (sobrante + ternero);
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean totalValido(String total) {
        try {
            float totalF = Float.parseFloat(total);
            return totalF > 0 && totalF < 20;
        } catch (Exception e) {
            return false;
        }
    }

    public static String obtenerTurno(LocalDateTime currentDateTime) {
        int hora = currentDateTime.getHour();
        return hora >= 6 && hora < 12 ? "M" : "T"; 
    }
    
}
