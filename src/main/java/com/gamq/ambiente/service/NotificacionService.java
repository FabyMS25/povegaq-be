package com.gamq.ambiente.service;
import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.NotificacionIntentoDto;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Vehiculo;

import java.util.List;

public interface NotificacionService {
    NotificacionDto obtenerNotificacionPorUuid(String uuid);
    boolean esPosibleNotificar(Vehiculo vehiculo);
   // int numeroIntentoNotificacion(Vehiculo vehiculo);
    List<NotificacionDto> obtenerNotificaciones();
    List<NotificacionDto> obtenerPorTipoNotificacion(TipoNotificacion typeNotificacion);
    NotificacionDto crearNotificacion(NotificacionDto notificacionDto);
    NotificacionDto generarNotificacionVistaPrevia(String uuidInpeccion);
    NotificacionDto actualizarNotificacion(NotificacionDto notificacionDto);
    NotificacionDto actualizarTipoNotificacion(String uuidNotificacion, TipoNotificacion nuevoTipoNotificacion);
    NotificacionDto actualizarEstadoNotificacion(String uuidNotificacion, EstadoNotificacion nuevoEstadoNotificacion);
    NotificacionDto eliminarNotificacion(String uuid);
    List<NotificacionDto> obtenerNotificacionesPorFechaAsistenciaVencida();

   // NotificacionIntentoDto ObtenerNotificacionIntentoPorInspeccion(String uuidInspeccion);
    // TipoNotificacion determinarTipoNotificacion(Inspeccion inspeccion);
}
