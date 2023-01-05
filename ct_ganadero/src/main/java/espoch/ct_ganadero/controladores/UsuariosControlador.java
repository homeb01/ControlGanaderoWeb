package espoch.ct_ganadero.controladores;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import espoch.ct_ganadero.modelo.Usuario;
import espoch.ct_ganadero.peticiones.PeticionUsuario;
import espoch.ct_ganadero.servicios.Usuarios;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class UsuariosControlador {

    private final Usuarios usuarios;

    @PostMapping("/usuarios/crear")
    public ResponseEntity registrarUsuario(@RequestBody PeticionUsuario peticion) {
        Usuario usuario = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            usuario = usuarios.registrar(peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(usuario, headers, HttpStatus.CREATED);
    }
    
    @PutMapping("/usuarios/{id}")
    public ResponseEntity modificarUsuario(@PathVariable("id") String id, @RequestBody PeticionUsuario peticion) {
        Usuario usuario = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            usuario = usuarios.modificar(id, peticion);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), headers, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(usuario, headers, HttpStatus.CREATED);
    }
    
    @GetMapping("/usuarios")
    public ResponseEntity listar() {
        List<Usuario> usuariosList = usuarios.listar();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(usuariosList, headers, HttpStatus.OK);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity ver(@PathVariable("id") String id) {
        Usuario usuario;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            usuario = usuarios.ver(id);
            return new ResponseEntity(usuario, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, headers, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity eliminar(@PathVariable("id") String id) {
        Usuario usuario;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            usuario = usuarios.eliminar(id);
            return new ResponseEntity(usuario, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e, headers, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/usuarios/token")
    public void actualizarToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algoritmo = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verificador = JWT.require(algoritmo).build();
                DecodedJWT decodedJWT = verificador.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Usuario usuario = usuarios.ver(username);
                String accessToken = JWT.create()
                        .withSubject(usuario.getUser())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", usuario.getRol())
                        .sign(algoritmo);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(403);
                Map<String, String> errors = new HashMap<>();
                errors.put("error", e.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        } else {
            throw new RuntimeException("Falta el token de actualizacion");
        }
    }

}
