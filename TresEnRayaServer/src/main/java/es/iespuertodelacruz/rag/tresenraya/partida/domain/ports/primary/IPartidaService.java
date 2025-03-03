package es.iespuertodelacruz.rag.tresenraya.partida.domain.ports.primary;

import java.util.List;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.EstadoEnum;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;

public interface IPartidaService {
        public Partida createPartida(int jugador1);

        public Partida savePartida(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
                        int turno, int ganador, int movimientos);

        public Partida updatePartida(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
                        int turno, int ganador, int movimientos);

        public Partida joinPartida(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
                        int turno, int ganador, int movimientos);

        public Partida jugar(int id, EstadoEnum estado, String[] tablero, int jugador1, int jugador2,
                        int turno, int ganador, int movimientos, int pos);

        public boolean deletePartida(int id);

        public Partida findPartidaById(int id);

        public List<Partida> findPartidasByEstado(String estado);

        public Partida findFirstPartidaByEstado(String estado);

        public List<Partida> findAllPartidas();
}
