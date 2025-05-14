package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ArchivoAdjuntoDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface ArchivoAdjuntoService {
    ArchivoAdjuntoDto obtenerArchivoAdjuntoPorUuid(String uuid);
    List<ArchivoAdjuntoDto> obtenerArchivosAdjuntos(String uuidRequisitoInspeccion);
    ArchivoAdjuntoDto crearArchivoAdjunto(ArchivoAdjuntoDto archivoAdjuntoDto);
    ArchivoAdjuntoDto actualizarArchivoAdjunto(ArchivoAdjuntoDto archivoAdjuntoDto);
    ArchivoAdjuntoDto eliminarArchivoAdjunto(String uuid);
    Resource descargarArchivo(String nombreFile);
}
