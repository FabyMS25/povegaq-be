package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.TipoCombustibleDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.VehiculoTipoCombustibleBasicoDto;
import com.gamq.ambiente.dto.VehiculoTipoCombustibleDto;
import com.gamq.ambiente.model.VehiculoTipoCombustible;

public class VehiculoTipoCombustibleMapper {
    public static VehiculoTipoCombustibleBasicoDto toVehiculoTipoCombustibleBasicoDto(VehiculoTipoCombustible vehiculoTipoCombustible){
        return new VehiculoTipoCombustibleBasicoDto()
                .setUuid(vehiculoTipoCombustible.getUuid())
                .setEsPrimario(vehiculoTipoCombustible.getEsPrimario())
                .setEstado(vehiculoTipoCombustible.isEstado());
    }
    public static VehiculoTipoCombustibleDto toVehiculoTipoCombustibleDto(VehiculoTipoCombustible vehiculoTipoCombustible){
        return new VehiculoTipoCombustibleDto()
                .setUuid(vehiculoTipoCombustible.getUuid())
                .setEsPrimario(vehiculoTipoCombustible.getEsPrimario())
                .setEstado(vehiculoTipoCombustible.isEstado())
                .setVehiculoDto(vehiculoTipoCombustible.getVehiculo()==null?null: new VehiculoDto()
                        .setUuid(vehiculoTipoCombustible.getVehiculo().getUuid())
                        .setEsOficial(vehiculoTipoCombustible.getVehiculo().isEsOficial())
                        .setFechaRegistro(vehiculoTipoCombustible.getVehiculo().getFechaRegistro())
                        .setJurisdiccionOrigen(vehiculoTipoCombustible.getVehiculo().getJurisdiccionOrigen())
                        .setPlaca(vehiculoTipoCombustible.getVehiculo().getPlaca())
                        .setPoliza(vehiculoTipoCombustible.getVehiculo().getPoliza())
                        .setVinNumeroIdentificacion(vehiculoTipoCombustible.getVehiculo().getVinNumeroIdentificacion())
                        .setEstado(vehiculoTipoCombustible.getVehiculo().isEstado())
                        .setEsUnidadIndustrial(vehiculoTipoCombustible.getVehiculo().isEsUnidadIndustrial())
                        .setEsMovil(vehiculoTipoCombustible.getVehiculo().getEsMovil())
                        .setPinNumeroIdentificacion(vehiculoTipoCombustible.getVehiculo().getPinNumeroIdentificacion())
                        .setNroCopiasPlaca(vehiculoTipoCombustible.getVehiculo().getNroCopiasPlaca())
                        .setPlacaAnterior(vehiculoTipoCombustible.getVehiculo().getPlacaAnterior())
                     //   .setChasis(vehiculoTipoCombustible.getVehiculo().getChasis())
                )
                .setTipoCombustibleDto(vehiculoTipoCombustible.getTipoCombustible()==null? null: new TipoCombustibleDto()
                        .setUuid(vehiculoTipoCombustible.getTipoCombustible().getUuid())
                        .setNombre(vehiculoTipoCombustible.getTipoCombustible().getNombre())
                        .setDescripcion(vehiculoTipoCombustible.getTipoCombustible().getDescripcion())
                        .setTipoMotor(vehiculoTipoCombustible.getTipoCombustible().getTipoMotor())
                        .setEstado(vehiculoTipoCombustible.getTipoCombustible().isEstado())

                ) ;
    }

    public static VehiculoTipoCombustibleDto toDtoSinVehiculo(VehiculoTipoCombustible vehiculoTipoCombustible) {
        return new VehiculoTipoCombustibleDto()
                .setUuid(vehiculoTipoCombustible.getUuid())
                .setEsPrimario(vehiculoTipoCombustible.getEsPrimario())
                .setEstado(vehiculoTipoCombustible.isEstado())
                .setTipoCombustibleDto(
                        vehiculoTipoCombustible.getTipoCombustible() != null
                                ? TipoCombustibleMapper.toTipoCombustibleDto(vehiculoTipoCombustible.getTipoCombustible())
                                : null
                );
    }

    public static VehiculoTipoCombustible toVehiculoTipoCombustible(VehiculoTipoCombustibleDto vehiculoTipoCombustibleDto){
        return  new VehiculoTipoCombustible()
                .setUuid(vehiculoTipoCombustibleDto.getUuid())
                .setEsPrimario(vehiculoTipoCombustibleDto.getEsPrimario())
                .setEstado(vehiculoTipoCombustibleDto.isEstado());
    }

}
