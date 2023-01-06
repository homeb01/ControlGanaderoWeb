package espoch.ct_ganadero.logica;

import java.util.regex.Pattern;

public class ValidacionUsuario {
    public static boolean cedulaValida(String cedula) {
        //TODO Implementar validacion de cedula
        return true;
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
    
    public static boolean sexoValido(String sexo) {
        return sexo.equals("H") || sexo.equals("M");
    }
    
    public static boolean usernameValido(String usr) {
        return Pattern.compile("[A-Z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][0-9][0-9][0-9]").matcher(usr).matches();
    }
    
    public static boolean passwValido(String passw) {
        return Pattern.compile("[A-Z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][a-z][0-9][0-9][0-9]").matcher(passw).matches();
    }
    
    public static boolean rolValido(String rol) {
        return rol.equalsIgnoreCase("ADMIN") || rol.equalsIgnoreCase("USER");
    }
}
