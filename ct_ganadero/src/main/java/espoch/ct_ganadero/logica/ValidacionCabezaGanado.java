package espoch.ct_ganadero.logica;

public class ValidacionCabezaGanado {

    public static boolean idValido(String id) {
        return !id.isBlank();
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
        return edad > 0 && edad < 50;
    }

    public static boolean razaValida(String id_raza) {
        return !id_raza.isBlank();
    }

    public static boolean procedenciaValida(String id_procedencia) {
        return !id_procedencia.isBlank();
    }

    public static boolean estaViva(String estado) {
        return estado.equals("VIVA");
    }
}
