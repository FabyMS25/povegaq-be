package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.RequisitoDto;
import com.gamq.ambiente.model.Requisito;

public class RequisitoMapper {
    public static RequisitoDto toRequisitoDto(Requisito requisito){
        return new RequisitoDto()
                .setUuid(requisito.getUuid())
                .setDescripcion(requisito.getDescripcion())
                .setEstado(requisito.isEstado());
    }

    public static Requisito toRequisito(RequisitoDto requisitoDto){
        return new Requisito()
                .setUuid(requisitoDto.getUuid())
                .setDescripcion(requisitoDto.getDescripcion())
                .setEstado(requisitoDto.isEstado());
    }
}
