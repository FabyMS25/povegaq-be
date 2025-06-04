package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.TipoInfraccionDto;
import com.gamq.ambiente.enumeration.GradoInfraccion;

import java.util.List;

public interface TipoInfraccionService {
    TipoInfraccionDto obtenerTipoInfraccionPorUuid(String uuid);
    TipoInfraccionDto obtenerTipoInfraccionPorGrado(GradoInfraccion grado);
    List<TipoInfraccionDto> obtenerTipoInfracciones();
    TipoInfraccionDto crearTipoInfraccion(TipoInfraccionDto tipoInfraccionDto);
    TipoInfraccionDto actualizarTipoInfraccion(TipoInfraccionDto tipoInfraccionDto);
    TipoInfraccionDto eliminarTipoInfraccion(String uuid);
}
