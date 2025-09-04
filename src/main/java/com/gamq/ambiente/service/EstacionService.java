package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.EstacionDto;

import java.util.List;

public interface EstacionService {
    EstacionDto obtenerEstacionPorUuid(String uuid);
    EstacionDto obtenerEstacionPorNombre(String nombre);
    List<EstacionDto> obtenerEstaciones();
    EstacionDto crearEstacion(EstacionDto estacionDto);
    EstacionDto actualizarEstacion(EstacionDto estacionDto);
    EstacionDto eliminarEstacion(String uuid);
}
