package espoch.ct_ganadero.controladores;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PeticionGanadoAjeno {
    private final String id;
    private final String id_raza;
    private final String nombre;
    private final char sexo;
    private final String id_procedencia;
    private final String edad;
}
