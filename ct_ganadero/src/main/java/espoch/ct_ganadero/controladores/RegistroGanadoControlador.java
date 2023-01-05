package espoch.ct_ganadero.controladores;

import espoch.ct_ganadero.peticiones.PeticionGanadoAjeno;
import espoch.ct_ganadero.peticiones.PeticionGanadoComprado;
import espoch.ct_ganadero.peticiones.PeticionGanadoPropio;
import espoch.ct_ganadero.modelo.Ajeno;
import espoch.ct_ganadero.modelo.CabezaGanado;
import espoch.ct_ganadero.modelo.Comprado;
import espoch.ct_ganadero.modelo.Propio;
import espoch.ct_ganadero.servicios.RegistroGanado;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class RegistroGanadoControlador {

    private final RegistroGanado registroGanado;

    @PostMapping("/ganado/propio")
    public ResponseEntity registrarPropio(@RequestBody PeticionGanadoPropio peticion) {
        Propio cabezaGanadoPropio = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            cabezaGanadoPropio = registroGanado.registrar(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(cabezaGanadoPropio, headers, HttpStatus.CREATED);
    }
    
    @PostMapping("/ganado/ajeno")
    public ResponseEntity registraraAjeno(@RequestBody PeticionGanadoAjeno peticion) {
        Ajeno cabezaGanadoAjeno = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            cabezaGanadoAjeno = registroGanado.registrar(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(cabezaGanadoAjeno, headers, HttpStatus.CREATED);
    }
    
    @PostMapping("/ganado/comprado")
    public ResponseEntity registrarComprado(@RequestBody PeticionGanadoComprado peticion) {
        Comprado cabezaGanadoComprado = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            cabezaGanadoComprado = registroGanado.registrar(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(cabezaGanadoComprado, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("/ganado/propio/{id}")
    public ResponseEntity modificarPropio(@PathVariable("id") String id, @RequestBody PeticionGanadoPropio peticion) {
        Propio cabezaGanadoPropio = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            cabezaGanadoPropio = registroGanado.modificar(id, peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(cabezaGanadoPropio, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("/ganado/ajeno/{id}")
    public ResponseEntity modificarAjeno(@PathVariable("id") String id, @RequestBody PeticionGanadoAjeno peticion) {
        Ajeno cabezaGanadoAjeno = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            cabezaGanadoAjeno = registroGanado.modificar(id, peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(cabezaGanadoAjeno, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("/ganado/comprado/{id}")
    public ResponseEntity modificarComprado(@PathVariable("id") String id, @RequestBody PeticionGanadoComprado peticion) {
        Comprado cabezaGanadoComprado = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            cabezaGanadoComprado = registroGanado.modificar(id, peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(cabezaGanadoComprado, headers, HttpStatus.CREATED);
    }
    
    @GetMapping("/ganado")
    public ResponseEntity listar() {
        List<Propio> ganadoPropio = registroGanado.listar();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(ganadoPropio, headers, HttpStatus.OK);
    }
    
    @GetMapping("/ganado/{id}")
    public ResponseEntity ver(@PathVariable("id") String id) {
        CabezaGanado cabezaGanado;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            cabezaGanado = registroGanado.ver(id);
            return new ResponseEntity(cabezaGanado, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, headers, HttpStatus.NOT_FOUND);
        }
    }
}
