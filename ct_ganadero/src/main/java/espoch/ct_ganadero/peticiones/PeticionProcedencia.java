package espoch.ct_ganadero.peticiones;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PeticionProcedencia {
    private final String id;
    private final String procedencia;
}
