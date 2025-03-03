package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;

import es.iespuertodelacruz.rag.tresenraya.persona.infraestructure.adapters.secondary.entity.PersonaEntity;

@Entity(name = "partidas")
public class PartidaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "estado")
    private int estado;

    @Column(name = "tablero", length = 9)
    private String tablero;

    @ManyToOne
    @JoinColumn(nullable = true)
    private PersonaEntity jugador1;

    @ManyToOne
    @JoinColumn(nullable = true)
    private PersonaEntity jugador2;

    @ManyToOne
    @JoinColumn(nullable = true)
    private PersonaEntity turno;

    @ManyToOne
    @JoinColumn(nullable = true)
    private PersonaEntity ganador;

    @Column(name = "movimientos")
    private int movimientos;

    public PartidaEntity() {
    }

    public PartidaEntity(int id, int estado, String tablero, PersonaEntity jugador1, PersonaEntity jugador2,
            PersonaEntity turno, PersonaEntity ganador,
            int movimientos) {
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

    public int getEstado() {
        return this.estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getTablero() {
        return this.tablero;
    }

    public void setTablero(String tablero) {
        this.tablero = tablero;
    }

    public PersonaEntity getJugador1() {
        return this.jugador1;
    }

    public void setJugador1(PersonaEntity jugador1) {
        this.jugador1 = jugador1;
    }

    public PersonaEntity getJugador2() {
        return this.jugador2;
    }

    public void setJugador2(PersonaEntity jugador2) {
        this.jugador2 = jugador2;
    }

    public PersonaEntity getTurno() {
        return this.turno;
    }

    public void setTurno(PersonaEntity turno) {
        this.turno = turno;
    }

    public PersonaEntity getGanador() {
        return this.ganador;
    }

    public void setGanador(PersonaEntity ganador) {
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
        if (!(o instanceof PartidaEntity)) {
            return false;
        }
        PartidaEntity partidaEntity = (PartidaEntity) o;
        return id == partidaEntity.id;
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
                ", jugador1='" + getJugador1() + "'" +
                ", jugador2='" + getJugador2() + "'" +
                ", turno='" + getTurno() + "'" +
                ", ganador='" + getGanador() + "'" +
                ", movimientos='" + getMovimientos() + "'" +
                "}";
    }
}
