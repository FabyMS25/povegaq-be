package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ConductorDto;
import com.gamq.ambiente.dto.FotoVehiculoDto;

import java.util.List;

public interface ConductorService {
    ConductorDto obtenerConductorPorUuid(String uuid);
    ConductorDto obtenerConductorPorNumeroDocumento(String numeroDocumento);
    List<ConductorDto> obtenerConductores();
    ConductorDto crearConductor(ConductorDto conductorDto);
    ConductorDto actualizarConductor(ConductorDto conductorDto);
    ConductorDto eliminarConductor(String uuid);
}
