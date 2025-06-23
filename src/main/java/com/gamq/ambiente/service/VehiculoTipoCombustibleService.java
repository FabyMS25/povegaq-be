package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.VehiculoTipoCombustibleDto;
import com.gamq.ambiente.model.Vehiculo;

import java.util.List;

public interface VehiculoTipoCombustibleService {
    void asignarCombustibles(Vehiculo vehiculo, List<VehiculoTipoCombustibleDto> combustibles);
    void eliminarTodosPorVehiculo(String uuidVehiculo);
    List<VehiculoTipoCombustibleDto> obtenerPorVehiculo(String uuidVehiculo);
}
