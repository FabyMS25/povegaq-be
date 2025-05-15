package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.TipoInfraccionDto;
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
                .setEstado(infraccion.isEstado())
                .setTipoInfraccionDto(infraccion.getTipoInfraccion()==null? null: new TipoInfraccionDto()
                        .setUuid(infraccion.getTipoInfraccion().getUuid())
                        .setGrado(infraccion.getTipoInfraccion().getGrado())
                        .setValorUFV(infraccion.getTipoInfraccion().getValorUFV())
                        .setEstado(infraccion.getTipoInfraccion().isEstado())
                )
                .setInspeccionDto(infraccion.getInspeccion()==null? null: new InspeccionDto()
                        .setUuid(infraccion.getInspeccion().getUuid())
                        .setResultado(infraccion.getInspeccion().isResultado())
                        .setObservacion(infraccion.getInspeccion().getObservacion())
                        .setFechaInspeccion(infraccion.getInspeccion().getFechaInspeccion())
                        .setLugarInspeccion(infraccion.getInspeccion().getLugarInspeccion())
                        .setNombreInspector(infraccion.getInspeccion().getNombreInspector())
                        .setUuidUsuario(infraccion.getInspeccion().getUuidUsuario())
                        .setAltitud(infraccion.getInspeccion().getAltitud())
                        .setEquipo(infraccion.getInspeccion().getEquipo())
                        .setExamenVisualConforme(infraccion.getInspeccion().isExamenVisualConforme())
                        .setGasesEscapeConforme(infraccion.getInspeccion().isGasesEscapeConforme())
                        .setFechaProximaInspeccion(infraccion.getInspeccion().getFechaProximaInspeccion())
                        .setEstado(infraccion.getInspeccion().isEstado())
                )

                ;
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
