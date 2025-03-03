import { DataSource } from "typeorm";
import PartidaDB from "./PartidaDB";

export const dataSource = new DataSource({
    database: 'tresenraya.db',
    entities: [PartidaDB],
    location: 'default',
    logging: [],
    synchronize: true,
    type: 'react-native',
});

export const PartidaRepository = dataSource.getRepository(PartidaDB);