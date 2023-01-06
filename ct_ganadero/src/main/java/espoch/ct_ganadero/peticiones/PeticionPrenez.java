package espoch.ct_ganadero.peticiones;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PeticionPrenez {
    private final int id;
    private final String idCabezaGanado;
    private final String estado;
    private final String fechaInseminacion;
    private final String fechaParto;
}
