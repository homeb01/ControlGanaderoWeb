package espoch.ct_ganadero.logica;

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
        return !usr.isBlank();
    }
    
    public static boolean passwValido(String passw) {
        return !passw.isBlank();
    }
    
    public static boolean rolValido(String rol) {
        return rol.equalsIgnoreCase("ADMIN") || rol.equalsIgnoreCase("USER");
    }
}
