package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.RequisitoDto;

import java.util.List;

public interface RequisitoService {
    RequisitoDto obtenerRequisitoPorUuid(String uuid);
    RequisitoDto obtenerRequisitoPorDescripcion(String descripcion);
    List<RequisitoDto> obtenerRequisitos();
    RequisitoDto crearRequisito(RequisitoDto requisitoDto);
    RequisitoDto actualizarRequisito(RequisitoDto requisitoDto);
    RequisitoDto eliminarRequisito(String uuid);
}
