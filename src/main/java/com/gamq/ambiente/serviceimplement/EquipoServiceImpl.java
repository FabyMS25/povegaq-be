package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.EquipoDto;
import com.gamq.ambiente.dto.mapper.EquipoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Equipo;
import com.gamq.ambiente.repository.EquipoRepository;
import com.gamq.ambiente.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EquipoServiceImpl implements EquipoService {
    @Autowired
    EquipoRepository equipoRepository;
    @Override
    public EquipoDto obtenerEquipoPorUuid(String uuid) {
        Optional<Equipo> equipoOptional = equipoRepository.findByUuid(uuid);
        if(equipoOptional.isPresent()){
            return EquipoMapper.toEquipoDto(equipoOptional.get());
        }
        throw new ResourceNotFoundException("Equipo", "uuid", uuid);
    }

    @Override
    public EquipoDto obtenerEquipoPorNombre(String nombre) {
        Optional<Equipo> equipoOptional = equipoRepository.findByNombre(nombre);
        if(equipoOptional.isPresent()){
            return EquipoMapper.toEquipoDto(equipoOptional.get());
        }
        throw new ResourceNotFoundException("Equipo", "descripcion", nombre);
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
        String nombre = equipoDto.getNombre();
        if(nombre==null){ throw new ResourceNotFoundException("Equipo","nombre", nombre);}
        Optional<Equipo> equipoOptional = equipoRepository.findByNombre(nombre);
        if(equipoOptional.isEmpty()){
            Equipo nuevoEquipo = EquipoMapper.toEquipo(equipoDto);
            return EquipoMapper.toEquipoDto(equipoRepository.save(nuevoEquipo));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Equipo ya existe");
    }

    @Override
    public EquipoDto actualizarEquipo(EquipoDto equipoDto) {
        Optional<Equipo> equipoOptional = equipoRepository.findByUuid(equipoDto.getUuid());
        if(equipoOptional.isPresent()) {
            if (!equipoRepository.exitsEquipoLikeNombre(equipoDto.getNombre().toLowerCase(), equipoDto.getUuid())) {
                Equipo updateEquipo = EquipoMapper.toEquipo(equipoDto);
                updateEquipo.setIdEquipo(equipoOptional.get().getIdEquipo());
                return EquipoMapper.toEquipoDto(equipoRepository.save(updateEquipo));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Equipo ya existe");
            }
        }
        throw new ResourceNotFoundException("Equipo", "uuid",equipoDto.getUuid());
    }

    @Override
    public EquipoDto eliminarEquipo(String uuid) {
        Equipo equipoQBE = new Equipo(uuid);
        Optional<Equipo> optionalEquipo = equipoRepository.findOne(Example.of(equipoQBE));
        if(optionalEquipo.isPresent()){
            Equipo equipo = optionalEquipo.get();
           // if(!equipo.getEquipoInspeccionList().isEmpty()){
           //     throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el equipo ya esta siendo usado por las inspecciones");
           // }
            equipoRepository.delete(equipo);
            return EquipoMapper.toEquipoDto(equipo);
        }
        throw new ResourceNotFoundException("Equipo","uuid", uuid);
    }

}
