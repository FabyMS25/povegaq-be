package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.dto.TipoInfraccionDto;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.TipoInfraccion;

public class TipoInfraccionMapper {
    public static TipoInfraccionDto toTipoInfraccionDto(TipoInfraccion tipoInfraccion){
        return new TipoInfraccionDto()
                .setUuid(tipoInfraccion.getUuid())
                .setValorUFV(tipoInfraccion.getValorUFV())
                .setGrado(tipoInfraccion.getGrado())
                .setFechaInicio(tipoInfraccion.getFechaInicio())
                .setFechaFin(tipoInfraccion.getFechaFin())
                .setEstado(tipoInfraccion.isEstado())
                .setTipoContribuyenteDto(tipoInfraccion.getTipoContribuyente() == null? null: new TipoContribuyenteDto()
                        .setUuid(tipoInfraccion.getTipoContribuyente().getUuid())
                        .setDescripcion(tipoInfraccion.getTipoContribuyente().getDescripcion())
                        .setEstado(tipoInfraccion.getTipoContribuyente().isEstado())
                );

    }

    public static TipoInfraccion toTipoInfraccion(TipoInfraccionDto tipoInfraccionDto){
        return new TipoInfraccion()
                .setUuid(tipoInfraccionDto.getUuid())
                .setValorUFV(tipoInfraccionDto.getValorUFV())
                .setGrado(tipoInfraccionDto.getGrado())
                .setFechaInicio(tipoInfraccionDto.getFechaInicio())
                .setFechaFin(tipoInfraccionDto.getFechaFin())
                .setEstado(tipoInfraccionDto.isEstado())
                .setTipoContribuyente(tipoInfraccionDto.getTipoContribuyenteDto() == null? null: new TipoContribuyente()
                        .setUuid(tipoInfraccionDto.getTipoContribuyenteDto().getUuid())
                        .setDescripcion(tipoInfraccionDto.getTipoContribuyenteDto().getDescripcion())
                        .setEstado(tipoInfraccionDto.getTipoContribuyenteDto().isEstado())
                );
   }
}
