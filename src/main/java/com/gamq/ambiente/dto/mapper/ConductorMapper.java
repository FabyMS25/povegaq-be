package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ConductorDto;
import com.gamq.ambiente.model.Conductor;

public class ConductorMapper {
    public static ConductorDto toConductorDto(Conductor conductor){
        return new ConductorDto()
                .setUuid(conductor.getUuid())
                .setCodigoContribuyente(conductor.getCodigoContribuyente())
                .setExpedido(conductor.getExpedido())
                .setNombreCompleto(conductor.getNombreCompleto())
                .setNumeroDocumento(conductor.getNumeroDocumento())
                .setTipoDocumento(conductor.getTipoDocumento())
                .setTipoContribuyente(conductor.getTipoContribuyente())
                .setEstado(conductor.isEstado());
    }

    public static Conductor toConductor(ConductorDto conductorDto){
        return new Conductor()
                .setUuid(conductorDto.getUuid())
                .setCodigoContribuyente(conductorDto.getCodigoContribuyente())
                .setExpedido(conductorDto.getExpedido())
                .setNombreCompleto(conductorDto.getNombreCompleto())
                .setNumeroDocumento(conductorDto.getNumeroDocumento())
                .setTipoDocumento(conductorDto.getTipoDocumento())
                .setTipoContribuyente(conductorDto.getTipoContribuyente())
                .setEstado(conductorDto.isEstado());
    }
}
