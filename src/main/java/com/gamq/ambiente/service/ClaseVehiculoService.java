package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ClaseVehiculoDto;

import java.util.List;

public interface ClaseVehiculoService {
    ClaseVehiculoDto obtenerClaseVehiculoPorUuid(String uuid);
    ClaseVehiculoDto obtenerClaseVehiculoPorNombre(String nombre);
    List<ClaseVehiculoDto> obtenerClaseVehiculos();
    ClaseVehiculoDto crearClaseVehiculo(ClaseVehiculoDto claseVehiculoDto);
    ClaseVehiculoDto actualizarClaseVehiculo(ClaseVehiculoDto claseVehiculoDto);
    ClaseVehiculoDto eliminarClaseVehiculo(String uuid);
}
