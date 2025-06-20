package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.EquipoDto;
import com.gamq.ambiente.model.Equipo;

public class EquipoMapper {
    public static EquipoDto toEquipoDto(Equipo equipo){
        return new EquipoDto()
                .setUuid(equipo.getUuid())
                .setNombre(equipo.getNombre())
                .setVersion(equipo.getVersion())
                .setEstado(equipo.isEstado());
    }
    public static  Equipo toEquipo(EquipoDto equipoDto){
        return new Equipo()
                .setUuid(equipoDto.getUuid())
                .setNombre(equipoDto.getNombre())
                .setVersion(equipoDto.getVersion())
                .setEstado(equipoDto.isEstado());
    }

}
