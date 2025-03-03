import { StyleSheet } from "react-native";

export const styles = StyleSheet.create({
    loading: {
        flex: 1,
        position: 'absolute',
        backgroundColor: 'black',
        zIndex: 1,
        width: '100%',
        height: '100%',
        opacity: 0.9
    },

    list: {
        backgroundColor: 'white',
        margin: 10,
        borderRadius: 20,
        alignItems: 'center',
        padding: 20,
    },
});