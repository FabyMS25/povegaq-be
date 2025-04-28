package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.dto.ReglamentoDto;

import java.util.List;

public interface ReglamentoService {
    ReglamentoDto obtenerReglamentoPorUuid(String uuid);
    ReglamentoDto obtenerReglamentoPorCodigo(String codigo);
    List<ReglamentoDto> obtenerReglamentos();
    ReglamentoDto crearReglamento(ReglamentoDto reglamentoDto);
    ReglamentoDto actualizarReglamento(ReglamentoDto reglamentoDto);
    ReglamentoDto eliminarReglamento(String uuid);
    boolean actualizarReglamentoActivoToInactivo();
}
