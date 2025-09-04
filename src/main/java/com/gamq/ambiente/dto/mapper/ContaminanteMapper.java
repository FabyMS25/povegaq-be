package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ContaminanteDto;
import com.gamq.ambiente.model.Contaminante;

import java.util.stream.Collectors;

public class ContaminanteMapper {
    public static ContaminanteDto toContaminanteDto(Contaminante contaminante){
        return new ContaminanteDto()
                .setUuid(contaminante.getUuid())
                .setNombre(contaminante.getNombre())
                .setDescripcion(contaminante.getDescripcion())
                .setEstado(contaminante.isEstado())
                .setMedicionAireDtoList(contaminante.getMedicionAireList().stream().map(medicionAire -> {
                    return MedicionAireMapper.toMedicionAireDto(medicionAire);
                }).collect(Collectors.toList()))
                ;
    }

    public static Contaminante toContaminante(ContaminanteDto contaminanteDto){
        return new Contaminante()
                .setUuid(contaminanteDto.getUuid())
                .setNombre(contaminanteDto.getNombre())
                .setDescripcion(contaminanteDto.getDescripcion())
                .setEstado(contaminanteDto.isEstado());
    }
}
