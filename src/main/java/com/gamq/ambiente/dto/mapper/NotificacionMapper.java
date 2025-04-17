package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.model.Notificacion;

public class NotificacionMapper {
    public static NotificacionDto toNotificacionDto(Notificacion notificacion){
        return new NotificacionDto()
                .setUuid(notificacion.getUuid())
                .setTipoNotificacion(notificacion.getTipoNotificacion())
                .setFechaNotificacion(notificacion.getFechaNotificacion())
                .setNumeroNotificacion(notificacion.getNumeroNotificacion())
                .setObservacion(notificacion.getObservacion())
                .setFechaLimite(notificacion.getFechaLimite())
                .setFechaAsistencia(notificacion.getFechaAsistencia())
                .setHoraAsistencia(notificacion.getHoraAsistencia())
                .setNombreNotificador(notificacion.getNombreNotificador())
                .setUuidUsuario(notificacion.getUuidUsuario())
                .setRecordatorio(notificacion.isRecordatorio())
                .setVencido(notificacion.isVencido())
                .setStatusNotificacion(notificacion.getStatusNotificacion())
                .setEstado(notificacion.isEstado());
    }

    public static Notificacion toNotificacion( NotificacionDto notificacionDto){
        return new Notificacion()
                .setUuid(notificacionDto.getUuid())
                .setTipoNotificacion(notificacionDto.getTipoNotificacion())
                .setFechaNotificacion(notificacionDto.getFechaNotificacion())
                .setNumeroNotificacion(notificacionDto.getNumeroNotificacion())
                .setObservacion(notificacionDto.getObservacion())
                .setFechaLimite(notificacionDto.getFechaLimite())
                .setFechaAsistencia(notificacionDto.getFechaAsistencia())
                .setHoraAsistencia(notificacionDto.getHoraAsistencia())
                .setNombreNotificador(notificacionDto.getNombreNotificador())
                .setUuidUsuario(notificacionDto.getUuidUsuario())
                .setRecordatorio(notificacionDto.isRecordatorio())
                .setVencido(notificacionDto.isVencido())
                .setStatusNotificacion(notificacionDto.getStatusNotificacion())
                .setEstado(notificacionDto.isEstado());
    }
}
