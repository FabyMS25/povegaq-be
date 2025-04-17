package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.EventoDto;

import java.util.Date;
import java.util.List;

public interface EventoService {
    EventoDto obtenerEventoPorUuid(String uuid);
    List<EventoDto> obtenerEventosAntesDeFecha(Date fecha);
    List<EventoDto> obtenerEventoPorInstitucion(String institucion);
    List<EventoDto> obtenerEventos();
    EventoDto crearEvento(EventoDto eventoDto);
    EventoDto actualizarEvento(EventoDto eventoDto);
    EventoDto eliminarEvento(String uuid);
    List<EventoDto> obtenerEventosActivas(Date fecha);
    List<EventoDto> obtenerEventosPorAnio(Integer year);
}
