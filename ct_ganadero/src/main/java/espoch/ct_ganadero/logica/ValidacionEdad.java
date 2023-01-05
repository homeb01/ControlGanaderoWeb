package espoch.ct_ganadero.logica;

public class ValidacionEdad {
    public static boolean edadValida(int edad) {
        return edad > 0 && edad < 100;
    }
}
