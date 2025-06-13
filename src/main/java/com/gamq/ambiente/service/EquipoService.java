package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.EquipoDto;

import java.util.List;

public interface EquipoService {
    EquipoDto obtenerEquipoPorUuid(String uuid);
    EquipoDto obtenerEquipoPorNombre(String nombre);
    List<EquipoDto> obtenerEquipos();
    EquipoDto crearEquipo(EquipoDto equipoDto);
    EquipoDto actualizarEquipo(EquipoDto equipoDto);
    EquipoDto eliminarEquipo(String uuid);
}
