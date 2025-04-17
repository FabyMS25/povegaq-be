package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.model.Infraccion;

public class InfraccionMapper {
    public static InfraccionDto toInfraccionDto(Infraccion infraccion){
        return new InfraccionDto()
                .setUuid(infraccion.getUuid())
                .setFechaInfraccion(infraccion.getFechaInfraccion())
                .setEstadoPago(infraccion.isEstadoPago())
                .setFechaPago(infraccion.getFechaPago())
                .setMontoTotal(infraccion.getMontoTotal())
                .setNumeroTasa(infraccion.getNumeroTasa())
                .setStatusInfraccion(infraccion.getStatusInfraccion())
                .setEstado(infraccion.isEstado());
    }

    public static Infraccion toInfraccion(InfraccionDto infraccionDto){
        return new Infraccion()
                .setUuid(infraccionDto.getUuid())
                .setFechaInfraccion(infraccionDto.getFechaInfraccion())
                .setEstadoPago(infraccionDto.isEstadoPago())
                .setFechaPago(infraccionDto.getFechaPago())
                .setMontoTotal(infraccionDto.getMontoTotal())
                .setNumeroTasa(infraccionDto.getNumeroTasa())
                .setStatusInfraccion(infraccionDto.getStatusInfraccion())
                .setEstado(infraccionDto.isEstado());
    }
}
