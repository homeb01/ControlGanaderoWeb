package espoch.ct_ganadero.logica;

public class ValidacionUsuario {

    public static boolean cedulaValida(String cedula) throws Exception {
        boolean cedulaCorrecta;
        try {

            if (cedula.length() == 10)
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else cedulaCorrecta = (10 - (suma % 10)) == verificador;
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            cedulaCorrecta = false;
            throw new Exception("Una excepcion ocurrio en el proceso de validadcion");
        }

        return cedulaCorrecta;
    }

    public static boolean nombreValido(String nombre) {
        if (nombre.isBlank()) {
            return false;
        }

        for (char c : nombre.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
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
