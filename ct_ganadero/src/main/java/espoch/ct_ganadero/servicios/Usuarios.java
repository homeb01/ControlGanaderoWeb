package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.datos.PersonaCRUD;
import espoch.ct_ganadero.datos.UsuarioCRUD;
import espoch.ct_ganadero.logica.FechaValidator;
import espoch.ct_ganadero.logica.UsuarioValidator;
import espoch.ct_ganadero.modelo.Persona;
import espoch.ct_ganadero.modelo.Usuario;
import espoch.ct_ganadero.peticiones.PeticionUsuario;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class Usuarios implements UserDetailsService {

    private final UsuarioCRUD usuarioCrud;
    private final PersonaCRUD personaCrud;
    
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario = usuarioCrud.buscar(username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(usuario.getRol()));
            return new User(usuario.getUser(), usuario.getPassword(), authorities);
        } catch (Exception ex) {
            throw new UsernameNotFoundException("El usuario no fue encontrado");
        }

    }
    
    public Usuario registrar(PeticionUsuario peticion) throws IllegalStateException, Exception {
        boolean cedulaValida = UsuarioValidator.cedulaValida(peticion.getCedulaRuc());
        boolean nombreValido = UsuarioValidator.nombreValido(peticion.getNombre());
        boolean fechaNacimientoValida = FechaValidator.fechaValida(peticion.getFechaNacimiento());
        boolean sexoValido = UsuarioValidator.sexoValido(peticion.getSexo());
        boolean ciudadValida = UsuarioValidator.nombreValido(peticion.getCiudad());
        boolean usuarioValido = UsuarioValidator.usernameValido(peticion.getUser());
        boolean contrasenaValida = UsuarioValidator.passwValido(peticion.getPassword());
        boolean rolValido = UsuarioValidator.rolValido(peticion.getRol());
        
        if (!usuarioValido || usuarioCrud.contiene(peticion.getUser()))
            throw new IllegalStateException("El usuario ingresado ya se encuentra registrado");
        if (!contrasenaValida)
            throw new IllegalStateException("La contrasena ingresada no es valida");
        if (!cedulaValida || personaCrud.contiene(peticion.getCedulaRuc()))
            throw new IllegalStateException("Ya se ha registrado este usuario");
        if (!nombreValido)
            throw new IllegalStateException("El nombre ingresado no es valido");
        if (!fechaNacimientoValida)
            throw new IllegalStateException("La fecha de nacimiento ingresada no es valida");
        if (!sexoValido)
            throw new IllegalStateException("El sexo ingresado no es valido");
        if (!ciudadValida)
            throw new IllegalStateException("La ciudad ingresada no es valida");
        if (!rolValido)
            throw new IllegalStateException("El rol ingresado no es valido");
        
        Persona persona = new Persona(
                peticion.getCedulaRuc(), 
                peticion.getNombre(), 
                FechaValidator.StringACalendar(peticion.getFechaNacimiento()), 
                peticion.getSexo().charAt(0), 
                peticion.getCiudad()
        );
        
        Usuario usuario = new Usuario(
                persona, 
                peticion.getUser(), 
                encoder.encode(peticion.getPassword()), 
                peticion.getRol()
        );
        
        personaCrud.guardar(persona);
        usuarioCrud.guardar(usuario);
        return usuario;
    }
    
    public Usuario ver(String id) throws Exception {
        return usuarioCrud.buscar(id);
    }

}
