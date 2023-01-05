package espoch.ct_ganadero.logica;

public class ValidacionUsuario {
    public static boolean cedulaValida(String cedula) {
        //TODO Implementar validacion de cedula
        return true;
    }
    
    public static boolean nombreValido(String nombre) {
        //TODO Implementar validacion de nombre
        return true;
    }
    
    public static boolean sexoValido(String sexo) {
        //TODO Implementar validacion de sexo
        return true;
    }
    
    public static boolean usernameValido(String sexo) {
        //TODO Implementar validacion de usuario
        return true;
    }
    
    public static boolean passwValido(String passw) {
        //TODO Implementar validacion de contrasena
        return true;
    }
    
    public static boolean rolValido(String rol) {
        return rol.equalsIgnoreCase("ADMIN") || rol.equalsIgnoreCase("USER");
    }
}
