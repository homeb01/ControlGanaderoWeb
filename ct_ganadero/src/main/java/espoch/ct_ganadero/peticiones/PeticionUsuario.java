package espoch.ct_ganadero.peticiones;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PeticionUsuario {
    private final String user;
    private final String password;
    private final String rol;
    private final String cedulaRuc;
    private final String nombre;
    private final String fechaNacimiento;
    private final String ciudad;
    private final String sexo;
}
