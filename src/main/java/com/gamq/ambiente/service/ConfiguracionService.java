package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ConfiguracionDto;

import java.util.Date;
import java.util.List;

public interface ConfiguracionService {
    ConfiguracionDto obtenerConfiguracionPorUuid(String uuid);
    ConfiguracionDto obtenerConfiguracionPorClave(String Clave);
    List<ConfiguracionDto> obtenerConfiguraciones();
    ConfiguracionDto crearConfiguracion(ConfiguracionDto configuracionDto);
    ConfiguracionDto actualizarConfiguracion(ConfiguracionDto configuracionDto);
    ConfiguracionDto eliminarConfiguracion(String uuid);
    List<ConfiguracionDto> obtenerConfiguracionesActivas();
    List<ConfiguracionDto> obtenerConfiguracionesPorAnio(Integer year);
    ConfiguracionDto actualizarConfiguracionActivo(String uuid, boolean nuevoActivo);
    List<ConfiguracionDto> obtenerConfiguracionesEntreFechas(Date rangoInicio, Date rangoFin);
}
