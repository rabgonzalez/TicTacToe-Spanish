import { View, Text, Button, FlatList, RefreshControl, TouchableOpacity } from 'react-native'
import React, { useEffect, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { HomeStackProps } from '../navigation/HomeNavigator';
import { dataSource, PartidaRepository } from '../data/Database';
import PartidaDB from '../data/PartidaDB';
import Partida from '../data/Partida';
import { styles } from '../theme/Style';

type Props = NativeStackScreenProps<HomeStackProps, 'Partida'>;

const Home = ({ navigation }: Props) => {
    const [load, setLoad] = useState<boolean>(false);
    const [partidas, setPartidas] = useState<PartidaDB[]>([]);

    useEffect(() => {
        const getPartidas = async () => {
            try {
                const partidas: PartidaDB[] = await PartidaRepository.find();
                setPartidas([...partidas]);
            } catch (error) {
                console.log(error);
            }
        }
        getPartidas();
    }, [load == true])

    const crearPartida = async () => {
        try {
            const partida = new Partida();

            const partidaDB = new PartidaDB();
            partidaDB.jugador = partida.jugador;
            partidaDB.maquina = partida.maquina;
            partidaDB.turno = partida.turno;
            partidaDB.tablero = partida.tablero.join('');
            partidaDB.jugadas = partida.jugadas;
            partidaDB.estado = partida.estado.toString();
            partidaDB.ganador = partida.ganador;
            partidaDB.fecha = new Date();

            const newPartida = await PartidaRepository.save(partidaDB);
            navigate(newPartida.id);
        } catch (error) {
            console.log(error);
        }
    }

    function navigate(id: number) {
        navigation.navigate('Partida', { id });
        navigation.reset({
            index: 0,
            routes: [{ name: 'Partida', params: { id } }]
        });
    }

    return (
        <View>
            <Button title='Crear' onPress={crearPartida} />
            {partidas && <FlatList
                refreshControl={<RefreshControl refreshing={load} onRefresh={() => {
                    setLoad(true)
                    setTimeout(() => {
                        setLoad(false);
                    }, 1000)
                }} />}
                data={partidas}
                renderItem={({ item }) => {
                    return (
                        <TouchableOpacity style={styles.list} onPress={() => navigate(item.id)}>
                            <Text style={{ color: 'black', textAlign: 'center' }}>Fecha: {item.fecha.toLocaleString("es-ES")}</Text>
                            <Text style={{ color: 'black', textAlign: 'center' }}>Estado: {item.estado}</Text>
                            <Text style={{ color: 'black', textAlign: 'center' }}>Movimientos: {item.jugadas}</Text>
                        </TouchableOpacity>
                    )
                }}
                keyExtractor={(item) => item.id + ''} />
            }
        </View>
    )
}

export default Home