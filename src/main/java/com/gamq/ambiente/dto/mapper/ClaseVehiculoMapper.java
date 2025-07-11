package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ClaseVehiculoDto;
import com.gamq.ambiente.model.ClaseVehiculo;

import java.util.stream.Collectors;

public class ClaseVehiculoMapper {
    public static ClaseVehiculoDto toClaseVehiculoDto(ClaseVehiculo claseVehiculo){
        return new ClaseVehiculoDto()
                .setUuid(claseVehiculo.getUuid())
                .setNombre(claseVehiculo.getNombre())
                .setDescripcion(claseVehiculo.getDescripcion())
                .setEstado(claseVehiculo.isEstado())
                .setTipoClaseVehiculoDtoList(claseVehiculo.getTipoClaseVehiculoList().stream().map(tipoClaseVehiculo -> {
                    return TipoClaseVehiculoMapper.toTipoClaseVehiculoDtoSinClaseVehiculo(tipoClaseVehiculo);
                }).collect(Collectors.toList()))
                ;
    }
    public static ClaseVehiculo toClaseVehiculo(ClaseVehiculoDto claseVehiculoDto){
        return new ClaseVehiculo()
                .setUuid(claseVehiculoDto.getUuid())
                .setNombre(claseVehiculoDto.getNombre())
                .setDescripcion(claseVehiculoDto.getDescripcion())
                .setEstado(claseVehiculoDto.isEstado())
                ;
    }


}
