import { View, Text, TouchableOpacity, Alert } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { styles } from '../theme/GameStyle';
import { LoginStackProps } from '../navigation/LoginNavigator';
import { useAppContext } from '../context/AppContextProvider';
import axios from 'axios';

type Props = NativeStackScreenProps<LoginStackProps, 'Partida'>;

type Partida = {
    id: number,
    estado: string,
    ganador: number,
    jugador1: number,
    jugador2: number,
    movimientos: number,
    tablero: string,
    turno: number
}

const TresEnRayaOnline = ({ navigation, route }: Props) => {
    const uri: string = "http://164.92.131.208:8080/api/v2/partidas";
    const { token } = useAppContext();
    const [update, setUpdate] = useState<boolean>(false);

    let game = useRef<Partida>({} as Partida);
    const [gameRender, setGameRender] = useState<Partida>({} as Partida);
    let tableroRef = useRef<string[]>([]);

    const [nombre1, setNombre1] = useState<string>('');
    let nombre1Ref = useRef<string>("");

    const [nombre2, setNombre2] = useState<string>('');
    let nombre2Ref = useRef<string>("");

    useEffect(() => {
        const interval =
            setInterval(async () => {
                await getPartida();
                await getJugador1(game.current.jugador1);
                await getJugador2(game.current.jugador2);
                setUpdate(!update);
            }, 500);

        return () => {
            clearInterval(interval);
        }
    }, [route.params.id, update])

    async function getPartida() {
        try {
            const { data, status } = await axios.get(uri + "/" + route.params.id, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            if (status === 200) {
                game.current = data;
                tableroRef.current = data.tablero.split('');
                setGameRender({ ...data, tablero: tableroRef.current } as Partida);
            }
        } catch (error) {
        }
    }

    async function getJugador1(id: number) {
        try {
            const { data, status } = await axios.get("http://164.92.131.208:8080/api/v2/personas?id=" + id, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            if (status === 200) {
                nombre1Ref.current = data.nombre;
                setNombre1(data.nombre);
            }
        } catch (error) {
        }
    }

    async function getJugador2(id: number) {
        try {
            const { data, status } = await axios.get("http://164.92.131.208:8080/api/v2/personas?id=" + id, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
            if (status === 200) {
                nombre2Ref.current = data.nombre;
                setNombre2(data.nombre);
            }
        } catch (error) {
        }
    }

    async function jugar(pos: number) {
        try {
            const { data, status } = await axios.put(uri + "/" + route.params.id + "/" + pos, {}, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (status === 200) {
                game.current = data;
                tableroRef.current = data.tablero.split('');
                setGameRender({ ...data, tablero: tableroRef.current } as Partida);
            }
        } catch (error) {
        }
    }

    async function salir() {
        if (game.current.estado === "FINALIZADA") {
            return navigation.navigate('ChooseMode');
        }
        try {
            await axios.delete(uri + "/" + route.params.id, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            return navigation.navigate('ChooseMode');
        } catch (error) {
            console.log(error);
        }
    }

    return (
        <View style={{ flex: 1 }}>
            <Text style={styles.title}>Tres en Raya</Text>
            <View style={styles.players}>
                <View>
                    <Text style={styles.names}>{nombre1}</Text>
                    <View style={{ ...styles.playersymbol, backgroundColor: (game.current.turno == game.current.jugador1) ? 'red' : 'lightgray' }}>
                        <Text style={{ ...styles.symbol, fontSize: 15, color: (game.current.turno == game.current.jugador1) ? 'white' : 'black' }}>X</Text>
                    </View>
                </View>
                <View>
                    <Text style={styles.names}>{nombre2}</Text>
                    <View style={{ ...styles.playersymbol, backgroundColor: (game.current.turno == game.current.jugador2) ? 'red' : 'lightgray' }}>
                        <Text style={{ ...styles.symbol, fontSize: 15, color: (game.current.turno == game.current.jugador2) ? 'white' : 'black' }}>O</Text>
                    </View>
                </View>

            </View>
            {(game.current.estado == "FINALIZADA") &&
                <Text style={{ ...styles.names, textAlign: 'center' }}>Ganador: {(game.current.ganador === 0) ? "Empate" : (game.current.ganador === game.current.jugador1) ? nombre1Ref.current : nombre2Ref.current}</Text>
            }
            {tableroRef.current &&
                <View style={styles.board}>
                    {
                        tableroRef.current.map((casilla, index) => {
                            return (
                                <TouchableOpacity key={index} onPress={() => jugar(index)} style={{ ...styles.cell, flex: 1 }}>
                                    <Text style={styles.symbol}>{casilla}</Text>
                                </TouchableOpacity>
                            )
                        })
                    }
                </View>
            }
            <TouchableOpacity onPress={() => (game.current.estado === "FINALIZADA") ? salir() : Alert.alert(
                `¿Estás seguro de que quieres salir de la partida?`,
                'No podrás volver a conectarte',
                [
                    { text: 'Cancelar', onPress: () => console.log('Cancel Pressed'), style: 'cancel' },
                    { text: 'OK', onPress: salir },
                ],
            )} style={styles.button}>
                {/** TODO: si sales como espectador no debes finalizar la partida */}
                <Text style={{ ...styles.names, textAlign: 'center', color: 'white' }}>Salir</Text>
            </TouchableOpacity>

        </View>
    )
}

export default TresEnRayaOnline