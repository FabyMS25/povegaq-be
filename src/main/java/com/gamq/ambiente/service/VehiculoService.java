package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.VehiculoDto;

import java.util.List;

public interface VehiculoService {
    VehiculoDto obtenerVehiculoPorUuid(String uuid);
    VehiculoDto obtenerVehiculoPorPlaca(String placa);
    VehiculoDto obtenerVehiculoPorPoliza(String poliza);
    VehiculoDto obtenerVehiculoPorVinNumeroIdentificacion(String vinNumeroIdentificacion);
    VehiculoDto obtenerVehiculoPorPinNumeroIdentificacion(String pinNumeroIdentificacion);
    VehiculoDto obtenerVehiculoPorCopo(String copo);
    VehiculoDto obtenerVehiculoPorPlacaAnterior(String copo);

    List<VehiculoDto> obtenerVehiculos();
    VehiculoDto crearVehiculo(VehiculoDto vehiculoDto);
    VehiculoDto actualizarVehiculo(VehiculoDto vehiculoDto);
    VehiculoDto eliminarVehiculo(String uuid);
}
