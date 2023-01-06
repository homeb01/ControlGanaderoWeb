package espoch.ct_ganadero.logica;

import java.util.Calendar;
import java.util.regex.Pattern;

public class ValidacionCabezaGanado {

    public static boolean idValido(String id) {
        return Pattern.compile("[A-Z][0-9][0-9][0-9]").matcher(id).matches();
    }

    public static boolean sexoValido(char sexo) {
        return sexo == 'H' || sexo == 'M';
    }

    public static boolean estadoValido(String estado) {
        return estado.equals("VIVA") || estado.equals("MUERTA") || estado.equals("VENDIDA");
    }

    public static boolean nombreValido(String nombre) {
        if (nombre.isBlank())
            return false;
        
        for (char c: nombre.toCharArray()) {
            if (Character.isDigit(c))
                return false;
        }
        return true;   
    }
    
    public static boolean esMacho(char sexo) {
        return sexo == 'M';
    }
    
    public static boolean edadValida(int edad) {
        return edad > 0 && edad < 100;
    }
}
