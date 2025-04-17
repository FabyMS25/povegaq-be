package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.model.TipoContribuyente;

public class TipoContribuyenteMapper {
    public static TipoContribuyenteDto toTipoContribuyenteDto(TipoContribuyente tipoContribuyente){
        return new TipoContribuyenteDto()
                .setUuid(tipoContribuyente.getUuid())
                .setDescripcion(tipoContribuyente.getDescripcion())
                .setEstado(tipoContribuyente.isEstado());
    }

    public static TipoContribuyente toTipoContribuyente(TipoContribuyenteDto tipoContribuyenteDto){
        return new TipoContribuyente()
                .setUuid(tipoContribuyenteDto.getUuid())
                .setDescripcion(tipoContribuyenteDto.getDescripcion())
                .setEstado(tipoContribuyenteDto.isEstado());
    }
}
