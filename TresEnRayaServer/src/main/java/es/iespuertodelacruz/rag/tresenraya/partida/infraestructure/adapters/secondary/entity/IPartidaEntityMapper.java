package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.secondary.entity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.EstadoEnum;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;

@Mapper
public interface IPartidaEntityMapper {
    IPartidaEntityMapper INSTANCE = Mappers.getMapper(IPartidaEntityMapper.class);

    @Mapping(target = "estado", source = "estado", qualifiedByName = "intToEstado")
    @Mapping(target = "tablero", source = "tablero", qualifiedByName = "StringToTablero")
    Partida entityToDomain(PartidaEntity partidaEntity);

    @Mapping(target = "estado", source = "estado", qualifiedByName = "estadoToInt")
    @Mapping(target = "tablero", source = "tablero", qualifiedByName = "tableroToString")
    PartidaEntity domainToEntity(Partida partida);

    @Named("estadoToInt")
    default int estadoToInt(EstadoEnum estado) {
        if(estado == null){
            return -1;
        }
        return estado.ordinal();
    }

    @Named("intToEstado")
    default EstadoEnum intToEstado(int estadoIndex) {
        if(estadoIndex == -1){
            return null;
        }
        return EstadoEnum.values()[estadoIndex];
    }

    @Named("tableroToString")
    default String tableroToString(String[] tablero){
        if(tablero == null){
            return "";
        }
        String finalTablero = "";
        for(String symbol : tablero){
            finalTablero += symbol;
        }
        return finalTablero;
    }

    @Named("StringToTablero")
    default String[] tableroToString(String tablero){
        if(tablero == ""){
            return null;
        }
        return tablero.split("");
    }
}
