package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.CertificadoDto;
import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.TipoParametroDto;
import com.gamq.ambiente.model.DetalleInspeccion;
import com.gamq.ambiente.model.TipoParametro;

public class DetalleInspeccionMapper {
    public static DetalleInspeccionDto toDetalleInspeccionDto(DetalleInspeccion detalleInspeccion){
        return new DetalleInspeccionDto()
                .setUuid(detalleInspeccion.getUuid())
                .setValor(detalleInspeccion.getValor())
                .setNroEjecucion(detalleInspeccion.getNroEjecucion())
                .setEstado(detalleInspeccion.isEstado())
                .setTipoParametroDto(detalleInspeccion.getTipoParametro() == null? null: new TipoParametroDto()
                        .setUuid(detalleInspeccion.getTipoParametro().getUuid())
                        .setNombre(detalleInspeccion.getTipoParametro().getNombre())
                        .setDescripcion(detalleInspeccion.getTipoParametro().getDescripcion())
                        .setUnidad(detalleInspeccion.getTipoParametro().getUnidad())
                        .setActivo(detalleInspeccion.getTipoParametro().isActivo())
                        .setEstado(detalleInspeccion.getTipoParametro().isEstado())
                )
                .setInspeccionDto(detalleInspeccion.getInspeccion() == null? null: new InspeccionDto()
                        .setUuid(detalleInspeccion.getInspeccion().getUuid())
                        .setFechaInspeccion(detalleInspeccion.getInspeccion().getFechaInspeccion())
                        .setLugarInspeccion(detalleInspeccion.getInspeccion().getLugarInspeccion())
                        .setNombreInspector(detalleInspeccion.getInspeccion().getNombreInspector())
                        .setUuidUsuario(detalleInspeccion.getInspeccion().getUuidUsuario())
                        .setObservacion(detalleInspeccion.getInspeccion().getObservacion())
                        .setResultado(detalleInspeccion.getInspeccion().isResultado())
                );
    }

    public static DetalleInspeccion toDetalleInspeccion(DetalleInspeccionDto detalleInspeccionDto){
        return new DetalleInspeccion()
                .setUuid(detalleInspeccionDto.getUuid())
                .setValor(detalleInspeccionDto.getValor())
                .setNroEjecucion(detalleInspeccionDto.getNroEjecucion())
                .setEstado(detalleInspeccionDto.isEstado())
               /* .setTipoParametro(detalleInspeccionDto.getTipoParametroDto() == null? null: new TipoParametro()
                        .setUuid(detalleInspeccionDto.getTipoParametroDto().getUuid())
                        .setNombre(detalleInspeccionDto.getTipoParametroDto().getNombre())
                        .setDescripcion(detalleInspeccionDto.getTipoParametroDto().getDescripcion())
                        .setUnidad(detalleInspeccionDto.getTipoParametroDto().getUnidad())
                        .setActivo(detalleInspeccionDto.getTipoParametroDto().isActivo())
                        .setEstado(detalleInspeccionDto.getTipoParametroDto().isEstado())
                )*/;
    }
}
