package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.enumeration.StatusInfraccion;
import com.gamq.ambiente.model.Vehiculo;

import java.util.Date;
import java.util.List;

public interface InfraccionService {
    InfraccionDto obtenerInfraccionPorUuid(String uuid);
    List<InfraccionDto> obtenerInfraccionPorFecha(Date fecha);
    List<InfraccionDto> obtenerInfracciones();
    List<InfraccionDto> obtenerInfraccionPorVehiculo(String uuidVehiculo);
    InfraccionDto crearInfraccion(InfraccionDto InfraccionDto);
    InfraccionDto generarInfraccion(String uuidInspeccion);
    InfraccionDto actualizarInfraccion(InfraccionDto InfraccionDto);
    InfraccionDto eliminarInfraccion(String uuid);
    InfraccionDto marcarInfraccionComoPagada(String uuidInfraccion, String numeroTasa, Date fechaPago);
    InfraccionDto notificarInfraccion(String uuidInfraccion);
    InfraccionDto actualizarStatusInfraccion(String uuidInfraccion, StatusInfraccion nuevoStatus);
}
