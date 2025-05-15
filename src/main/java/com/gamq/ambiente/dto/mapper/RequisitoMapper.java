package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.RequisitoDto;
import com.gamq.ambiente.model.Requisito;

public class RequisitoMapper {
    public static RequisitoDto toRequisitoDto(Requisito requisito){
        return new RequisitoDto()
                .setUuid(requisito.getUuid())
                .setDescripcion(requisito.getDescripcion())
                .setObligatorio(requisito.getObligatorio())
                .setEstado(requisito.isEstado());
    }

    public static Requisito toRequisito(RequisitoDto requisitoDto){
        return new Requisito()
                .setUuid(requisitoDto.getUuid())
                .setDescripcion(requisitoDto.getDescripcion())
                .setObligatorio(requisitoDto.getObligatorio())
                .setEstado(requisitoDto.isEstado());
    }
}
