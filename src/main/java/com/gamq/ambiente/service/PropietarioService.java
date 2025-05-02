package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.PropietarioDto;

import java.util.List;

public interface PropietarioService {
    PropietarioDto obtenerPropietarioPorUuid(String uuid);
    List<PropietarioDto> obtenerPropietarios();
    PropietarioDto crearPropietario(PropietarioDto propietarioDto);
    PropietarioDto actualizarPropietario(PropietarioDto propietarioDto);
    PropietarioDto eliminarPropietario(String uuid);
}
