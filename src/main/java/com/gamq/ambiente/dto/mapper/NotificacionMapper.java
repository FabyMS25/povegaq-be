package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.InspeccionDto;
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
                .setFechaAsistencia(notificacion.getFechaAsistencia())
                .setHoraAsistencia(notificacion.getHoraAsistencia())
                .setNombreNotificador(notificacion.getNombreNotificador())
                .setUuidUsuario(notificacion.getUuidUsuario())
                .setRecordatorio(notificacion.isRecordatorio())
                .setActividad(notificacion.getActividad())
                .setDireccion(notificacion.getDireccion())
                .setStatusNotificacion(notificacion.getStatusNotificacion())
                .setEstado(notificacion.isEstado())
                .setInspeccionDto(notificacion.getInspeccion()== null? null : new InspeccionDto()
                        .setUuid(notificacion.getInspeccion().getUuid())
                        .setResultado(notificacion.getInspeccion().isResultado())
                        .setObservacion(notificacion.getInspeccion().getObservacion())
                        .setFechaInspeccion(notificacion.getInspeccion().getFechaInspeccion())
                        .setLugarInspeccion(notificacion.getInspeccion().getLugarInspeccion())
                        .setNombreInspector(notificacion.getInspeccion().getNombreInspector())
                        .setUuidUsuario(notificacion.getInspeccion().getUuidUsuario())
                        .setAltitud(notificacion.getInspeccion().getAltitud())
                        .setEquipo(notificacion.getInspeccion().getEquipo())
                        .setExamenVisualConforme(notificacion.getInspeccion().isExamenVisualConforme())
                        .setGasesEscapeConforme(notificacion.getInspeccion().isGasesEscapeConforme())
                        .setFechaProximaInspeccion(notificacion.getInspeccion().getFechaProximaInspeccion())
                        .setEstado(notificacion.getInspeccion().isEstado())
                )
                ;
    }

    public static Notificacion toNotificacion( NotificacionDto notificacionDto){
        return new Notificacion()
                .setUuid(notificacionDto.getUuid())
                .setTipoNotificacion(notificacionDto.getTipoNotificacion())
                .setFechaNotificacion(notificacionDto.getFechaNotificacion())
                .setNumeroNotificacion(notificacionDto.getNumeroNotificacion())
                .setObservacion(notificacionDto.getObservacion())
                .setFechaAsistencia(notificacionDto.getFechaAsistencia())
                .setHoraAsistencia(notificacionDto.getHoraAsistencia())
                .setNombreNotificador(notificacionDto.getNombreNotificador())
                .setUuidUsuario(notificacionDto.getUuidUsuario())
                .setRecordatorio(notificacionDto.isRecordatorio())
                .setActividad(notificacionDto.getActividad())
                .setDireccion(notificacionDto.getDireccion())
                .setStatusNotificacion(notificacionDto.getStatusNotificacion())
                .setEstado(notificacionDto.isEstado());
    }
}
