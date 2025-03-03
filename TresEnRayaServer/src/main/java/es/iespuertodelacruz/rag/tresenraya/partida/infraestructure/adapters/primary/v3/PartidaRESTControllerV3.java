package es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.primary.v3;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iespuertodelacruz.rag.tresenraya.partida.domain.EstadoEnum;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.Partida;
import es.iespuertodelacruz.rag.tresenraya.partida.domain.ports.primary.IPartidaService;
import es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.primary.dto.IPartidaDTOMapper;
import es.iespuertodelacruz.rag.tresenraya.partida.infraestructure.adapters.primary.dto.PartidaDTO;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin
@RequestMapping("/api/v3/partidas")
public class PartidaRESTControllerV3 {

    @Autowired
    IPartidaService partidaService;

    @GetMapping
    public ResponseEntity<?> getAllPartidas(@RequestParam(required = false) String estado) {
        List<Partida> partidas = null;
        if (estado == null) {
            partidas = partidaService.findAllPartidas();
        }
        String estadoEnum = estado.toUpperCase();

        if (EstadoEnum.valueOf(estadoEnum) != null) {
            partidas = partidaService.findPartidasByEstado(estadoEnum);
        }

        List<PartidaDTO> partidasDTO = new ArrayList<PartidaDTO>();
        for (Partida partida : partidas) {
            partidasDTO.add(IPartidaDTOMapper.INSTANCE.domainToDTO(partida));
        }
        return ResponseEntity.ok(partidasDTO);
    }

    @PostMapping
    public ResponseEntity<?> createPartida(@RequestBody PartidaDTO entity) {
        Partida createdPartida = partidaService.savePartida(0, EstadoEnum.valueOf(entity.estado()),
                entity.tablero().split(""),
                entity.jugador1(), entity.jugador2(), entity.turno(), entity.ganador(),
                entity.movimientos());
        return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(createdPartida));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePartida(@PathVariable(name = "id") Integer id, @RequestBody PartidaDTO entity) {
        Partida updatedPartida = partidaService.updatePartida(id, EstadoEnum.valueOf(entity.estado()),
                entity.tablero().split(""),
                entity.jugador1(), entity.jugador2(), entity.turno(), entity.ganador(),
                entity.movimientos());
        return ResponseEntity.ok(IPartidaDTOMapper.INSTANCE.domainToDTO(updatedPartida));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePartida(@PathVariable(name = "id") Integer id) {
        partidaService.deletePartida(id);
        return ResponseEntity.ok().build();
    }
}
