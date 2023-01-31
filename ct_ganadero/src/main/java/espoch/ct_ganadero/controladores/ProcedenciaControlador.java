package espoch.ct_ganadero.controladores;

import espoch.ct_ganadero.modelo.Procedencia;
import espoch.ct_ganadero.peticiones.PeticionProcedencia;
import espoch.ct_ganadero.servicios.RegistroProcedencias;
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
public class ProcedenciaControlador {
    
    private final RegistroProcedencias procedencias;
    
    @PostMapping("/procedencia/crear")
    public ResponseEntity registrarProcedencia(@RequestBody PeticionProcedencia peticion) {
        Procedencia proc = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            proc = procedencias.registrar(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(proc, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("/procedencia/{id}")
    public ResponseEntity modificarProcedencia(@PathVariable("id") String id, @RequestBody PeticionProcedencia peticion) {
        Procedencia procedencia;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            procedencia = procedencias.modificar(id, peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(procedencia, headers, HttpStatus.CREATED);
    }
    
    @GetMapping("/procedencia")
    public ResponseEntity listar() {
        List<Procedencia> procedenciasList = procedencias.listar();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(procedenciasList, headers, HttpStatus.OK);
    }

    @GetMapping("/procedencia/{id}")
    public ResponseEntity ver(@PathVariable("id") String id) {
        Procedencia procedencia;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            procedencia = procedencias.ver(id);
            return new ResponseEntity(procedencia, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/procedencia/{id}")
    public ResponseEntity eliminar(@PathVariable("id") String id) {
        Procedencia procedencia;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            procedencia = procedencias.eliminar(id);
            return new ResponseEntity(procedencia, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }
}
