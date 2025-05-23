package com.gamq.ambiente.service;
import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.NotificacionIntentoDto;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Inspeccion;

import java.util.List;

public interface NotificacionService {
    NotificacionDto obtenerNotificacionPorUuid(String uuid);
    boolean esPosibleNotificar(String uuidInspeccion);
    int numeroIntentoNotificacion(String uuidInspeccion);
    List<NotificacionDto> obtenerNotificaciones();
    List<NotificacionDto> obtenerPorTipoNotificacion(TipoNotificacion typeNotificacion);
    NotificacionDto crearNotificacion(NotificacionDto notificacionDto);
    NotificacionDto generarNotificacionVistaPrevia(String uuidInpeccion);
    NotificacionDto actualizarNotificacion(NotificacionDto notificacionDto);
    NotificacionDto actualizarTipoNotificacion(String uuidNotificacion, TipoNotificacion nuevoTipoNotificacion);
    NotificacionDto actualizarEstadoNotificacion(String uuidNotificacion, EstadoNotificacion nuevoEstadoNotificacion);
    NotificacionDto eliminarNotificacion(String uuid);
    NotificacionIntentoDto ObtenerNotificacionIntentoPorInspeccion(String uuidInspeccion);
    TipoNotificacion determinarTipoNotificacion(Inspeccion inspeccion);
}
