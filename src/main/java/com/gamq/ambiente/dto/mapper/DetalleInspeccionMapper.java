package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.*;
import com.gamq.ambiente.model.DetalleInspeccion;

import java.util.stream.Collectors;


public class DetalleInspeccionMapper {
    public static DetalleInspeccionDto toDetalleInspeccionDto(DetalleInspeccion detalleInspeccion){
        return new DetalleInspeccionDto()
                .setUuid(detalleInspeccion.getUuid())
                .setValor(detalleInspeccion.getValor())
                .setTipoPrueba(detalleInspeccion.getTipoPrueba())
                .setResultadoParcial(detalleInspeccion.isResultadoParcial())
                .setNroEjecucion(detalleInspeccion.getNroEjecucion())
                .setLimitePermisible(detalleInspeccion.getLimitePermisible())
                .setEstado(detalleInspeccion.isEstado())
                .setTipoParametroDto(detalleInspeccion.getTipoParametro() == null? null: new TipoParametroDto()
                        .setUuid(detalleInspeccion.getTipoParametro().getUuid())
                        .setNombre(detalleInspeccion.getTipoParametro().getNombre())
                        .setDescripcion(detalleInspeccion.getTipoParametro().getDescripcion())
                        .setUnidad(detalleInspeccion.getTipoParametro().getUnidad())
                        .setActivo(detalleInspeccion.getTipoParametro().isActivo())
                        .setEstado(detalleInspeccion.getTipoParametro().isEstado())
                )
                .setTipoCombustibleDto(detalleInspeccion.getTipoCombustible()== null? null: new TipoCombustibleDto()
                        .setUuid(detalleInspeccion.getTipoCombustible().getUuid())
                        .setNombre(detalleInspeccion.getTipoCombustible().getNombre())
                        .setDescripcion(detalleInspeccion.getTipoCombustible().getDescripcion())
                        .setTipoMotor(detalleInspeccion.getTipoCombustible().getTipoMotor())
                        .setEstado(detalleInspeccion.getTipoCombustible().isEstado())
                )
                .setInspeccionDto(detalleInspeccion.getInspeccion() == null? null: new InspeccionDto()
                        .setUuid(detalleInspeccion.getInspeccion().getUuid())
                        .setFechaInspeccion(detalleInspeccion.getInspeccion().getFechaInspeccion())
                        .setLugarInspeccion(detalleInspeccion.getInspeccion().getLugarInspeccion())
                        .setNombreInspector(detalleInspeccion.getInspeccion().getNombreInspector())
                        .setUuidUsuario(detalleInspeccion.getInspeccion().getUuidUsuario())
                        .setObservacion(detalleInspeccion.getInspeccion().getObservacion())
                        .setResultado(detalleInspeccion.getInspeccion().isResultado())
                        .setConductorDto( detalleInspeccion.getInspeccion().getConductor() == null? null: new ConductorDto()
                                .setUuid(detalleInspeccion.getInspeccion().getConductor().getUuid())
                                .setNombre(detalleInspeccion.getInspeccion().getConductor().getNombre())
                                .setPrimerApellido(detalleInspeccion.getInspeccion().getConductor().getPrimerApellido())
                                .setSegundoApellido(detalleInspeccion.getInspeccion().getConductor().getSegundoApellido())
                                .setApellidoEsposo(detalleInspeccion.getInspeccion().getConductor().getApellidoEsposo())
                                .setTipoDocumento(detalleInspeccion.getInspeccion().getConductor().getTipoDocumento())
                                .setNumeroDocumento(detalleInspeccion.getInspeccion().getConductor().getNumeroDocumento())
                                .setExpedido(detalleInspeccion.getInspeccion().getConductor().getExpedido())
                                .setNroTelefono(detalleInspeccion.getInspeccion().getConductor().getNroTelefono())
                                .setEmail(detalleInspeccion.getInspeccion().getConductor().getEmail())
                                .setEstado(detalleInspeccion.getInspeccion().getConductor().isEstado())
                                .setTipoContribuyenteDto(detalleInspeccion.getInspeccion().getConductor().getTipoContribuyente()==null? null: new TipoContribuyenteDto()
                                        .setUuid(detalleInspeccion.getInspeccion().getConductor().getTipoContribuyente().getUuid())
                                        .setDescripcion(detalleInspeccion.getInspeccion().getConductor().getTipoContribuyente().getDescripcion())
                                        .setEstado(detalleInspeccion.getInspeccion().getConductor().getTipoContribuyente().isEstado())
                                        .setCodigo(detalleInspeccion.getInspeccion().getConductor().getTipoContribuyente().getCodigo())
                                )
                        )
                     /*   .setVehiculoDto(detalleInspeccion.getInspeccion().getVehiculo() == null ? null: new VehiculoDto()
                                .setUuid(detalleInspeccion.getInspeccion().getVehiculo().getUuid())
                                .setPlaca(detalleInspeccion.getInspeccion().getVehiculo().getPlaca())
                                .setVehiculoTipoCombustibleDtoList(detalleInspeccion.getInspeccion().getVehiculo().getVehiculoTipoCombustibleList().stream().map(vehiculoTipoCombustible -> {
                                    return VehiculoTipoCombustibleMapper.toDtoSinVehiculo(vehiculoTipoCombustible);
                                }).collect(Collectors.toList()))
                        )*/
                );
    }

    public static DetalleInspeccion toDetalleInspeccion(DetalleInspeccionDto detalleInspeccionDto){
        return new DetalleInspeccion()
                .setUuid(detalleInspeccionDto.getUuid())
                .setValor(detalleInspeccionDto.getValor())
                .setTipoPrueba(detalleInspeccionDto.getTipoPrueba())
                .setResultadoParcial(detalleInspeccionDto.isResultadoParcial())
                .setNroEjecucion(detalleInspeccionDto.getNroEjecucion())
                .setLimitePermisible(detalleInspeccionDto.getLimitePermisible())
                .setEstado(detalleInspeccionDto.isEstado());
    }
}
