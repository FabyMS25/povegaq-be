package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.RequisitoDto;
import com.gamq.ambiente.dto.RequisitoInspeccionDto;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Requisito;
import com.gamq.ambiente.model.RequisitoInspeccion;

import java.util.stream.Collectors;

public class RequisitoInspeccionMapper {
    public static RequisitoInspeccionDto toRequisitoInspeccionDto(RequisitoInspeccion requisitoInspeccion){
        return new RequisitoInspeccionDto()
                .setUuid(requisitoInspeccion.getUuid())
                .setCumple(requisitoInspeccion.isCumple())
                .setFechaPresentacion(requisitoInspeccion.getFechaPresentacion())
                .setEstado(requisitoInspeccion.isEstado())
                .setInspeccionDto(requisitoInspeccion.getInspeccion() == null? null: new InspeccionDto()
                        .setUuid(requisitoInspeccion.getInspeccion().getUuid())
                        .setFechaInspeccion(requisitoInspeccion.getInspeccion().getFechaInspeccion())
                        .setLugarInspeccion(requisitoInspeccion.getInspeccion().getLugarInspeccion())
                        .setNombreInspector(requisitoInspeccion.getInspeccion().getNombreInspector())
                        .setObservacion(requisitoInspeccion.getInspeccion().getObservacion())
                        .setResultado(requisitoInspeccion.getInspeccion().isResultado())
                        .setUuidUsuario(requisitoInspeccion.getInspeccion().getUuidUsuario())
                        .setEstado(requisitoInspeccion.getInspeccion().isEstado())
                )
                .setRequisitoDto(requisitoInspeccion.getRequisito() == null? null: new RequisitoDto()
                        .setUuid(requisitoInspeccion.getRequisito().getUuid())
                        .setDescripcion(requisitoInspeccion.getRequisito().getDescripcion())
                        .setEstado(requisitoInspeccion.getRequisito().isEstado())
                )
                .setArchivoAdjuntoDtoList(requisitoInspeccion.getArchivoAdjuntoList().stream().map(archivoAdjunto -> {
                    return ArchivoAdjuntoMapper.toArchivoAdjuntoDto(archivoAdjunto);
                }).collect(Collectors.toList()))
                ;
    }

    public static RequisitoInspeccion toRequisitoInspeccion(RequisitoInspeccionDto requisitoInspeccionDto){
        return new RequisitoInspeccion()
                .setUuid(requisitoInspeccionDto.getUuid())
                .setCumple(requisitoInspeccionDto.isCumple())
                .setFechaPresentacion(requisitoInspeccionDto.getFechaPresentacion())
                .setEstado(requisitoInspeccionDto.isEstado())
                /*.setInspeccion(requisitoInspeccionDto.getInspeccionDto()==null? null: new Inspeccion()
                        .setUuid(requisitoInspeccionDto.getInspeccionDto().getUuid())
                        .setFechaInspeccion(requisitoInspeccionDto.getInspeccionDto().getFechaInspeccion())
                        .setLugarInspeccion(requisitoInspeccionDto.getInspeccionDto().getLugarInspeccion())
                        .setNombreInspector(requisitoInspeccionDto.getInspeccionDto().getNombreInspector())
                        .setObservacion(requisitoInspeccionDto.getInspeccionDto().getObservacion())
                        .setResultado(requisitoInspeccionDto.getInspeccionDto().isResultado())
                        .setUuidUsuario(requisitoInspeccionDto.getInspeccionDto().getUuidUsuario())
                        .setEstado(requisitoInspeccionDto.getInspeccionDto().isEstado())
                )
                .setRequisito(requisitoInspeccionDto.getRequisitoDto()==null? null: new Requisito()
                        .setUuid(requisitoInspeccionDto.getRequisitoDto().getUuid())
                        .setDescripcion(requisitoInspeccionDto.getRequisitoDto().getDescripcion())
                        .setEstado(requisitoInspeccionDto.getRequisitoDto().isEstado())
                )*/
                ;

    }
}
