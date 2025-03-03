package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.primary.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.EstadoEnum;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;
import es.iespuertodelacruz.rag.tresenraya.persona.domain.Persona;

@Mapper
public interface IPartidaDTOMapper {
    IPartidaDTOMapper INSTANCE = Mappers.getMapper(IPartidaDTOMapper.class);

    Persona map(Integer value);

    @Mapping(target = "estado", source = "estado", qualifiedByName = "stringToEstadoInt")
    @Mapping(target = "tablero", source = "tablero", qualifiedByName = "StringToTablero")
    Partida dtoToDomain(PartidaDTO partidaDTO);

    @Mapping(target = "estado", source = "estado", qualifiedByName = "intToEstadoString")
    @Mapping(target = "tablero", source = "tablero", qualifiedByName = "tableroToString")
    @Mapping(target = "jugador1", source = "jugador1.id")
    @Mapping(target = "jugador2", source = "jugador2.id")
    @Mapping(target = "turno", source = "turno.id")
    @Mapping(target = "ganador", source = "ganador.id")
    PartidaDTO domainToDTO(Partida partida);

    @Named("stringToEstadoInt")
    default int stringToEstadoInt(String estado) {
        return EstadoEnum.valueOf(estado).ordinal();
    }

    @Named("intToEstadoString")
    default String intToEstadoString(int estadoIndex) {
        return EstadoEnum.values()[estadoIndex].toString();
    }

    @Named("tableroToString")
    default String tableroToString(String[] tablero) {
        if (tablero == null) {
            return "";
        }
        String finalTablero = "";
        for (String symbol : tablero) {
            finalTablero += symbol;
        }
        return finalTablero;
    }

    @Named("StringToTablero")
    default String[] tableroToString(String tablero) {
        if (tablero == "") {
            return null;
        }
        return tablero.split("");
    }
}