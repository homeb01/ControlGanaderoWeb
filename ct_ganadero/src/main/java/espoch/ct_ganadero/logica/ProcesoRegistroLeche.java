package espoch.ct_ganadero.logica;

import espoch.ct_ganadero.datos.RegistroLecheCRUD;
import espoch.ct_ganadero.modelo.LechePorVaca;
import espoch.ct_ganadero.modelo.RegistroLeche;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.PostPersist;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ProcesoRegistroLeche {

    @Autowired
    private RegistroLecheCRUD registroLecheCrud;

    @PostPersist
    private void crearRegistroDiario(LechePorVaca registroLechePorVaca) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.now();
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(out);
        RegistroLeche registroLecheDiario = new RegistroLeche(
                calendar,
                0,
                0
        );
        registroLecheCrud.guardar(registroLecheDiario);
    }

}
