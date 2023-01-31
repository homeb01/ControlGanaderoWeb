package espoch.ct_ganadero.logica;

public class ValidacionPrenez {

    public static String PRENADA = "PRENADA";
    public static String PRODUCCION = "PRODUCCION";
    public static String ABORTO = "ABORTO";

    public static boolean estadoValido(String estado) {
        return estado.equals(PRENADA) || estado.equals(PRODUCCION) || estado.equals(ABORTO);
    }
    
}
