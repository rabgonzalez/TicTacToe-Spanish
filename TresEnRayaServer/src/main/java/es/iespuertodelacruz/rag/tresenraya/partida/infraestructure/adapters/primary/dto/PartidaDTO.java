package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.primary.dto;

public record PartidaDTO(int id, String estado, String tablero, int jugador1, int jugador2, int turno, int ganador,
        int movimientos) {
}
