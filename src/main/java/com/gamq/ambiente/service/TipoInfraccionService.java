package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.TipoInfraccionDto;

import java.util.List;

public interface TipoInfraccionService {
    TipoInfraccionDto obtenerTipoInfraccionPorUuid(String uuid);
    TipoInfraccionDto obtenerTipoInfraccionPorGrado(String grado);
    List<TipoInfraccionDto> obtenerTipoInfracciones();
    TipoInfraccionDto crearTipoInfraccion(TipoInfraccionDto tipoInfraccionDto);
    TipoInfraccionDto actualizarTipoInfraccion(TipoInfraccionDto tipoInfraccionDto);
    TipoInfraccionDto eliminarTipoInfraccion(String uuid);
}
