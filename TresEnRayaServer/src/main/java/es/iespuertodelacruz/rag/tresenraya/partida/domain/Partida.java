package es.iespuertodelacruz.rag.tresenraya.partida.domain;

import java.util.Objects;

import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;

public class Partida {
    public final static int MAX_MOVIMIENTOS = 9;
    public final static String SIMBOLO_JUGADOR1 = "X";
    public final static String SIMBOLO_JUGADOR2 = "O";

    private int id;
    private EstadoEnum estado;
    private String[] tablero;
    private Persona jugador1;
    private Persona jugador2;
    private Persona turno;
    private Persona ganador;
    private int movimientos;

    public Partida() {
    }

    public Partida(int id, EstadoEnum estado, String[] tablero, Persona jugador1, Persona jugador2, Persona turno,
            Persona ganador, int movimientos) {
        this.id = id;
        this.estado = estado;
        this.tablero = tablero;
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.turno = turno;
        this.ganador = ganador;
        this.movimientos = movimientos;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public String[] getTablero() {
        return this.tablero;
    }

    public void setTablero(String[] tablero) {
        this.tablero = tablero;
    }

    public Persona getJugador1() {
        return this.jugador1;
    }

    public void setJugador1(Persona jugador1) {
        this.jugador1 = jugador1;
    }

    public Persona getJugador2() {
        return this.jugador2;
    }

    public void setJugador2(Persona jugador2) {
        this.jugador2 = jugador2;
    }

    public Persona getTurno() {
        return this.turno;
    }

    public void setTurno(Persona turno) {
        this.turno = turno;
    }

    public Persona getGanador() {
        return this.ganador;
    }

    public void setGanador(Persona ganador) {
        this.ganador = ganador;
    }

    public int getMovimientos() {
        return this.movimientos;
    }

    public void setMovimientos(int movimientos) {
        this.movimientos = movimientos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Partida)) {
            return false;
        }
        Partida partida = (Partida) o;
        return id == partida.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", estado='" + getEstado() + "'" +
                ", tablero='" + getTablero() + "'" +
                ", persona1='" + getJugador1() + "'" +
                ", persona2='" + getJugador2() + "'" +
                ", turno='" + getTurno() + "'" +
                ", ganador='" + getGanador() + "'" +
                ", movimientos='" + getMovimientos() + "'" +
                "}";
    }

    public void crearPartida(Persona jugador1) {
        this.jugador1 = jugador1;
        this.estado = EstadoEnum.ESPERANDO;
    }

    public void unirsePartida(Persona jugador2) {
        this.estado = EstadoEnum.ACTIVA;
        this.jugador2 = jugador2;
        this.tablero = this.crearTablero();
        this.ganador = null;
        this.movimientos = 0;
        this.turno = this.jugador1;
    }

    public String[] crearTablero() {
        String[] arr = new String[MAX_MOVIMIENTOS];
        for (int i = 0; i < MAX_MOVIMIENTOS; i++) {
            arr[i] = " ";
        }
        return arr;
    }

    public boolean comprobarDisponibilidad(int pos) {
        if (this.estado == EstadoEnum.FINALIZADA) {
            return false;
        }
        // Si pulsas una casilla mayor que el tablero
        if (pos >= MAX_MOVIMIENTOS || pos < 0) {
            return false;
        }
        // Si la casilla está libre o no
        return this.tablero[pos].equals(" ");
    }

    public void jugar(int pos) {
        if (!this.comprobarDisponibilidad(pos)) {
            return;
        }

        if (turno.equals(jugador1)) {
            this.tablero[pos] = SIMBOLO_JUGADOR1;
        } else if (turno.equals(jugador2)) {
            this.tablero[pos] = SIMBOLO_JUGADOR2;
        } else {
            return;
        }
        this.movimientos++;

        if (this.comprobarGanador()) {
            this.estado = EstadoEnum.FINALIZADA;
            this.ganador = this.turno;
            return;

        } else if (this.movimientos >= MAX_MOVIMIENTOS) {
            this.estado = EstadoEnum.FINALIZADA;
            this.ganador = null;
            return;
        }

        if (this.turno.getId() == this.jugador1.getId()) {
            this.turno = this.jugador2;
        } else {
            this.turno = this.jugador1;
        }
    }

    public boolean comprobarGanador() {
        for (int i = 0; i < 3; i++) {
            // Comprobar columnas
            if ((this.tablero[i].equals(this.tablero[i + 3]) && this.tablero[i].equals(this.tablero[i + 6]))
                    && !this.tablero[i].equals(" ")) {
                return true;
            }

            // Comprobar filas
            if ((this.tablero[i * 3].equals(this.tablero[i * 3 + 1])
                    && this.tablero[i * 3].equals(this.tablero[i * 3 + 2]))
                    && !this.tablero[i * 3].equals(" ")) {
                return true;
            }

            // Comprobar diagonales
            if ((this.tablero[0].equals(this.tablero[4]) && this.tablero[0].equals(this.tablero[8]))
                    && !this.tablero[0].equals(" ")) {
                return true;
            }
            if ((this.tablero[2].equals(this.tablero[4]) && this.tablero[2].equals(this.tablero[6]))
                    && !this.tablero[2].equals(" ")) {
                return true;
            }
        }
        return false;
    }
}
