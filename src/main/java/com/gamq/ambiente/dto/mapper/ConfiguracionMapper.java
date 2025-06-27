package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ConfiguracionDto;
import com.gamq.ambiente.model.Configuracion;


public class ConfiguracionMapper {
    public static ConfiguracionDto toConfiguracionDto(Configuracion configuracion){
        return new ConfiguracionDto()
                .setUuid(configuracion.getUuid())
                .setClave(configuracion.getClave())
                .setValor(configuracion.getValor())
                .setUnidad(configuracion.getUnidad())
                .setDescripcion(configuracion.getDescripcion())
                .setFechaInicio(configuracion.getFechaInicio())
                .setFechaFin(configuracion.getFechaFin())
                .setFechaRegistro(configuracion.getFechaRegistro())
                .setResolucionApoyo(configuracion.getResolucionApoyo())
                .setRegistradoPor(configuracion.getRegistradoPor())
                .setUuidUsuario(configuracion.getUuidUsuario())
                .setActivo(configuracion.isActivo())
                .setEstado(configuracion.isEstado());
    }

    public static Configuracion toConfiguracion(ConfiguracionDto configuracionDto){
        return new Configuracion()
                .setUuid(configuracionDto.getUuid())
                .setClave(configuracionDto.getClave())
                .setValor(configuracionDto.getValor())
                .setUnidad(configuracionDto.getUnidad())
                .setDescripcion(configuracionDto.getDescripcion())
                .setFechaInicio(configuracionDto.getFechaInicio())
                .setFechaFin(configuracionDto.getFechaFin())
                .setFechaRegistro(configuracionDto.getFechaRegistro())
                .setResolucionApoyo(configuracionDto.getResolucionApoyo())
                .setRegistradoPor(configuracionDto.getRegistradoPor())
                .setUuidUsuario(configuracionDto.getUuidUsuario())
                .setActivo(configuracionDto.isActivo())
                .setEstado(configuracionDto.isEstado());
    }
}
