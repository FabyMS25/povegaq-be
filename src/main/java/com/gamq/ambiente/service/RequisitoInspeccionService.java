package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.InspeccionRequisitoInspeccionDto;
import com.gamq.ambiente.dto.RequisitoInspeccionDto;

import java.util.List;

public interface RequisitoInspeccionService {
    RequisitoInspeccionDto obtenerRequisitoInspeccionPorUuid(String uuid);
    List<RequisitoInspeccionDto> obtenerRequisitoInspeccionPorUuidInspeccion(String uuidInspeccion);
    List<RequisitoInspeccionDto> obtenerRequisitoInspecciones();
    RequisitoInspeccionDto crearRequisitoInspeccion(RequisitoInspeccionDto RequisitoInspeccionDto);
    RequisitoInspeccionDto actualizarRequisitoInspeccion(RequisitoInspeccionDto RequisitoInspeccionDto);
    RequisitoInspeccionDto eliminarRequisitoInspeccion(String uuid);
    List<RequisitoInspeccionDto> addRequisitoInspeccionToInspeccion(InspeccionRequisitoInspeccionDto inspeccionRequisitoInspeccionDto);
}
