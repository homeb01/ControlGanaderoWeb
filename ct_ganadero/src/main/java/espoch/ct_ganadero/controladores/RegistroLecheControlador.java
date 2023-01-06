package espoch.ct_ganadero.controladores;

import espoch.ct_ganadero.logica.ValidacionFecha;
import espoch.ct_ganadero.modelo.LechePorVaca;
import espoch.ct_ganadero.modelo.RegistroLeche;
import espoch.ct_ganadero.peticiones.PeticionLechePorVaca;
import espoch.ct_ganadero.peticiones.PeticionTotalTerneros;
import espoch.ct_ganadero.servicios.RegistroLecheDiario;
import java.text.ParseException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class RegistroLecheControlador {

    private final RegistroLecheDiario registro;

    @PostMapping("/leche/registrar")
    public ResponseEntity registrarLecheXVaca(@RequestBody PeticionLechePorVaca peticion) throws Exception {
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

    @PutMapping("/leche/{id}")
    public ResponseEntity modificarRegistroXVaca(@PathVariable("id") int id, @RequestBody PeticionLechePorVaca peticion) {
        LechePorVaca registroXVaca = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            registroXVaca = registro.modificar(id, peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(registroXVaca, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/leche/{id}")
    public ResponseEntity eliminarRegistroVaca(@PathVariable("id") int id) {
        LechePorVaca registroXVaca;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            registroXVaca = registro.eliminar(id);
            return new ResponseEntity(registroXVaca, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, headers, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/leche/registros/listarde/{id}")
    public ResponseEntity listarRegistroVaca(@PathVariable("id") String id) throws ParseException {
        id = ValidacionFecha.idAFormatoAceptado(id);
        List<LechePorVaca> lechePorVaca = registro.listar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(lechePorVaca, headers, HttpStatus.OK);
    }

    @GetMapping("/leche/registros")
    public ResponseEntity listarRegistros() {
        List<RegistroLeche> registrosLeche = registro.listar();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(registrosLeche, headers, HttpStatus.OK);
    }

    @GetMapping("/leche/registros/ver/{id}")
    public ResponseEntity ver(@PathVariable("id") int id) {
        LechePorVaca lechePorVaca;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            lechePorVaca = registro.ver(id);
            return new ResponseEntity(lechePorVaca, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/leche/registros/{id}")
    public ResponseEntity ver(@PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            id = ValidacionFecha.idAFormatoAceptado(id);
            RegistroLeche registroLeche;
            registroLeche = registro.ver(id);
            return new ResponseEntity(registroLeche, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/leche/registrar/totalterneros")
    public ResponseEntity registrarTotalTerneros(@RequestBody PeticionTotalTerneros peticion) throws Exception {
        RegistroLeche registroLeche;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            registroLeche = registro.actualizarTotalTerneros(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(registroLeche, headers, HttpStatus.CREATED);
    }
    
    @GetMapping("/leche/registros/totalmanana/{id}")
    public ResponseEntity verTotalManana(@PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            id = ValidacionFecha.idAFormatoAceptado(id);
            String totalManana = registro.totalManana(id);
            return new ResponseEntity(totalManana, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/leche/registros/totaltarde/{id}")
    public ResponseEntity verTotalTarde(@PathVariable("id") String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            id = ValidacionFecha.idAFormatoAceptado(id);
            String totalTarde = registro.totalTarde(id);
            return new ResponseEntity(totalTarde, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), headers, HttpStatus.NOT_FOUND);
        }
    }
}
