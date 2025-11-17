package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.PropietarioDto;

import java.util.List;

public interface PropietarioService {
    PropietarioDto obtenerPropietarioPorUuid(String uuid);
    PropietarioDto obtenerPropietarioPorNumeroDocumento(String numeroDocumento);
    List<PropietarioDto> obtenerPropietarios();
    PropietarioDto crearPropietario(PropietarioDto propietarioDto);
    PropietarioDto actualizarPropietario(PropietarioDto propietarioDto);
    boolean puedeQuitarVehiculo(String uuidPropietario, String uuidVehiculo);
    PropietarioDto eliminarPropietario(String uuid);
}
