package espoch.ct_ganadero.logica;

public class EdadValidator {
    public static boolean edadValida(int edad) {
        return edad > 0 && edad < 100;
    }
}
