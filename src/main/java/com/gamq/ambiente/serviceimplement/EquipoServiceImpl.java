package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.EquipoDto;
import com.gamq.ambiente.dto.mapper.EquipoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Equipo;
import com.gamq.ambiente.repository.EquipoRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EquipoServiceImpl implements EquipoService {
    @Autowired
    EquipoRepository equipoRepository;
    @Autowired
    InspeccionRepository inspeccionRepository;
    @Override
    public EquipoDto obtenerEquipoPorUuid(String uuid) {
        Equipo equipo = obtenerEquipoPorUuidOThrow(uuid);
        return EquipoMapper.toEquipoDto(equipo);
    }

    @Override
    public EquipoDto obtenerEquipoPorNombre(String nombre) {
        Equipo equipo = equipoRepository.findByNombre(nombre)
                .orElseThrow(()-> new ResourceNotFoundException("equipo", "nombre", nombre));
        return EquipoMapper.toEquipoDto(equipo);
    }

    @Override
    public List<EquipoDto> obtenerEquipos() {
        List<Equipo> equipoList = equipoRepository.findAll();
        return  equipoList.stream().map( equipo -> {
            return  EquipoMapper.toEquipoDto(equipo);
        }).collect(Collectors.toList());
    }

    @Override
    public EquipoDto crearEquipo(EquipoDto equipoDto) {
        validarNombreNoNulo(equipoDto.getNombre());
        equipoRepository.findByNombre(equipoDto.getNombre())
                .ifPresent(e -> {throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El equipo ya existe");});
        Equipo nuevoEquipo = EquipoMapper.toEquipo(equipoDto);
        return EquipoMapper.toEquipoDto(equipoRepository.save(nuevoEquipo));
    }

    @Override
    public EquipoDto actualizarEquipo(EquipoDto equipoDto) {
        Equipo equipo = obtenerEquipoPorUuidOThrow(equipoDto.getUuid());
        if (equipoRepository.exitsEquipoLikeNombre(equipoDto.getNombre().toLowerCase(), equipoDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Equipo ya existe");
        }
        Equipo updateEquipo = EquipoMapper.toEquipo(equipoDto);
        updateEquipo.setIdEquipo(equipo.getIdEquipo());
        return EquipoMapper.toEquipoDto(equipoRepository.save(updateEquipo));
    }

    @Override
    public EquipoDto eliminarEquipo(String uuid) {
        Equipo equipo = obtenerEquipoPorUuidOThrow(uuid);
        if (inspeccionRepository.exitsInspeccionWithUuidEquipo(uuid)){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el equipo ya esta siendo usado por las inspecciones");
        }
        equipoRepository.delete(equipo);
        return EquipoMapper.toEquipoDto(equipo);
    }

    private Equipo obtenerEquipoPorUuidOThrow(String uuid){
        return equipoRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Equipo", "uuid", uuid));
    }

    private void validarNombreNoNulo(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ResourceNotFoundException("Equipo", "nombre", "null");
        }
    }
}
