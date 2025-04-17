package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.TipoContribuyenteDto;

import java.util.List;

public interface TipoContribuyenteService {
    TipoContribuyenteDto obtenerTipoContribuyentePorUuid(String uuid);
    TipoContribuyenteDto obtenerTipoContribuyentePorDescripcion(String descripcion);
    List<TipoContribuyenteDto> obtenerTipoContribuyentes();
    TipoContribuyenteDto crearTipoContribuyente(TipoContribuyenteDto tipoContribuyenteDto);
    TipoContribuyenteDto actualizarTipoContribuyente(TipoContribuyenteDto tipoContribuyenteDto);
    TipoContribuyenteDto eliminarTipoContribuyente(String uuid);
}
