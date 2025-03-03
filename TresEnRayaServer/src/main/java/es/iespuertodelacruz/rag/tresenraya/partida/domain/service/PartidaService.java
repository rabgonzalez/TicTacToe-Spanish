package es.iespuertodelacruz.rag.tresenraya.partida.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.EstadoEnum;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.ports.primary.IPartidaService;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.ports.secondary.IPartidaRepository;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.ports.secondary.IPersonaRepository;

@Service
public class PartidaService implements IPartidaService {
    @Autowired
    IPartidaRepository partidaRepository;

    @Autowired
    IPersonaRepository personaRepository;

    @Override
    public Partida createPartida(int userId) {
        Persona jugador1 = personaRepository.getPersonaById(userId);
        if (jugador1 == null) {
            return null;
        }
        Partida partida = new Partida();
        partida.crearPartida(jugador1);
        return partidaRepository.createPartida(partida);
    }

    @Override
    public Partida updatePartida(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
            int turno, int ganador, int movimientos) {
        Partida partida = new Partida(id, estado, tablero, null, null, null, null, movimientos);
        partida.setJugador1(personaRepository.getPersonaById(jugador1));
        partida.setJugador2(personaRepository.getPersonaById(jugador2));
        partida.setTurno(personaRepository.getPersonaById(turno));
        partida.setGanador(personaRepository.getPersonaById(ganador));
        return partidaRepository.updatePartida(partida);
    }

    @Override
    public Partida joinPartida(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
            int turno, int ganador, int movimientos) {
        Partida partida = new Partida(id, estado, tablero, null, null, null, null, movimientos);
        partida.setJugador1(personaRepository.getPersonaById(jugador1));
        partida.setJugador2(personaRepository.getPersonaById(jugador2));
        partida.setTurno(personaRepository.getPersonaById(turno));
        partida.setGanador(personaRepository.getPersonaById(ganador));
        partida.unirsePartida(partida.getJugador2());
        return partidaRepository.updatePartida(partida);
    }

    @Override
    public Partida jugar(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
            int turno, int ganador, int movimientos, int pos) {
        Partida partida = new Partida(id, estado, tablero, null, null, null, null, movimientos);
        partida.setJugador1(personaRepository.getPersonaById(jugador1));
        partida.setJugador2(personaRepository.getPersonaById(jugador2));
        partida.setTurno(personaRepository.getPersonaById(turno));
        partida.setGanador(personaRepository.getPersonaById(ganador));
        partida.jugar(pos);
        return partidaRepository.updatePartida(partida);
    }

    @Override
    public boolean deletePartida(int id) {
        return partidaRepository.deletePartida(id);
    }

    @Override
    public Partida findPartidaById(int id) {
        return partidaRepository.findPartidaById(id);
    }

    @Override
    public List<Partida> findPartidasByEstado(String estado) {
        return partidaRepository.findPartidasByEstado(estado);
    }

    @Override
    public Partida findFirstPartidaByEstado(String estado) {
        return partidaRepository.findFirstPartidaByEstado(estado);
    }

    @Override
    public List<Partida> findAllPartidas() {
        return partidaRepository.findAllPartidas();
    }

    @Override
    public Partida savePartida(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
            int turno, int ganador, int movimientos) {
        Partida partida = new Partida(id, estado, tablero, null, null, null, null, movimientos);
        partida.setJugador1(personaRepository.getPersonaById(jugador1));
        partida.setJugador2(personaRepository.getPersonaById(jugador2));
        partida.setTurno(personaRepository.getPersonaById(turno));
        partida.setGanador(personaRepository.getPersonaById(ganador));
        return partidaRepository.savePartida(partida);
    }
}
