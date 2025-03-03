import {BaseEntity, Column, Entity, PrimaryGeneratedColumn} from 'typeorm';

@Entity('partidas')
export default class PartidaDB extends BaseEntity {
  @PrimaryGeneratedColumn() id: number;
  @Column('int') jugador: number;
  @Column('int') maquina: number;
  @Column('int') turno: number;
  @Column('text') tablero: string;
  @Column('int') jugadas: number;
  @Column('text') estado: String;
  @Column({type: 'int', nullable: true}) ganador: number;
  @Column('date') fecha: Date;
}
