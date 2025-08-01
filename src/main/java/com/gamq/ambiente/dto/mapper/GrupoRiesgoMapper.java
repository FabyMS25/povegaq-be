package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.CategoriaAireDto;
import com.gamq.ambiente.dto.GrupoRiesgoDto;
import com.gamq.ambiente.model.GrupoRiesgo;

public class GrupoRiesgoMapper {
    public static GrupoRiesgoDto toGrupoRiesgoDto(GrupoRiesgo grupoRiesgo){
        return new GrupoRiesgoDto()
                .setUuid(grupoRiesgo.getUuid())
                .setGrupo(grupoRiesgo.getGrupo())
                .setRecomendacion(grupoRiesgo.getRecomendacion())
                .setEstado(grupoRiesgo.isEstado())
                .setCategoriaAireDto(grupoRiesgo.getCategoriaAire() == null? null: new CategoriaAireDto()
                        .setUuid(grupoRiesgo.getCategoriaAire().getUuid())
                        .setCategoria(grupoRiesgo.getCategoriaAire().getCategoria())
                        .setDescripcion(grupoRiesgo.getCategoriaAire().getDescripcion())
                        .setRecomendacion(grupoRiesgo.getCategoriaAire().getRecomendacion())
                        .setNorma(grupoRiesgo.getCategoriaAire().getNorma())
                        .setValorMinimo(grupoRiesgo.getCategoriaAire().getValorMinimo())
                        .setValorMaximo(grupoRiesgo.getCategoriaAire().getValorMaximo())
                        .setColor(grupoRiesgo.getCategoriaAire().getColor())
                        .setEstado(grupoRiesgo.getCategoriaAire().isEstado())
                        .setActivo(grupoRiesgo.getCategoriaAire().isActivo())
                );
    }

    public static GrupoRiesgo toGrupoRiesgo(GrupoRiesgoDto grupoRiesgoDto){
        return new GrupoRiesgo()
                .setUuid(grupoRiesgoDto.getUuid())
                .setGrupo(grupoRiesgoDto.getGrupo())
                .setRecomendacion(grupoRiesgoDto.getRecomendacion())
                .setEstado(grupoRiesgoDto.isEstado());
    }
}
