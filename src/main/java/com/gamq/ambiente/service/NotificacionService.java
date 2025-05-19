package com.gamq.ambiente.service;
import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.NotificacionIntentoDto;

import java.util.List;

public interface NotificacionService {
    NotificacionDto obtenerNotificacionPorUuid(String uuid);
    boolean esPosibleNotificar(String uuidInspeccion);
    int numeroIntentoNotificacion(String uuidInspeccion);
    List<NotificacionDto> obtenerNotificaciones();
    NotificacionDto crearNotificacion(NotificacionDto notificacionDto);
    NotificacionDto actualizarNotificacion(NotificacionDto notificacionDto);
    NotificacionDto eliminarNotificacion(String uuid);
    NotificacionIntentoDto ObtenerNotificacionIntentoPorInspeccion(String uuidInspeccion);
}
