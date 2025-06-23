package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.TipoCombustibleDto;

import java.util.List;

public interface TipoCombustibleService {
    TipoCombustibleDto obtenerTipoCombustiblePorUuid(String uuid);
    TipoCombustibleDto obtenerTipoCombustiblePorNombre(String nombre);
    List<TipoCombustibleDto> obtenerTipoCombustibles();
    TipoCombustibleDto crearTipoCombustible(TipoCombustibleDto tipoCombustibleDto);
    TipoCombustibleDto actualizarTipoCombustible(TipoCombustibleDto tipoCombustibleDto);
    TipoCombustibleDto eliminarTipoCombustible(String uuid);
}
