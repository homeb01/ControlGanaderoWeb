package espoch.ct_ganadero.controladores;

import espoch.ct_ganadero.modelo.Propio;
import espoch.ct_ganadero.servicios.RegistroGanadoPropio;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ganado")
@AllArgsConstructor
public class RegistroGanadoControlador {

    private final RegistroGanadoPropio registroGanadoPropio;

    @PostMapping()
    public String registrar(@RequestBody PeticionRegistroGanadoPropio peticion) {
        try {
            registroGanadoPropio.registrar(peticion);
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "Registro correcto";
    }
    
    @GetMapping
    public List<Propio> listar() {
        return registroGanadoPropio.listar();
    }
}
