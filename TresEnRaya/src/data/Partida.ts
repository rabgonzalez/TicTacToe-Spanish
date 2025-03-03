import { EstadoEnum } from './EstadoEnum';
import { BaseEntity, Column, Entity, PrimaryColumn } from 'typeorm';

//@Entity('partidas')
// extends BaseEntity
export default class Partida {
  casillas: number = 9;
  jugador: number; 
  maquina: number; 
  turno: number;
  tablero: string[];
  jugadas: number;
  estado: EstadoEnum; // "ESPERANDO" |  "ACTIVA" |  "FINALIZADA"
  ganador: number;

  constructor() {
    this.jugador = 1;
    this.maquina = 0;
    this.turno = this.jugador;
    this.tablero = this.crearTablero();
    this.jugadas = 0;
    this.estado = EstadoEnum.ACTIVA;
    this.ganador = null;
  }
                       
  crearTablero(){
    let arr: string[] = [];
    for(let i = 0; i < this.casillas; i++){
      arr.push(" ");
    }
    return arr;
  }

  comprobarDisponibilidad(pos: number) {
    if(this.estado === EstadoEnum.TERMINADA){
      return false;
    }
    // Si pulsas una casilla mayor que el tablero
    if(pos >= this.tablero.length) {
      return false;
    }
    if(this.turno !== this.jugador) {
      return false;
    }
    // Si la casilla está libre o no
    return this.tablero[pos] === " ";
  }

  jugar(pos: number) {
    if(!this.comprobarDisponibilidad(pos)){
      return;
    }
    this.tablero[pos] = "X";
    this.jugadas++;

    if(this.comprobarGanador()){
      this.estado = EstadoEnum.TERMINADA;
      this.ganador = this.jugador;
      return;
      
    } else if(this.jugadas >= this.casillas) {
      this.estado = EstadoEnum.TERMINADA;
      this.ganador = null;
      return;
    }

    this.turno = this.maquina;
    this.jugarMaquina();
  }

  jugarMaquina() {
    if(this.estado === EstadoEnum.TERMINADA){
      return;
    }
    // Comprueba que el medio esté libre 
    let medio: number = Math.floor(this.casillas / 2);
    if(this.jugadas === 1 && this.tablero[medio] === " "){
      this.tablero[medio] = "O";

    }  else {
      let pos: number = -1;
      do {
        pos = Math.floor(Math.random() * this.casillas);
      } while(this.tablero[pos] !== " ");
      
      this.tablero[pos] = "O";
    }
    this.jugadas++;

    if(this.comprobarGanador()){
      this.estado = EstadoEnum.TERMINADA;
      this.ganador = this.maquina;
      return;
    } else if(this.jugadas >= this.casillas) {
      this.estado = EstadoEnum.TERMINADA;
      this.ganador = null;
      return;
    }
    this.turno = this.jugador;
  }

  comprobarGanador() {
    for(let i = 0; i < 3; i++){
      // Comprobar columnas
      if((this.tablero[i] === this.tablero[i + 3] && this.tablero[i] === this.tablero[i + 6]) && this.tablero[i] !== " "){
        return true;
      }

      // Comprobar filas
      if((this.tablero[i * 3] === this.tablero[i * 3 + 1] && this.tablero[i * 3] === this.tablero[i * 3 + 2]) && this.tablero[i * 3] !== " "){
        return true;
      }

      // Comprobar diagonales
      if((this.tablero[0] === this.tablero[4] && this.tablero[0] === this.tablero[8]) && this.tablero[0] !== " "){
        return true;
      }
      if((this.tablero[2] === this.tablero[4] && this.tablero[2] === this.tablero[6]) && this.tablero[2] !== " "){
        return true;
      }
    }
  }
}
