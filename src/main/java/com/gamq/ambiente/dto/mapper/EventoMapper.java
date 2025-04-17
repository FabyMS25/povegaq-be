package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.dto.EventoDto;
import com.gamq.ambiente.model.Actividad;
import com.gamq.ambiente.model.Evento;

public class EventoMapper {
    public static EventoDto toEventoDto(Evento evento){
        return new EventoDto()
                .setUuid(evento.getUuid())
                .setInstitucion(evento.getInstitucion())
                .setFechaInicio(evento.getFechaInicio())
                .setFechaFin(evento.getFechaFin())
                .setEstado(evento.isEstado())
                .setActividadDto(evento.getActividad() == null? null: new ActividadDto()
                        .setUuid(evento.getActividad().getUuid())
                        .setTipoActividad(evento.getActividad().getTipoActividad())
                        .setFechaInicio(evento.getActividad().getFechaInicio())
                        .setFechaFin(evento.getActividad().getFechaFin())
                        .setActivo(evento.getActividad().isActivo())
                        .setEstado(evento.getActividad().isEstado())
                );

    }

    public static Evento toEvento(EventoDto eventoDto){
        return new Evento()
                .setUuid(eventoDto.getUuid())
                .setInstitucion(eventoDto.getInstitucion())
                .setFechaInicio(eventoDto.getFechaInicio())
                .setFechaFin(eventoDto.getFechaFin())
                .setEstado(eventoDto.isEstado())
                .setActividad(eventoDto.getActividadDto()== null? null: new Actividad()
                        .setUuid(eventoDto.getActividadDto().getUuid())
                        .setTipoActividad(eventoDto.getActividadDto().getTipoActividad())
                        .setFechaInicio(eventoDto.getActividadDto().getFechaInicio())
                        .setFechaFin(eventoDto.getActividadDto().getFechaFin())
                        .setActivo(eventoDto.getActividadDto().isActivo())
                        .setEstado(eventoDto.getActividadDto().isEstado())
                );
    }
}
