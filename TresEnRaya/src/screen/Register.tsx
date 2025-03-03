import { View, TextInput, TouchableHighlight, Text, TouchableOpacity, Alert } from 'react-native';
import React, { useState } from 'react';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import axios from 'axios';
import Icon from 'react-native-vector-icons/MaterialCommunityIcons';
import { LoginStackProps } from '../navigation/LoginNavigator';
import { loginStyles } from '../theme/LoginStyle';

type Props = NativeStackScreenProps<LoginStackProps, 'Register'>;

type Usuario = {
    nombre: string,
    password: string,
    email: string,
    // nombreFoto: string
}

const Register = ({ navigation }: Props) => {
    const [seePassword, setSeePassword] = useState<boolean>(false);
    const uri: string = 'http://164.92.131.208:8080/api/register';
    const [user, setUser] = useState<Usuario>({} as Usuario);

    function navigateLogin() {
        navigation.navigate('Login');
        navigation.reset({
            index: 0,
            routes: [{ name: 'Login' }]
        });
    }

    async function register() {
        try {
            const { status } = await axios.post(uri, user)
            if (status === 200) {
                Alert.alert('Usuario registrado, verifica su cuenta en su correo');
                navigateLogin();
            }
        } catch (error) {
            console.log(error);
            Alert.alert('No se pudo registrar el usuario');
        }
    }

    return (
        <View>
            <View style={[loginStyles.form, loginStyles.shadow]}>
                <Text style={loginStyles.title}>Register</Text>
                <TextInput style={loginStyles.input} placeholder='Nombre' placeholderTextColor='gray' onChangeText={(text) => setUser({ ...user, nombre: text } as Usuario)} />
                <TextInput style={loginStyles.input} placeholder='Email' placeholderTextColor='gray' inputMode='email' onChangeText={(text) => setUser({ ...user, email: text } as Usuario)} />
                <TextInput style={loginStyles.input} placeholder='Contraseña' placeholderTextColor='gray' secureTextEntry={!seePassword} onChangeText={(text) => setUser({ ...user, password: text } as Usuario)} />
                <TouchableOpacity onPress={() => setSeePassword(!seePassword)}>
                    <View style={{ flexDirection: 'row', margin: 7 }}>
                        <Icon name={seePassword ? "checkbox-marked" : "checkbox-blank-outline"} size={24} color="black" />
                        <Text style={{ color: 'black' }}>Mostrar Contraseña </Text>
                    </View>
                </TouchableOpacity>
                <TouchableHighlight style={loginStyles.button} onPress={register}>
                    <Text style={{ color: 'black' }}>Submit</Text>
                </TouchableHighlight>
                <View style={{ flexDirection: 'row', marginTop: 10, marginLeft: 10 }}>
                    <Text style={{ color: 'black' }}>¿Ya tienes cuenta? </Text>
                    <Text onPress={navigateLogin} style={loginStyles.link}>Login</Text>
                </View>
            </View>
        </View>
    )
}

export default Register