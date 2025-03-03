import { View, Text, TouchableHighlight, TextInput, TouchableOpacity, Alert } from 'react-native'
import React, { useEffect, useState } from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { PrincipalStackProps } from '../navigation/PrincipalStack';
import { styles } from '../theme/GameStyle';
import axios from 'axios';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';
import { useAppContext } from '../context/AppContextProvider';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { LoginStackProps } from '../navigation/LoginNavigator';
import { loginStyles } from '../theme/LoginStyle';

type Props = NativeStackScreenProps<LoginStackProps, 'Login'>;

type LoginProps = {
    nombre: string,
    password: string
}

const Login = ({ navigation }: Props) => {
    const [seePassword, setSeePassword] = useState<boolean>(false);
    const uri: string = 'http://164.92.131.208:8080/api/login';
    const [user, setUser] = useState<LoginProps>({} as LoginProps);
    const { setToken } = useAppContext();

    useEffect(() => {
        const getToken = async () => {
            const token = await AsyncStorage.getItem('token');
            if (token) {
                setToken(token);
                navigate("ChooseMode");
            }
        }
        getToken();
    }, [])

    async function login() {
        try {
            const { data, status } = await axios.post(uri, user);
            if (status === 200) {
                AsyncStorage.setItem('token', data);
                setToken(data);
                navigate("ChooseMode");
            }
        } catch (error) {
            console.log(error);
            Alert.alert("Usuario o contraseña incorrectos");
        }
    }

    function navigate(path: "ChooseMode" | "Register") {
        navigation.navigate(path);
        navigation.reset({
            index: 0,
            routes: [{ name: path }]
        });
    }

    return (
        <View>
            <View style={[loginStyles.form, loginStyles.shadow]}>
                <Text style={styles.title}>Login</Text>
                <TextInput style={loginStyles.input} placeholder='Name' placeholderTextColor='gray' onChangeText={(text) => setUser({ ...user, nombre: text })} />
                <TextInput style={loginStyles.input} placeholder='Password' placeholderTextColor='gray' secureTextEntry={!seePassword} onChangeText={(text) => setUser({ ...user, password: text })} />
                <TouchableOpacity onPress={() => setSeePassword(!seePassword)}>
                    <View style={{ flexDirection: 'row', margin: 7 }}>
                        <Icon name={seePassword ? "checkbox-marked" : "checkbox-blank-outline"} size={24} color="black" />
                        <Text style={{ color: 'black' }}>Mostrar Contraseña </Text>
                    </View>
                </TouchableOpacity>
                <TouchableHighlight style={loginStyles.button} onPress={login}>
                    <Text style={{ color: 'black' }}>Submit</Text>
                </TouchableHighlight>
                <View style={{ flexDirection: 'row', marginTop: 10, marginLeft: 10 }}>
                    <Text style={{ color: 'black' }}>¿No tienes cuenta? </Text>
                    <Text onPress={() => navigate("Register")} style={loginStyles.link}>Register</Text>
                </View>
            </View>
        </View >
    )
}

export default Login