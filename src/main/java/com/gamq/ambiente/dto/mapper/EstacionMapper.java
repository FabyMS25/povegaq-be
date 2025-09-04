package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.EstacionDto;
import com.gamq.ambiente.model.Estacion;

import java.util.stream.Collectors;

public class EstacionMapper {
    public static EstacionDto toEstacionDto(Estacion estacion){
        return  new EstacionDto()
                .setUuid(estacion.getUuid())
                .setNombre(estacion.getNombre())
                .setTipo(estacion.getTipo())
                .setUbicacion(estacion.getUbicacion())
                .setDescripcion(estacion.getDescripcion())
                .setEstado(estacion.isEstado())
                .setMedicionAireDtoList(estacion.getMedicionAireList().stream().map(medicionAire -> {
                    return MedicionAireMapper.toMedicionAireDto(medicionAire);
                }).collect(Collectors.toList()))
                ;
    }
    public static  Estacion toEstacion(EstacionDto estacionDto){
        return new Estacion()
                .setUuid(estacionDto.getUuid())
                .setNombre(estacionDto.getNombre())
                .setTipo(estacionDto.getTipo())
                .setUbicacion(estacionDto.getUbicacion())
                .setDescripcion(estacionDto.getDescripcion())
                .setEstado(estacionDto.isEstado());
    }
}
