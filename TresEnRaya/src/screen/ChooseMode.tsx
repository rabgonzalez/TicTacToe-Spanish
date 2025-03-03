import { View, Text, Button, Alert } from 'react-native'
import React, { useEffect, useRef, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { LoginStackProps } from '../navigation/LoginNavigator'
import axios from 'axios';
import { useAppContext } from '../context/AppContextProvider';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { styles } from '../theme/Style';

type Props = NativeStackScreenProps<LoginStackProps, 'ChooseMode'>;

const ChooseMode = ({ navigation }: Props) => {
    const uri: string = 'http://164.92.131.208:8080/api/v2/partidas';
    const [loading, setLoading] = useState<boolean>(false);
    const loadingRef = useRef(false);
    const { token, setToken } = useAppContext();
    const partidaIdRef = useRef<number>(0);

    async function logout() {
        try {
            await AsyncStorage.removeItem('token');
            setToken('');

            navigation.navigate('Login');
            navigation.reset({
                index: 0,
                routes: [{ name: 'Login' }]
            });
        } catch (error) {
            console.log(error);
        }
    }

    const buscarPartida = async (url: string) => {
        const { data, status } = await axios.get(url, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
        if (status === 200) {
            partidaIdRef.current = data.id;
        }
    }

    const conectarse = async () => {
        try {
            await axios.put(uri + "/" + partidaIdRef.current, {}, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
        } catch (error) {
            console.log(error);
        }
    }

    const actualizar = async () => {
        setLoading(true);
        loadingRef.current = true;

        const interval =
            setInterval(async () => {
                if (!loadingRef.current) {
                    setLoading(false);
                    clearInterval(interval);
                }

                try {
                    const { data } = await axios.get(uri + "/" + partidaIdRef.current, {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    });

                    if (data.jugador2 !== 0) {
                        console.log(data);
                        clearInterval(interval);
                        setLoading(false);
                        loadingRef.current = false;
                        navigation.navigate('Partida', { id: data.id });
                    }
                } catch (error) {
                    console.log(error);
                }
            }, 500);
    }

    return (
        <View style={{ flex: 1 }}>
            {loading &&
                <View style={styles.loading}>
                    <Text style={{ color: 'white', justifyContent: 'center', textAlign: 'center', alignContent: 'center', margin: 'auto' }}>Esperando jugadores...</Text>
                    <Button title='Cancelar' onPress={() => loadingRef.current = false} />
                </View>
            }
            <View style={{ flex: 1 }}>
                <View style={{ flex: 1 }}>
                    <Button title='Buscar Partida' onPress={async () => {
                        await buscarPartida(uri + "?estado=ESPERANDO");
                        await conectarse();
                        actualizar();
                    }} />
                    <Button title='Observar Partida' onPress={async () => {
                        await buscarPartida(uri + "?estado=ACTIVA");
                        await conectarse();
                        actualizar();
                    }} />
                </View>
                <Button title='Logout' onPress={logout} color='red' />
            </View>
        </View>
    )
}

export default ChooseMode