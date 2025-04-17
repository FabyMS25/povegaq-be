package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.model.Actividad;

public class ActividadMapper {
    public static ActividadDto toActividadDto(Actividad actividad){
        return new ActividadDto()
                .setUuid(actividad.getUuid())
                .setTipoActividad(actividad.getTipoActividad())
                .setFechaInicio(actividad.getFechaInicio())
                .setFechaFin(actividad.getFechaFin())
                .setActivo(actividad.isActivo())
                .setEstado(actividad.isEstado());
    }

    public static  Actividad toActividad(ActividadDto actividadDto){
        return new Actividad()
                .setUuid(actividadDto.getUuid())
                .setTipoActividad(actividadDto.getTipoActividad())
                .setFechaInicio(actividadDto.getFechaInicio())
                .setFechaFin(actividadDto.getFechaFin())
                .setActivo(actividadDto.isActivo())
                .setEstado(actividadDto.isEstado());
    }
}
