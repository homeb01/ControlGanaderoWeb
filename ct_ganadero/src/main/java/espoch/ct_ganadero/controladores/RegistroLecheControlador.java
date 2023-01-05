package espoch.ct_ganadero.controladores;

import espoch.ct_ganadero.modelo.LechePorVaca;
import espoch.ct_ganadero.peticiones.PeticionLechePorVaca;
import espoch.ct_ganadero.servicios.RegistroLecheDiario;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class RegistroLecheControlador {
    
    private final RegistroLecheDiario registro;
    
    @PostMapping("/leche/registrar")
    public ResponseEntity registrarUsuario(@RequestBody PeticionLechePorVaca peticion) throws Exception {
        LechePorVaca lecheXVaca = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            lecheXVaca = registro.registrar(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(lecheXVaca, headers, HttpStatus.CREATED);
    }
    
}
