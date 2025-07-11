package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.TipoClaseVehiculoDto;

import java.util.List;

public interface TipoClaseVehiculoService {
    TipoClaseVehiculoDto obtenerTipoClaseVehiculoPorUuid(String uuid);
    TipoClaseVehiculoDto obtenerTipoClaseVehiculoPorNombre(String nombre);
    List<TipoClaseVehiculoDto> obtenerTipoClaseVehiculos();
    TipoClaseVehiculoDto crearTipoClaseVehiculo(TipoClaseVehiculoDto tipoClaseVehiculoDto);
    TipoClaseVehiculoDto actualizarTipoClaseVehiculo(TipoClaseVehiculoDto tipoClaseVehiculoDto);
    TipoClaseVehiculoDto eliminarTipoClaseVehiculo(String uuid);
}
