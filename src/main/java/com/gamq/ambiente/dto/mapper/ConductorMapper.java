package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ConductorDto;
import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.model.Conductor;
import com.gamq.ambiente.model.TipoContribuyente;

public class ConductorMapper {
    public static ConductorDto toConductorDto(Conductor conductor){
        return new ConductorDto()
                .setUuid(conductor.getUuid())
                .setExpedido(conductor.getExpedido())
                .setNombreCompleto(conductor.getNombreCompleto())
                .setNumeroDocumento(conductor.getNumeroDocumento())
                .setTipoDocumento(conductor.getTipoDocumento())
                .setEmail(conductor.getEmail())
                .setEstado(conductor.isEstado())
                .setTipoContribuyenteDto(conductor.getTipoContribuyente() == null? null: new TipoContribuyenteDto()
                        .setUuid(conductor.getTipoContribuyente().getUuid())
                        .setDescripcion(conductor.getTipoContribuyente().getDescripcion())
                        .setCodigo(conductor.getTipoContribuyente().getCodigo())
                        .setEstado(conductor.getTipoContribuyente().isEstado())
                )
                ;
    }

    public static Conductor toConductor(ConductorDto conductorDto){
        return new Conductor()
                .setUuid(conductorDto.getUuid())
                .setExpedido(conductorDto.getExpedido())
                .setNombreCompleto(conductorDto.getNombreCompleto())
                .setNumeroDocumento(conductorDto.getNumeroDocumento())
                .setTipoDocumento(conductorDto.getTipoDocumento())
                .setEmail(conductorDto.getEmail())
                .setEstado(conductorDto.isEstado())
                .setTipoContribuyente(conductorDto.getTipoContribuyenteDto() == null? null: new TipoContribuyente()
                        .setUuid(conductorDto.getTipoContribuyenteDto().getUuid())
                        .setDescripcion(conductorDto.getTipoContribuyenteDto().getDescripcion())
                        .setCodigo(conductorDto.getTipoContribuyenteDto().getCodigo())
                        .setEstado(conductorDto.getTipoContribuyenteDto().isEstado())
                )
                ;
    }
}
