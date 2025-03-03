import 'react-native-gesture-handler';
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import PrincipalStack from './src/navigation/PrincipalStack';

function App(): React.JSX.Element {
  return (
    <NavigationContainer>
      <PrincipalStack />
    </NavigationContainer>
  );
}

export default App;
