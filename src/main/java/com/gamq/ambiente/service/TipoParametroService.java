package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.TipoParametroDto;

import java.util.List;

public interface TipoParametroService {
    TipoParametroDto obtenerTipoParametroPorUuid(String uuid);
    TipoParametroDto obtenerTipoParametroPorNombre(String nombre);
    List<TipoParametroDto> obtenerTipoParametros();
    TipoParametroDto crearTipoParametro(TipoParametroDto tipoParametroDto);
    TipoParametroDto actualizarTipoParametro(TipoParametroDto tipoParametroDto);
    TipoParametroDto eliminarTipoParametro(String uuid);
    TipoParametroDto actualizarTipoParametroActivo(String uuid, boolean activo);
}
