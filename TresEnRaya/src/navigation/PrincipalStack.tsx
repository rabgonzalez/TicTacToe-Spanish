import React from 'react'
import { createNativeStackNavigator } from '@react-navigation/native-stack'
import { LoginNavigator } from './LoginNavigator'
import AppContextProvider from '../context/AppContextProvider';
import ChooseInternet from '../screen/ChooseInternet';
import ChooseMode from '../screen/ChooseMode';
import HomeNavigator from './HomeNavigator';

type Props = {}

export type PrincipalStackProps = {
  ChooseInternet: undefined;
  Offline: undefined;
  Online: undefined;
  ChooseMode: undefined;
}

const PrincipalStack = (props: Props) => {
  const Stack = createNativeStackNavigator<PrincipalStackProps>();
  return (
    <AppContextProvider>
      <Stack.Navigator id={undefined}>
        <Stack.Screen name="ChooseInternet" component={ChooseInternet} />
        <Stack.Screen name="Offline" component={HomeNavigator} />
        <Stack.Screen name="Online" component={LoginNavigator} />
        <Stack.Screen name="ChooseMode" component={ChooseMode} />
      </Stack.Navigator>
    </AppContextProvider>
  )
}

export default PrincipalStack