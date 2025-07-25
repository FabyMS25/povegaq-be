package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.CategoriaAireDto;

import java.util.List;

public interface CategoriaAireService {
    CategoriaAireDto obtenerCategoriaAirePorUuid(String uuid);
    CategoriaAireDto obtenerCategoriaAirePorDescripcion(String descripcion);
    List<CategoriaAireDto> obtenerCategoriaaAire();
    CategoriaAireDto crearCategoriaAire(CategoriaAireDto categoriaAireDto);
    CategoriaAireDto actualizarCategoriaAire(CategoriaAireDto categoriaAireDto);
    CategoriaAireDto eliminarCategoriaAire(String uuid);
}
