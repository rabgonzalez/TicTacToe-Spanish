package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.secondary.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.EstadoEnum;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.ports.secondary.IPartidaRepository;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity.IPersonaEntityRepository;
import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity.PersonaEntity;
import jakarta.transaction.Transactional;

@Service
public class PartidaEntityService implements IPartidaRepository {
    @Autowired
    IPartidaEntityRepository partidaEntityRepository;

    @Autowired
    IPersonaEntityRepository personaEntityRepository;

    @Override
    public List<Partida> findAllPartidas() {
        List<PartidaEntity> partidas = partidaEntityRepository.findAll();
        List<Partida> partidasDomain = new ArrayList<>();
        for (PartidaEntity entity : partidas) {
            partidasDomain.add(IPartidaEntityMapper.INSTANCE.entityToDomain(entity));
        }
        return partidasDomain;
    }

    @Override
    public List<Partida> findPartidasByEstado(String estado) {
        String estadoEnum = estado.toUpperCase();
        int estadoInt = EstadoEnum.valueOf(estadoEnum).ordinal();
        List<PartidaEntity> partidas = partidaEntityRepository.findPartidasByEstado(estadoInt);
        List<Partida> partidasDomain = new ArrayList<>();
        for (PartidaEntity entity : partidas) {
            partidasDomain.add(IPartidaEntityMapper.INSTANCE.entityToDomain(entity));
        }
        return partidasDomain;
    }

    @Override
    public Partida findFirstPartidaByEstado(String estado) {
        String estadoEnum = estado.toUpperCase();
        int estadoInt = EstadoEnum.valueOf(estadoEnum).ordinal();
        List<PartidaEntity> entities = partidaEntityRepository.findPartidasByEstado(estadoInt);
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        PartidaEntity entity = entities.get(0);
        return IPartidaEntityMapper.INSTANCE.entityToDomain(entity);
    }

    @Override
    public Partida findPartidaById(int id) {
        PartidaEntity entity = partidaEntityRepository.findById(id).orElse(null);
        if (entity == null) {
            return null;
        }
        return IPartidaEntityMapper.INSTANCE.entityToDomain(entity);
    }

    @Override
    @Transactional
    public Partida createPartida(Partida partida) {
        if (partida == null) {
            return null;
        }
        PartidaEntity entity = IPartidaEntityMapper.INSTANCE.domainToEntity(partida);
        PersonaEntity jugador1 = personaEntityRepository.findById(partida.getJugador1().getId()).orElse(null);
        PersonaEntity jugador2 = null;
        PersonaEntity turno = null;
        PersonaEntity ganador = null;
        if (partida.getJugador2() != null) {
            jugador2 = personaEntityRepository
                    .findById(partida.getJugador2().getId()).orElse(null);
        }
        if (partida.getTurno() != null) {
            turno = personaEntityRepository
                    .findById(partida.getTurno().getId()).orElse(null);
        }
        if (partida.getGanador() != null) {
            ganador = personaEntityRepository
                    .findById(partida.getGanador().getId()).orElse(null);
        }
        entity.setJugador1(jugador1);
        entity.setJugador2(jugador2);
        entity.setTurno(turno);
        entity.setGanador(ganador);
        return IPartidaEntityMapper.INSTANCE.entityToDomain(partidaEntityRepository.save(entity));
    }

    @Override
    @Transactional
    public Partida updatePartida(Partida partida) {
        if (partida == null) {
            return null;
        }
        PartidaEntity entity = IPartidaEntityMapper.INSTANCE.domainToEntity(partida);
        PersonaEntity jugador1 = personaEntityRepository.findById(partida.getJugador1().getId()).orElse(null);
        PersonaEntity jugador2 = null;
        PersonaEntity turno = null;
        PersonaEntity ganador = null;
        if (partida.getJugador2() != null) {
            jugador2 = personaEntityRepository
                    .findById(partida.getJugador2().getId()).orElse(null);
        }
        if (partida.getTurno() != null) {
            turno = personaEntityRepository
                    .findById(partida.getTurno().getId()).orElse(null);
        }
        if (partida.getGanador() != null) {
            ganador = personaEntityRepository
                    .findById(partida.getGanador().getId()).orElse(null);
        }
        entity.setJugador1(jugador1);
        entity.setJugador2(jugador2);
        entity.setTurno(turno);
        entity.setGanador(ganador);
        return IPartidaEntityMapper.INSTANCE.entityToDomain(partidaEntityRepository.save(entity));
    }

    @Override
    @Transactional
    public boolean deletePartida(int id) {
        if ((partidaEntityRepository.findById(id).orElse(null)) == null) {
            return false;
        }
        partidaEntityRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public Partida savePartida(Partida partida) {
        if (partida == null) {
            return null;
        }
        PartidaEntity entity = IPartidaEntityMapper.INSTANCE.domainToEntity(partida);
        PersonaEntity jugador1 = personaEntityRepository.findById(partida.getJugador1().getId()).orElse(null);
        PersonaEntity jugador2 = null;
        PersonaEntity turno = null;
        PersonaEntity ganador = null;
        if (partida.getJugador2() != null) {
            jugador2 = personaEntityRepository
                    .findById(partida.getJugador2().getId()).orElse(null);
        }
        if (partida.getTurno() != null) {
            turno = personaEntityRepository
                    .findById(partida.getTurno().getId()).orElse(null);
        }
        if (partida.getGanador() != null) {
            ganador = personaEntityRepository
                    .findById(partida.getGanador().getId()).orElse(null);
        }
        entity.setJugador1(jugador1);
        entity.setJugador2(jugador2);
        entity.setTurno(turno);
        entity.setGanador(ganador);
        return IPartidaEntityMapper.INSTANCE.entityToDomain(partidaEntityRepository.save(entity));
    }
}
