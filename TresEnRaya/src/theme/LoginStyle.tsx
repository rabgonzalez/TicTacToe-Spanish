import { StyleSheet } from "react-native";

export const loginStyles = StyleSheet.create({
    form: {
        padding: 20,
        backgroundColor: 'white',
        borderRadius: 10,
        margin: 20,
    },

    input: {
        borderColor: 'gray',
        borderWidth: 1,
        borderRadius: 10,
        color: 'black',
        margin: 10,
    },

    button: {
        backgroundColor: 'lightblue',
        padding: 10,
        borderRadius: 10,
        margin: 10,
        alignItems: 'center',
    },

    shadow: {
        shadowColor: 'black',
        elevation: 3,
    },

    link: {
        color: 'blue',
        textDecorationLine: 'underline',
    },

    title: {
        color: 'black',
        margin: 'auto',
        fontSize: 25,
        padding: 20
    },
});