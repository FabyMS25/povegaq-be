package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.LimiteEmisionDto;

import java.util.List;

public interface LimiteEmisionService {
    LimiteEmisionDto obtenerLimiteEmisionPorUuid(String uuid);
    List<LimiteEmisionDto> obtenerLimiteEmisionPorUuidTipoParametro(String uuidTipoParametro);
    List<LimiteEmisionDto> obtenerLimiteEmisiones();
    LimiteEmisionDto crearLimiteEmision(LimiteEmisionDto LimiteEmisionDto);
    LimiteEmisionDto actualizarLimiteEmision(LimiteEmisionDto LimiteEmisionDto);
    LimiteEmisionDto eliminarLimiteEmision(String uuid);
    LimiteEmisionDto actualizarLimiteEmisionActivo(String uuid, boolean nuevoActivo);
}
