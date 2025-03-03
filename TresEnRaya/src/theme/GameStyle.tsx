import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    title: {
        textAlign: 'center',
        fontSize: 40,
        fontWeight: 'bold',
        color: 'black'
    },
    
    players: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        padding: 30,
        marginHorizontal: 30
    },

    names: {
        fontSize: 24,
        fontFamily: 'emoji',
        color: 'black'
    },

    board: {
        backgroundColor: 'white',
        margin: 20,
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'center',
        padding: 10,
        borderRadius: 20,
        borderColor: 'black',
        borderWidth: 2,
    },

    cell: {
        backgroundColor: 'red',
        margin: 10,
        padding: 30,
        borderRadius: 15,
        borderColor: 'darkred',
        borderWidth: 2,
    },

    symbol: {
        color: 'white',
        fontSize: 24,
        fontWeight: 'bold',
        textAlign: 'center'
    },

    button: {
        backgroundColor: 'red',
    },

    playersymbol: {
        borderRadius: 10,
        borderColor: 'gray',
        borderWidth: 2,
        padding: 20,
        margin: 'auto'
    }
})
