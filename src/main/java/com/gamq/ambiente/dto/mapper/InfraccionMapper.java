package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.*;
import com.gamq.ambiente.model.Infraccion;

import java.util.stream.Collectors;

public class InfraccionMapper {
    public static InfraccionDto toInfraccionDto(Infraccion infraccion){
        return new InfraccionDto()
                .setUuid(infraccion.getUuid())
                .setFechaInfraccion(infraccion.getFechaInfraccion())
                .setMontoTotal(infraccion.getMontoTotal())
                .setStatusInfraccion(infraccion.getStatusInfraccion())
                .setEstadoPago(infraccion.isEstadoPago())
                .setFechaPago(infraccion.getFechaPago())
                .setNumeroTasa(infraccion.getNumeroTasa())
                .setMotivo(infraccion.getMotivo())
                .setNombreRegistrador(infraccion.getNombreRegistrador())
                .setUuidUsuario(infraccion.getUuidUsuario())
                .setEstado(infraccion.isEstado())
                .setTipoInfraccionDto(infraccion.getTipoInfraccion()==null? null: new TipoInfraccionDto()
                        .setUuid(infraccion.getTipoInfraccion().getUuid())
                        .setGrado(infraccion.getTipoInfraccion().getGrado())
                        .setValorUFV(infraccion.getTipoInfraccion().getValorUFV())
                        .setEstado(infraccion.getTipoInfraccion().isEstado())
                )
                .setVehiculoDto(infraccion.getVehiculo()==null? null: new VehiculoDto()
                        .setUuid(infraccion.getVehiculo().getUuid())
                        .setPlaca(infraccion.getVehiculo().getPlaca())
                        .setEsOficial(infraccion.getVehiculo().isEsOficial())
                        .setPoliza(infraccion.getVehiculo().getPoliza())
                        .setVinNumeroIdentificacion(infraccion.getVehiculo().getVinNumeroIdentificacion())
                        .setPinNumeroIdentificacion(infraccion.getVehiculo().getPinNumeroIdentificacion())
                        //tabla 2025
                        .setVehiculoTipoCombustibleDtoList(infraccion.getVehiculo().getVehiculoTipoCombustibleList().stream().map( vehiculoTipoCombustible -> {
                                    return VehiculoTipoCombustibleMapper.toDtoSinVehiculo(vehiculoTipoCombustible);
                        }).collect(Collectors.toList()))
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
                        .setExamenVisualConforme(infraccion.getInspeccion().isExamenVisualConforme())
                        .setGasesEscapeConforme(infraccion.getInspeccion().isGasesEscapeConforme())
                        .setFechaProximaInspeccion(infraccion.getInspeccion().getFechaProximaInspeccion())
                        .setEstado(infraccion.getInspeccion().isEstado())
                        .setEquipoDto(infraccion.getInspeccion().getEquipo()==null?null: new EquipoDto()
                                .setUuid(infraccion.getInspeccion().getEquipo().getUuid())
                                .setNombre(infraccion.getInspeccion().getEquipo().getNombre())
                                .setVersion(infraccion.getInspeccion().getEquipo().getVersion())
                                .setEstado(infraccion.getInspeccion().getEquipo().isEstado())
                        )
                )
                ;
    }

    public static Infraccion toInfraccion(InfraccionDto infraccionDto){
        return new Infraccion()
                .setUuid(infraccionDto.getUuid())
                .setFechaInfraccion(infraccionDto.getFechaInfraccion())
                .setMontoTotal(infraccionDto.getMontoTotal())
                .setStatusInfraccion(infraccionDto.getStatusInfraccion())
                .setEstadoPago(infraccionDto.isEstadoPago())
                .setFechaPago(infraccionDto.getFechaPago())
                .setNumeroTasa(infraccionDto.getNumeroTasa())
                .setMotivo(infraccionDto.getMotivo())
                .setNombreRegistrador(infraccionDto.getNombreRegistrador())
                .setUuidUsuario(infraccionDto.getUuidUsuario())
                .setEstado(infraccionDto.isEstado());
    }
}
