package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.model.Vehiculo;

public class VehiculoMapper {
    public static VehiculoDto toVehiculoDto(Vehiculo vehiculo){
        return new VehiculoDto()
                .setUuid(vehiculo.getUuid())
                .setEsOficial(vehiculo.isEsOficial())
                .setFechaRegistro(vehiculo.getFechaRegistro())
                .setJuridiccionOrigen(vehiculo.getJuridiccionOrigen())
                .setPlaca(vehiculo.getPlaca())
                .setPoliza(vehiculo.getPoliza())
                .setVinNumeroIdentificacion(vehiculo.getVinNumeroIdentificacion())
                .setEstado(vehiculo.isEstado());
    }

    public static Vehiculo toVehiculo(VehiculoDto vehiculoDto){
        return new Vehiculo()
                .setUuid(vehiculoDto.getUuid())
                .setEsOficial(vehiculoDto.isEsOficial())
                .setFechaRegistro(vehiculoDto.getFechaRegistro())
                .setJuridiccionOrigen(vehiculoDto.getJuridiccionOrigen())
                .setPlaca(vehiculoDto.getPlaca())
                .setPoliza(vehiculoDto.getPoliza())
                .setVinNumeroIdentificacion(vehiculoDto.getVinNumeroIdentificacion())
                .setEstado(vehiculoDto.isEstado());
    }
}
