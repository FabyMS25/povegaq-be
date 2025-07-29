package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.dto.CategoriaAireDto;

import java.util.List;

public interface CategoriaAireService {
    CategoriaAireDto obtenerCategoriaAirePorUuid(String uuid);
    CategoriaAireDto obtenerCategoriaAirePorCategoria(String categoria);
    List<CategoriaAireDto> obtenerCategoriasAire();
    CategoriaAireDto crearCategoriaAire(CategoriaAireDto categoriaAireDto);
    CategoriaAireDto actualizarCategoriaAire(CategoriaAireDto categoriaAireDto);
    CategoriaAireDto eliminarCategoriaAire(String uuid);
    List<CategoriaAireDto> obtenerCategoriasAireActivas();
    CategoriaAireDto actualizarCategoriaAireActivo(String uuidCategoriaAire, boolean nuevoActivo);
}
