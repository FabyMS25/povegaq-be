package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.ActividadDto;

import java.util.List;

public interface ActividadService {
    ActividadDto obtenerActividadPorUuid(String uuid);
    ActividadDto obtenerActividadPorTipoActividad(String tipoActividad);
    List<ActividadDto> obtenerActividades();
    ActividadDto crearActividad(ActividadDto actividadDto);
    ActividadDto actualizarActividad(ActividadDto actividadDto);
    ActividadDto eliminarActividad(String uuid);
    List<ActividadDto> obtenerActividadesActivas();
    List<ActividadDto> obtenerActividadesPorAnio(Integer year);
    ActividadDto actualizarActividadActivo(String uuid, boolean nuevoActivo);
}
