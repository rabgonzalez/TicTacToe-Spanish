package es.iespuertodelacruz.rag.tresenraya.shared.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity.IPersonaEntityMapper;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity.IPersonaEntityRepository;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity.PersonaEntity;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

	@Autowired
	private IPersonaEntityRepository personaRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Persona register(String nombre, String password, String email, String fotoNombre) {
		PersonaEntity entity = new PersonaEntity();
		entity.setNombre(nombre);
		entity.setPassword(passwordEncoder.encode(password));
		entity.setCorreo(email);
		entity.setRol("ROLE_USER");
		entity.setToken_verificacion(UUID.randomUUID().toString());
		entity.setVerificado(false);
		entity.setFotoNombre(fotoNombre);

		if(personaRepository.findByNombre(nombre) != null) {
			return null;
		}

		try {
			PersonaEntity usuario = personaRepository.save(entity);
			if (usuario == null) {
				return null;
			}
			return IPersonaEntityMapper.INSTANCE.entityToDomain(usuario);
		} catch (Exception e) {
			System.out.println("Error al guardar usuario");
			return null;
		}
	}

	public String login(String username, String password) {
		PersonaEntity persona = personaRepository.findByNombre(username);
		if (persona == null || !persona.isVerificado()) {
			return null;
		}

		if (passwordEncoder.matches(password, persona.getPassword())) {
			String generateToken = jwtService.generateToken(persona.getNombre(),
					persona.getRol());
			return generateToken;
		}
		return null;
	}

	@Transactional
	public boolean verify(int id, String uuid) {
		PersonaEntity persona = personaRepository.findById(id).orElse(null);

		if (persona != null && persona.getToken_verificacion().equals(uuid)) {
			persona.setVerificado(true);
			persona.setToken_verificacion(null);
			return true;
		}
		return false;
	}
}
