package com.gamq.ambiente.dto.mapper;


import com.gamq.ambiente.dto.ClaseVehiculoDto;
import com.gamq.ambiente.dto.TipoClaseVehiculoDto;
import com.gamq.ambiente.model.TipoClaseVehiculo;

public class TipoClaseVehiculoMapper {
    public static TipoClaseVehiculoDto toTipoClaseVehiculoDto(TipoClaseVehiculo tipoClaseVehiculo){
        return new TipoClaseVehiculoDto()
                .setUuid(tipoClaseVehiculo.getUuid())
                .setNombre(tipoClaseVehiculo.getNombre())
                .setDescripcion(tipoClaseVehiculo.getDescripcion())
                .setEstado(tipoClaseVehiculo.isEstado())
                .setClaseVehiculoDto(tipoClaseVehiculo.getClaseVehiculo()==null? null: new ClaseVehiculoDto()
                        .setUuid(tipoClaseVehiculo.getClaseVehiculo().getUuid())
                        .setNombre(tipoClaseVehiculo.getClaseVehiculo().getNombre())
                        .setDescripcion(tipoClaseVehiculo.getClaseVehiculo().getDescripcion())
                        .setEstado(tipoClaseVehiculo.getClaseVehiculo().isEstado())
                );
    }
    
    public static TipoClaseVehiculoDto toTipoClaseVehiculoDtoSinClaseVehiculo(TipoClaseVehiculo tipoClaseVehiculo){
        return new TipoClaseVehiculoDto()
                .setUuid(tipoClaseVehiculo.getUuid())
                .setNombre(tipoClaseVehiculo.getNombre())
                .setDescripcion(tipoClaseVehiculo.getDescripcion())
                .setEstado(tipoClaseVehiculo.isEstado());
    }

    public static TipoClaseVehiculo toTipoClaseVehiculo(TipoClaseVehiculoDto tipoClaseVehiculoDto){
        return new TipoClaseVehiculo()
                .setUuid(tipoClaseVehiculoDto.getUuid())
                .setNombre(tipoClaseVehiculoDto.getNombre())
                .setDescripcion(tipoClaseVehiculoDto.getDescripcion())
                .setEstado(tipoClaseVehiculoDto.isEstado());
    }
}
