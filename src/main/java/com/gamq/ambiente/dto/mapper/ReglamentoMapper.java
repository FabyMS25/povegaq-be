package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ReglamentoDto;
import com.gamq.ambiente.model.Reglamento;

import java.util.stream.Collectors;

public class ReglamentoMapper {
    public static ReglamentoDto toReglamentoDto(Reglamento reglamento){
        return new ReglamentoDto()
                .setUuid(reglamento.getUuid())
                .setCodigo(reglamento.getCodigo())
                .setDescripcion(reglamento.getDescripcion())
                .setActivo(reglamento.isActivo())
                .setFechaEmision(reglamento.getFechaEmision())
                .setEstado(reglamento.isEstado())
                .setTipoInfraccionDtoList(reglamento.getTipoInfraccionList().stream().map(tipoInfraccion -> {
                    return TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccion);
                }).collect(Collectors.toList()))
                ;
    }

    public static Reglamento toReglamento(ReglamentoDto reglamentoDto){
        return  new Reglamento()
                .setUuid(reglamentoDto.getUuid())
                .setCodigo(reglamentoDto.getCodigo())
                .setDescripcion(reglamentoDto.getDescripcion())
                .setFechaEmision(reglamentoDto.getFechaEmision())
                .setActivo(reglamentoDto.isActivo())
                .setEstado(reglamentoDto.isEstado());
    }
}
