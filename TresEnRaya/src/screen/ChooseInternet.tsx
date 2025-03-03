import { View, Button } from 'react-native'
import React from 'react'
import { NativeStackScreenProps } from '@react-navigation/native-stack'
import { PrincipalStackProps } from '../navigation/PrincipalStack';

type Props = NativeStackScreenProps<PrincipalStackProps, 'ChooseInternet'>;

const ChooseInternet = ({ navigation }: Props) => {
  return (
    <View style={{ flex: 1 }}>
      <Button title='Offline' onPress={() => navigation.navigate("Offline")} />
      <Button title='Online' onPress={() => navigation.navigate("Online")} />
    </View>
  )
}

export default ChooseInternet