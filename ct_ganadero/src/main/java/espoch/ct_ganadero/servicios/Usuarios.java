package espoch.ct_ganadero.servicios;

import espoch.ct_ganadero.datos.PersonaCRUD;
import espoch.ct_ganadero.datos.UsuarioCRUD;
import espoch.ct_ganadero.logica.ValidacionFecha;
import espoch.ct_ganadero.logica.ValidacionUsuario;
import espoch.ct_ganadero.modelo.Persona;
import espoch.ct_ganadero.modelo.Usuario;
import espoch.ct_ganadero.peticiones.PeticionUsuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
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
        boolean cedulaValida = ValidacionUsuario.cedulaValida(peticion.getCedulaRuc());
        boolean nombreValido = ValidacionUsuario.nombreValido(peticion.getNombre());
        boolean fechaNacimientoValida = ValidacionFecha.fechaValida(peticion.getFechaNacimiento());
        boolean sexoValido = ValidacionUsuario.sexoValido(peticion.getSexo());
        boolean ciudadValida = ValidacionUsuario.nombreValido(peticion.getCiudad());
        boolean usuarioValido = ValidacionUsuario.usernameValido(peticion.getUser());
        boolean contrasenaValida = ValidacionUsuario.passwValido(peticion.getPassword());
        boolean rolValido = ValidacionUsuario.rolValido(peticion.getRol());
        
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
                ValidacionFecha.StringACalendar(peticion.getFechaNacimiento()), 
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

    public Usuario modificar(String id, PeticionUsuario peticion) throws Exception {
        boolean cedulaValida = ValidacionUsuario.cedulaValida(peticion.getCedulaRuc());
        boolean nombreValido = ValidacionUsuario.nombreValido(peticion.getNombre());
        boolean fechaNacimientoValida = ValidacionFecha.fechaValida(peticion.getFechaNacimiento());
        boolean sexoValido = ValidacionUsuario.sexoValido(peticion.getSexo());
        boolean ciudadValida = ValidacionUsuario.nombreValido(peticion.getCiudad());
        boolean usuarioValido = ValidacionUsuario.usernameValido(peticion.getUser());
        boolean contrasenaValida = ValidacionUsuario.passwValido(peticion.getPassword());
        boolean rolValido = ValidacionUsuario.rolValido(peticion.getRol());
        
        if (!usuarioCrud.contiene(id))
            throw new IllegalStateException("El usuario a modificar no se encuentra registrado");
        if (!usuarioValido || usuarioCrud.contiene(peticion.getUser()))
            throw new IllegalStateException("El usuario nuevo ingresado ya se encuentra registrado");
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
                ValidacionFecha.StringACalendar(peticion.getFechaNacimiento()), 
                peticion.getSexo().charAt(0), 
                peticion.getCiudad()
        );
        
        Usuario usuario = new Usuario(
                persona, 
                peticion.getUser(), 
                encoder.encode(peticion.getPassword()), 
                peticion.getRol()
        );
        
        Usuario usuarioAnterior = usuarioCrud.buscar(id);
        personaCrud.modificar(usuarioAnterior.getPersona(), persona);
        usuarioCrud.modificar(id, usuario);
        return usuario;
    }
    
    public List<Usuario> listar() {
        return usuarioCrud.listar();
    }
    
    public Usuario ver(String id) throws Exception {
        boolean usuarioValido = ValidacionUsuario.usernameValido(id);
        if (!usuarioValido)
            throw new IllegalStateException("El usuario nuevo ingresado ya se encuentra registrado");
        return usuarioCrud.buscar(id);
    }
    
    public Usuario eliminar(String id) throws Exception {
        boolean usuarioValido = ValidacionUsuario.usernameValido(id);
        if (!usuarioValido)
            throw new IllegalStateException("El usuario nuevo ingresado ya se encuentra registrado");
        return usuarioCrud.eliminar(id);
    }
}
