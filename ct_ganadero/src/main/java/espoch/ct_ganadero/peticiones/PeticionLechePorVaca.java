package espoch.ct_ganadero.peticiones;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PeticionLechePorVaca {
    private final String idCabezaGanado;
    private final String idUsuario;
    private final String total;
}
