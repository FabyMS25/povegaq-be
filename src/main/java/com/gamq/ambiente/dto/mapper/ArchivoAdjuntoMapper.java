package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ArchivoAdjuntoDto;
import com.gamq.ambiente.model.ArchivoAdjunto;

public class ArchivoAdjuntoMapper {
    public static ArchivoAdjuntoDto toArchivoAdjuntoDto(ArchivoAdjunto archivoAdjunto){
        return new ArchivoAdjuntoDto()
                .setUuid(archivoAdjunto.getUuid())
                .setNombre(archivoAdjunto.getNombre())
                .setDescripcion(archivoAdjunto.getDescripcion())
                .setRutaArchivo(archivoAdjunto.getRutaArchivo())
                .setFechaAdjunto(archivoAdjunto.getFechaAdjunto())
                .setNombreUsuario(archivoAdjunto.getNombreUsuario())
                .setUuidUsuario(archivoAdjunto.getUuidUsuario())
                .setEstado(archivoAdjunto.isEstado())
                ;

    }

    public static ArchivoAdjunto toArchivoAdjunto(ArchivoAdjuntoDto archivoAdjuntoDto){
        return new ArchivoAdjunto()
                .setUuid(archivoAdjuntoDto.getUuid())
                .setNombre(archivoAdjuntoDto.getNombre())
                .setDescripcion(archivoAdjuntoDto.getDescripcion())
                .setRutaArchivo(archivoAdjuntoDto.getRutaArchivo())
                .setFechaAdjunto(archivoAdjuntoDto.getFechaAdjunto())
                .setNombreUsuario(archivoAdjuntoDto.getNombreUsuario())
                .setUuidUsuario(archivoAdjuntoDto.getUuidUsuario())
                .setEstado(archivoAdjuntoDto.isEstado())
                ;
    }

}
