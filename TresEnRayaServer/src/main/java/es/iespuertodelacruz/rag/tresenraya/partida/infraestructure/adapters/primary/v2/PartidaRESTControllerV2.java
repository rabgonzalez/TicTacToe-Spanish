package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.primary.v2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.EstadoEnum;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.ports.primary.IPartidaService;
import es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.primary.dto.IPartidaDTOMapper;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.primary.IPersonaService;
import es.iespuertodelacruz.rag.tresenraya.shared.security.JwtService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@CrossOrigin
@RequestMapping("/api/v2/partidas")
public class PartidaRESTControllerV2 {
    Logger logger = LoggerFactory.getLogger(PartidaRESTControllerV2.class.getName());

    @Autowired
    IPartidaService partidaService;

    @Autowired
    JwtService jwtService;

    @Autowired
    IPersonaService personaService;

    @PostMapping
    public ResponseEntity<?> createPartida() {
        if (partidaService.findFirstPartidaByEstado("ESPERANDO") != null) {
            logger.error("No puedes crear una partida si ya hay una en espera");
            return ResponseEntity.badRequest().body("No puedes crear una partida si ya hay una en espera");
        }

        Persona persona = personaService
                .getPersonaByNombre(SecurityContextHolder.getContext().getAuthentication().getName());
        if (persona == null) {
            logger.error("No se ha encontrado la persona");
            return ResponseEntity.badRequest().body("No se ha encontrado la persona");
        }

        Partida newPartida = partidaService.createPartida(persona.getId());
        if (newPartida != null) {
            logger.info("Partida creada correctamente");
            return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(newPartida));
        }
        logger.error("No se ha podido crear la partida");
        return ResponseEntity.badRequest().body("No se ha podido crear la partida");
    }

    @GetMapping
    public ResponseEntity<?> findPartidaByEstado(@RequestParam(required = false) String estado) {
        if (estado == null) {
            logger.error("Estado no válido");
            return ResponseEntity.badRequest().body("Estado no válido");
        }
        String estadoEnum = estado.toUpperCase();
        if (EstadoEnum.valueOf(estadoEnum) == null) {
            logger.error("Estado no válido");
            return ResponseEntity.badRequest().body("Estado no válido");
        }
        Partida searchedPartida = partidaService.findFirstPartidaByEstado(estadoEnum);
        if (searchedPartida != null) {
            logger.info("Partida encontrada");
            return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(searchedPartida));
        }

        if (!estadoEnum.equals(EstadoEnum.ESPERANDO.toString())) {
            logger.error("No se han encontrado partidas disponibles");
            return ResponseEntity.badRequest().body("No se han encontrado partidas disponibles");
        }
        // Si estás buscando una partida en espera y no hay ninguna, crea una nueva
        return createPartida();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPartidaById(@PathVariable(name = "id") Integer id) {
        Partida partida = partidaService.findPartidaById(id);
        if (partida == null) {
            logger.error("No se ha encontrado la partida");
            return ResponseEntity.badRequest().body("No se ha encontrado la partida");
        }
        logger.info("Partida encontrada");
        return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(partida));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> joinPartida(@PathVariable(name = "id") Integer id) {
        Partida partida = partidaService.findPartidaById(id);
        if (partida == null || partida.getJugador1() == null) {
            logger.error("No se ha encontrado la partida");
            return ResponseEntity.badRequest().body("No se ha encontrado la partida");
        }
        // TODO: si la partida está activa, pueden unirse los espectadores
        if (partida.getJugador2() != null || partida.getEstado() != EstadoEnum.ESPERANDO) {
            logger.error("La partida ya está en juego");
            return ResponseEntity.badRequest().body("La partida ya está en juego");
        }

        Persona jugador2 = personaService
                .getPersonaByNombre(SecurityContextHolder.getContext().getAuthentication().getName());
        if (jugador2 == null || jugador2.equals(partida.getJugador1())) {
            logger.error("No se ha encontrado el jugador");
            return ResponseEntity.badRequest().body("No se ha encontrado el jugador");
        }

        int turnoId = partida.getTurno() == null ? 0 : partida.getTurno().getId();
        int ganadorId = partida.getGanador() == null ? 0 : partida.getGanador().getId();
        Partida updatedPartida = partidaService.joinPartida(partida.getId(), partida.getEstado(),
                partida.getTablero(), partida.getJugador1().getId(),
                jugador2.getId(), turnoId, ganadorId,
                partida.getMovimientos());

        if (updatedPartida != null && jugador2.equals(updatedPartida.getJugador2())) {
            logger.info("Jugador unido a la partida");
            return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(updatedPartida));
        }
        logger.error("No se ha podido unir al jugador a la partida");
        return ResponseEntity.badRequest().body("No se ha podido unir al jugador a la partida");
    }

    @PutMapping("/{id}/{pos}")
    public ResponseEntity<?> jugar(@PathVariable(name = "id") Integer id, @PathVariable(name = "pos") Integer pos) {
        Partida partida = partidaService.findPartidaById(id);
        if (partida == null || partida.getEstado() == EstadoEnum.FINALIZADA) {
            logger.error("No se ha encontrado la partida");
            return ResponseEntity.badRequest().body("No se ha encontrado la partida");
        }

        Persona jugador = personaService
                .getPersonaByNombre(SecurityContextHolder.getContext().getAuthentication().getName());
        if (jugador == null || !jugador.equals(partida.getTurno())) {
            logger.error("No se ha encontrado el jugador");
            return ResponseEntity.badRequest().body("No se ha encontrado el jugador");
        }

        int ganadorId = partida.getGanador() == null ? 0 : partida.getGanador().getId();
        Partida updatedPartida = partidaService.jugar(partida.getId(), partida.getEstado(),
                partida.getTablero(), partida.getJugador1().getId(),
                partida.getJugador2().getId(), partida.getTurno().getId(), ganadorId,
                partida.getMovimientos(), pos);

        if (updatedPartida != null && (updatedPartida.getMovimientos() > partida.getMovimientos())) {
            logger.info("Jugada realizada");
            return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(updatedPartida));
        }
        logger.error("No se ha podido realizar la jugada");
        return ResponseEntity.badRequest().body("No se ha podido realizar la jugada");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> abandonar(@PathVariable(name = "id") Integer id) {
        Partida partida = partidaService.findPartidaById(id);
        if (partida == null) {
            logger.error("No se ha encontrado la partida");
            return ResponseEntity.badRequest().body("No se ha encontrado la partida");
        }
        partida.setEstado(EstadoEnum.FINALIZADA);
        int jugador2Id = partida.getJugador2() == null ? 0 : partida.getJugador2().getId();
        int turnoId = partida.getTurno() == null ? 0 : partida.getTurno().getId();
        int ganadorId = partida.getGanador() == null ? 0 : partida.getGanador().getId();
        Partida updatedPartida = partidaService.updatePartida(partida.getId(), partida.getEstado(),
                partida.getTablero(), partida.getJugador1().getId(),
                jugador2Id, turnoId, ganadorId,
                partida.getMovimientos());
        return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(updatedPartida));
    }
}
