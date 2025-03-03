import React, { useEffect } from 'react'
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack'
import { PrincipalStackProps } from './PrincipalStack'
import TresEnRayaOffline from '../screen/TresEnRayaOffline';
import Home from '../screen/Home';
import { dataSource } from '../data/Database';

type Props = NativeStackScreenProps<PrincipalStackProps, 'Offline'>;

export type HomeStackProps = {
    Home: undefined;
    Partida: { id: number }
}

const HomeNavigator = ({ navigation }: Props) => {
    const Stack = createNativeStackNavigator<HomeStackProps>();

    return (
        <Stack.Navigator id={undefined} screenOptions={{ headerShown: false }}>
            <Stack.Screen name='Home' component={Home} />
            <Stack.Screen name='Partida' component={TresEnRayaOffline} />
        </Stack.Navigator>
    )
}

export default HomeNavigator