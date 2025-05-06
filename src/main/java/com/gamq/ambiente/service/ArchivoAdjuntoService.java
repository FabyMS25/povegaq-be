package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ArchivoAdjuntoDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ArchivoAdjuntoService {
    ArchivoAdjuntoDto obtenerArchivoAdjuntoPorUuid(String uuid);
    ArchivoAdjuntoDto obtenerArchivoAdjuntoPorUuidActivoInactivo(String uuid);
    ArchivoAdjuntoDto obtenerArchivoAdjuntoPorNombre(String nombre);
    List<ArchivoAdjuntoDto> obtenerArchivosAdjuntos(String uuidTarea);
    List<ArchivoAdjuntoDto> obtenerArchivosAdjuntosActivosInactivos(String uuidTarea);
    ArchivoAdjuntoDto crearArchivoAdjunto(ArchivoAdjuntoDto archivoAdjuntoDto);
    ArchivoAdjuntoDto actualizarArchivoAdjunto(ArchivoAdjuntoDto archivoAdjuntoDto);
    ArchivoAdjuntoDto eliminarArchivoAdjunto(String uuid);
    Resource descargarArchivo(String nombreFile);
}
