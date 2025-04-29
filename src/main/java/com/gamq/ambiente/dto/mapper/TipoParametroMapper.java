package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.TipoParametroDto;
import com.gamq.ambiente.model.TipoParametro;

import java.util.stream.Collectors;

public class TipoParametroMapper {
    public static TipoParametroDto toTipoParametroDto(TipoParametro tipoParametro){
        return new TipoParametroDto()
                .setUuid(tipoParametro.getUuid())
                .setNombre(tipoParametro.getNombre())
                .setDescripcion(tipoParametro.getDescripcion())
                .setUnidad(tipoParametro.getUnidad())
                .setActivo(tipoParametro.isActivo())
                .setEstado(tipoParametro.isEstado())
                .setLimiteEmisionDtoList(tipoParametro.getLimiteEmisionList().stream().map( limiteEmision -> {
                    return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
                }).collect(Collectors.toList()))
                ;
    }
    public static TipoParametro toTipoParametro(TipoParametroDto tipoParametroDto){
        return new TipoParametro()
                .setUuid(tipoParametroDto.getUuid())
                .setNombre(tipoParametroDto.getNombre())
                .setDescripcion(tipoParametroDto.getDescripcion())
                .setUnidad(tipoParametroDto.getUnidad())
                .setActivo(tipoParametroDto.isActivo())
                .setEstado(tipoParametroDto.isEstado());
    }
}
