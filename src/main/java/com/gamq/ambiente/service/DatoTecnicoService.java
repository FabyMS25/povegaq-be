package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.DatoTecnicoDto;

import java.util.List;

public interface DatoTecnicoService {
    DatoTecnicoDto obtenerDatoTecnicoPorUuid(String uuid);
   // DatoTecnicoDto obtenerDatoTecnicoPorUuidVehiculo(String vehiculoUuid);
   // List<DatoTecnicoDto> obtenerDatoTecnicos();
    DatoTecnicoDto crearDatoTecnico(DatoTecnicoDto DatoTecnicoDto);
    DatoTecnicoDto actualizarDatoTecnico(DatoTecnicoDto DatoTecnicoDto);
    DatoTecnicoDto eliminarDatoTecnico(String uuid);
}
