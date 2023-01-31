package espoch.ct_ganadero.controladores;

import espoch.ct_ganadero.modelo.Prenez;
import espoch.ct_ganadero.peticiones.PeticionPrenez;
import espoch.ct_ganadero.servicios.RegistroPrenez;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/")
@AllArgsConstructor
public class PrenezControlador {
    
    private final RegistroPrenez registroPrenez;
    
    @PostMapping("/prenez/crear")
    public ResponseEntity registrarPrenez(@RequestBody PeticionPrenez peticion) {
        Prenez prenez;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            prenez = registroPrenez.registrar(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(prenez, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("/prenez/actualizar/produccion")
    public ResponseEntity marcarInicioProduccion(@RequestBody PeticionPrenez peticion) {
        Prenez prenez;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            prenez = registroPrenez.actualizarProduccion(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(prenez, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("/prenez/actualizar/aborto")
    public ResponseEntity marcarFinPrenez(@RequestBody PeticionPrenez peticion) {
        Prenez prenez;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            prenez = registroPrenez.actualizarAborto(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(prenez, headers, HttpStatus.CREATED);
    }
    
    @GetMapping("/prenez")
    public ResponseEntity listar() {
        List<Prenez> prenezList = registroPrenez.listar();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(prenezList, headers, HttpStatus.OK);
    }

    @GetMapping("/prenez/{id}")
    public ResponseEntity ver(@PathVariable("id") int id) {
        Prenez prenez;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            prenez = registroPrenez.ver(id);
            return new ResponseEntity(prenez, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/estaprenada/{id}")
    public ResponseEntity estaPrenada(@PathVariable("id") int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            return new ResponseEntity(registroPrenez.estaPrenada(id), headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/prenez/{id}")
    public ResponseEntity eliminar(@PathVariable("id") int id) {
        Prenez prenez;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            prenez = registroPrenez.eliminar(id);
            return new ResponseEntity(prenez, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    } 
}
