import { View, Text, TouchableOpacity } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import Partida from '../data/Partida';
import { styles } from '../theme/GameStyle';
import { EstadoEnum } from '../data/EstadoEnum';
import { PartidaRepository } from '../data/Database';
import { HomeStackProps } from '../navigation/HomeNavigator';
import PartidaDB from '../data/PartidaDB';

type Props = NativeStackScreenProps<HomeStackProps, 'Partida'>;

const TresEnRayaOffline = ({ navigation, route }: Props) => {
    let game = useRef({} as Partida);
    const [gameRender, setGameRender] = useState({} as Partida);

    useEffect(() => {
        const getPartida = async () => {
            try {
                const partidaDB: PartidaDB = await PartidaRepository.findOneBy({ id: route.params.id });
                
                const partida = new Partida();
                partida.jugador = partidaDB.jugador;
                partida.maquina = partidaDB.maquina;
                partida.turno = partidaDB.turno;
                partida.tablero = partidaDB.tablero.split('');
                partida.jugadas = partidaDB.jugadas;
                partida.estado = Number(partidaDB.estado);
                partida.ganador = partidaDB.ganador;

                game.current = partida;
                setGameRender({ ...game.current } as Partida);
            } catch (error) {
                console.log(error);
            }
        }
        getPartida();
    }, [route.params?.id])

    function jugar(pos: number) {
        game.current.jugar(pos);
        setGameRender({ ...game.current } as Partida);
    }

    const salir = async () => {
        const partidaDB = new PartidaDB();
        partidaDB.id = route.params.id;
        partidaDB.jugador = gameRender.jugador;
        partidaDB.maquina = gameRender.maquina;
        partidaDB.turno = gameRender.turno;
        partidaDB.tablero = gameRender.tablero.join('');
        partidaDB.jugadas = gameRender.jugadas;
        partidaDB.estado = gameRender.estado.toString();
        partidaDB.ganador = gameRender.ganador;
        partidaDB.fecha = new Date();

        await PartidaRepository.save(partidaDB);

        navigation.navigate('Home');
        navigation.reset({
            index: 0,
            routes: [{ name: 'Home' }]
        });
    }

    return (
        <View style={{ flex: 1 }}>
            <Text style={styles.title}>Tres en Raya</Text>
            <View style={styles.players}>
                <View>
                    <Text style={styles.names}>Jugador</Text>
                    <View style={{ ...styles.playersymbol, backgroundColor: 'lightgray' }}>
                        <Text style={{ ...styles.symbol, fontSize: 15, color: 'black' }}>X</Text>
                    </View>
                </View>
                <View>
                    <Text style={styles.names}>Máquina</Text>
                    <View style={{ ...styles.playersymbol, backgroundColor: 'lightgray' }}>
                        <Text style={{ ...styles.symbol, fontSize: 15, color: 'black' }}>O</Text>
                    </View>
                </View>

            </View>
            {(gameRender.estado === EstadoEnum.TERMINADA) &&
                <Text style={{ ...styles.names, textAlign: 'center' }}>Ganador: {(gameRender.ganador == null) ? "Empate" : (gameRender.ganador == 0) ? "Máquina" : "Jugador"}</Text>
            }
            <View style={styles.board}>
                {gameRender.tablero &&
                    gameRender.tablero.map((casilla, index) => {
                        return (
                            <TouchableOpacity key={index} onPress={() => jugar(index)} style={{ ...styles.cell, flex: 1 }}>
                                <Text style={styles.symbol}>{casilla}</Text>
                            </TouchableOpacity>
                        )
                    })
                }
            </View>
            <TouchableOpacity onPress={salir} style={styles.button}>
                <Text style={{ ...styles.names, textAlign: 'center', color: 'white' }}>Salir</Text>
            </TouchableOpacity>
        </View>
    )
}

export default TresEnRayaOffline