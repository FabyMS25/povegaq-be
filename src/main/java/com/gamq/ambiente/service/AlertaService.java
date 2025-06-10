package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.AlertaDto;

import java.util.Date;
import java.util.List;

public interface AlertaService {
    AlertaDto obtenerAlertaPorUuid(String uuid);
    List<AlertaDto> obtenerAlertas();
    AlertaDto crearAlerta  (AlertaDto eventoDto);
    AlertaDto actualizarAlerta  (AlertaDto eventoDto);
    AlertaDto eliminarAlerta  (String uuid);
    List<AlertaDto> obtenerAlertasPorFechaActual(Date fechaActual);
    List<AlertaDto> obtenerAlertasPorFechaActualAndUuidDestinatario( Date fechaActual, String uuidDestinatario);
}
