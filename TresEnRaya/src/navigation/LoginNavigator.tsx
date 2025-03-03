import React from 'react'
import { createNativeStackNavigator, NativeStackScreenProps } from '@react-navigation/native-stack'
import Login from '../screen/Login'
import { PrincipalStackProps } from './PrincipalStack'
import ChooseMode from '../screen/ChooseMode';
import TresEnRayaOnline from '../screen/TresEnRayaOnline'
import Register from '../screen/Register'

type Props = NativeStackScreenProps<PrincipalStackProps, 'Online'>;

export type LoginStackProps = {
  Login: undefined;
  Register: undefined;
  ChooseMode: undefined;
  Partida: { id: number };
}

export const LoginNavigator = (props: Props) => {
  const Stack = createNativeStackNavigator<LoginStackProps>();

  return (
    <Stack.Navigator id={undefined} screenOptions={{ headerShown: false }}>
      <Stack.Screen name="Login" component={Login} />
      <Stack.Screen name="Register" component={Register} />
      <Stack.Screen name="ChooseMode" component={ChooseMode} />
      <Stack.Screen name="Partida" component={TresEnRayaOnline} />
    </Stack.Navigator>
  )
}