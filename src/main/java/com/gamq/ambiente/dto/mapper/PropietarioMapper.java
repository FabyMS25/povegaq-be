package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.PropietarioDto;
import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.TipoContribuyente;

import java.util.stream.Collectors;

public class PropietarioMapper {
    public static PropietarioDto toPropietarioDto(Propietario propietario){
        return new PropietarioDto()
                .setUuid(propietario.getUuid())
                .setNombreCompleto(propietario.getNombreCompleto())
                .setNroDocumento(propietario.getNroDocumento())
                .setTipoDocumento(propietario.getTipoDocumento())
                .setExpedido(propietario.getExpedido())
                .setEmail(propietario.getEmail())
                .setNroTelefono(propietario.getNroTelefono())
                .setEstado(propietario.isEstado())
                .setTipoContribuyenteDto(propietario.getTipoContribuyente() == null? null: new TipoContribuyenteDto()
                        .setUuid(propietario.getTipoContribuyente().getUuid())
                        .setDescripcion(propietario.getTipoContribuyente().getDescripcion())
                        .setEstado(propietario.getTipoContribuyente().isEstado())
                )
                .setVehiculoDtoList(propietario.getVehiculoList().stream().map( vehiculo -> {
                    return VehiculoMapper.toVehiculoDto(vehiculo);
                }).collect(Collectors.toList()))
                ;
    }

    public static Propietario toPropietario(PropietarioDto propietarioDto){
        return new Propietario()
                .setUuid(propietarioDto.getUuid())
                .setNombreCompleto(propietarioDto.getNombreCompleto())
                .setNroDocumento(propietarioDto.getNroDocumento())
                .setTipoDocumento(propietarioDto.getTipoDocumento())
                .setExpedido(propietarioDto.getExpedido())
                .setEmail(propietarioDto.getEmail())
                .setNroTelefono(propietarioDto.getNroTelefono())
                .setEstado(propietarioDto.isEstado())
                .setTipoContribuyente(propietarioDto.getTipoContribuyenteDto() == null? null: new TipoContribuyente()
                        .setUuid(propietarioDto.getTipoContribuyenteDto().getUuid())
                        .setDescripcion(propietarioDto.getTipoContribuyenteDto().getDescripcion())
                        .setEstado(propietarioDto.getTipoContribuyenteDto().isEstado())
                )
                .setVehiculoList(propietarioDto.getVehiculoDtoList().stream().map(vehiculoDto -> {
                    return VehiculoMapper.toVehiculo(vehiculoDto);
                }).collect(Collectors.toList()))
                ;
    }
}
