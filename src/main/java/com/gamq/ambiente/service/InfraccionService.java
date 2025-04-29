package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.InfraccionDto;

import java.util.Date;
import java.util.List;

public interface InfraccionService {
    InfraccionDto obtenerInfraccionPorUuid(String uuid);
    List<InfraccionDto> obtenerInfraccionPorFechaInfraccion(Date fechaInfraccion);
    List<InfraccionDto> obtenerInfracciones();
    InfraccionDto crearInfraccion(InfraccionDto InfraccionDto);
    InfraccionDto actualizarInfraccion(InfraccionDto InfraccionDto);
    InfraccionDto eliminarInfraccion(String uuid);
}
