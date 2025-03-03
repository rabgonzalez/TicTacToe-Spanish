package es.iespuertodelacruz.rag.tresenraya.partida.domain.ports.secondary;

import java.util.List;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;

public interface IPartidaRepository {
    public List<Partida> findAllPartidas();

    public List<Partida> findPartidasByEstado(String estado);

    // cuando le das a buscar o observar, obtienes la primera partida activa o en espera
    public Partida findFirstPartidaByEstado(String estado);

    // contempla los cambios de la partida, tanto de si se une alguien, si se pone ficha o si cambia el estado
    public Partida findPartidaById(int id);

    public Partida createPartida(Partida partida);

    public Partida updatePartida(Partida partida);

    // elimina la partida si esta finaliza, si cancelan la búsqueda, si abandonan o si se desconectan
    public boolean deletePartida(int id);

    public Partida savePartida(Partida partida);
}
