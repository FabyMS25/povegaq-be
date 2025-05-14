package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.VehiculoConductorInspeccionDto;

import java.util.List;

public interface VehiculoConductorInspeccionService {
    VehiculoConductorInspeccionDto obtenerVehiculoConductorInspeccionPorUuid(String uuid);
    VehiculoConductorInspeccionDto obtenerVehiculoConductorInspeccionPorUuidInspeccion(String uuidInspeccion);
    List<VehiculoConductorInspeccionDto> obtenerVehiculoConductorInspecciones();
    VehiculoConductorInspeccionDto crearVehiculoConductorInspeccion(VehiculoConductorInspeccionDto vehiculoConductorInspeccionDto);
    VehiculoConductorInspeccionDto actualizarVehiculoConductorInspeccion(VehiculoConductorInspeccionDto vehiculoConductorInspeccionDto);
    VehiculoConductorInspeccionDto eliminarVehiculoConductorInspeccion(String uuid);
}
