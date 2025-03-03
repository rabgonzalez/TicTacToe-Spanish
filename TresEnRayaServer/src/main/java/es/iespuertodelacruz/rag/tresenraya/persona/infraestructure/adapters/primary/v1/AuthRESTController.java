package es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.primary.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.shared.MailService;
import es.iespuertodelacruz.rag.tresenraya.shared.security.AuthService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthRESTController {
    Logger logger = LoggerFactory.getLogger(AuthRESTController.class);

    @Autowired
    AuthService authService;

    @Autowired
    private MailService mailService;

    static class RegisterPersona {
        public RegisterPersona() {
        }

        String nombre;
        String password;
        String email;
        String nombreFoto;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNombreFoto() {
            return nombre;
        }

        public void setNombreFoto(String nombreFoto) {
            this.nombreFoto = nombreFoto;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterPersona entity) {
        if (entity == null) {
            logger.error("Persona no puede ser null");
            return ResponseEntity.badRequest().body("Persona no puede ser null");
        }

        Persona createdPersona = authService.register(entity.getNombre(), entity.getPassword(), entity.getEmail(),
                entity.getNombreFoto());
        if (createdPersona != null) {
            enviarCorreo(createdPersona);
            logger.info("Correo enviado a " + entity.getEmail());
            return ResponseEntity
                    .ok().body("Usuario registrado, verifica el usuario en " + entity.getEmail()
                            + " para poder iniciar sesión");
        }
        logger.error("Error al crear la persona");
        return ResponseEntity.badRequest().body("Error al crear la persona");
    }

    static class LoginPersona {
        public LoginPersona() {
        }

        String nombre;
        String password;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPersona entity) {
        if (entity == null) {
            logger.error("Persona no puede ser null");
            return ResponseEntity.badRequest().body("Persona no puede ser null");
        }
        String token = authService.login(entity.getNombre(), entity.getPassword());
        if (token != null) {
            logger.info("logueado correctamente");
            return ResponseEntity.ok().body(token);
        }
        logger.error("nombre o contraeña erróneos");
        return ResponseEntity.badRequest().body("nombre o contraseña erróneos");
    }

    @GetMapping("verify/{id}/{uuid}")
    public ResponseEntity<?> verify(@PathVariable(name = "id") int id, @PathVariable(name = "uuid") String uuid) {
        if (authService.verify(id, uuid)) {
            logger.info("Usuario verificado correctamente");
            return ResponseEntity.ok().body("Usuario verificado correctamente");
        }
        logger.error("Error en la verificación");
        return ResponseEntity.badRequest().body("Error en la verificación");
    }

    public void enviarCorreo(Persona persona) {
        String senders[] = { persona.getCorreo() };
        mailService.send(senders, "Verifica el usuario",
                "http://164.92.131.208:8080/api/verify/" + persona.getId() + "/"
                        + persona.getToken_verificacion());
    }
}
