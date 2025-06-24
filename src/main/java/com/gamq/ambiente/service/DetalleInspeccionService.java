package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionRequestDto;
import com.gamq.ambiente.model.DetalleInspeccion;

import java.util.List;

public interface DetalleInspeccionService {
    DetalleInspeccionDto obtenerDetalleInspeccionPorUuid(String uuid);
    List<DetalleInspeccionDto> obtenerDetalleInspecciones();
    DetalleInspeccionDto crearDetalleInspeccion(DetalleInspeccionDto detalleInspeccionDto);
    DetalleInspeccionDto actualizarDetalleInspeccion(DetalleInspeccionDto detalleInspeccionDto);
    DetalleInspeccionDto eliminarDetalleInspeccion(String uuid);

    void agregarEjecucion(String uuidInspeccion, List<DetalleInspeccion> nuevosDetalles);

    List<DetalleInspeccionDto> obtenerDetalleInpeccionUltimaEjecucion(String uuidInspeccion);
    List<DetalleInspeccionDto> obtenerDetalleInspeccionPorNroEjecucion(String uuidInspeccion, Integer nroEjecucion);
    List<DetalleInspeccionDto> addDetalleInspecionToInspeccion(InspeccionDetalleInspeccionDto inspeccionDetalleInspeccionDto);
    List<DetalleInspeccionDto> obtenerDetalleInspeccionPorUuidInspeccion(String uuidInspeccion);

    void registrarDetalleInspeccionGases(InspeccionRequestDto inspeccionRequestDto);
    List<DetalleInspeccionDto> registrarDetalleInspeccionMasivo(InspeccionRequestDto inspeccionRequestDto);
}
