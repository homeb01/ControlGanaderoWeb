package espoch.ct_ganadero.logica;

public class ValidacionPrenez {

    public static String PRENADA = "Prenada";
    public static String PRODUCCION = "Produccion";
    public static String ABORTO = "Aborto";

    public static boolean estadoValido(String estado) {
        return estado.equals(PRENADA) || estado.equals(PRODUCCION) || estado.equals(ABORTO);
    }
    
}
