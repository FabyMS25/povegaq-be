package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ContaminanteDto;

import java.util.List;

public interface ContaminanteService {
    ContaminanteDto obtenerContaminantePorUuid(String uuid);
    ContaminanteDto obtenerContaminantePorNombre(String nombre);
    List<ContaminanteDto> obtenerContaminantes();
    ContaminanteDto crearContaminante(ContaminanteDto contaminanteDto);
    ContaminanteDto actualizarContaminante(ContaminanteDto contaminanteDto);
    ContaminanteDto eliminarContaminante(String uuid);
}
