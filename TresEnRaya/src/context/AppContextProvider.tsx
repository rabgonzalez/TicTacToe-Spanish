import React, { createContext, Dispatch, SetStateAction, useContext, useEffect, useState } from 'react';
import { dataSource } from '../data/Database';

export interface AppContextType {
    token: string;
    setToken: Dispatch<SetStateAction<string>>;
}

export const AppContext = createContext<AppContextType>({} as AppContextType);

const AppContextProvider = (props: any) => {
    const [token, setToken] = useState<string>('');

    useEffect(() => {
        const initDDBB = async () => {
            try {
                await dataSource.initialize();
                console.log('Base de datos inicializada');
            } catch (error) {
                console.log(error);
            }
        }
        initDDBB();
    }, [])

    const contextValues: AppContextType = {
        token,
        setToken,
    }

    return (
        <AppContext.Provider value={contextValues}>
            {props.children}
        </AppContext.Provider>
    )
}

export const useAppContext = () => {
    return useContext(AppContext);
}

export default AppContextProvider