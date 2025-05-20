package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Vehiculo;

import java.util.Date;
import java.util.List;

public interface InspeccionService {
    InspeccionDto obtenerInspeccionPorUuid(String uuid);
    List<InspeccionDto> obtenerInspeccionPorPlaca(String uuid);
    List<InspeccionDto> obtenerInspeccionPorUuidUsuario(String uuidUsuario);
    List<InspeccionDto> obtenerInspecciones();
    InspeccionDto crearInspeccion(InspeccionDto inspeccionDto);
    InspeccionDto actualizarInspeccion(InspeccionDto inspeccionDto);
    InspeccionDto eliminarInspeccion(String uuid);
    List<InspeccionDto> obtenerInspeccionPorUuidActividad(String uuidActividad);
    List<InspeccionDto> obtenerInspeccionPorFechaInspeccion(Date fechaInspeccion);
    int obtenerNumeroIntentoActual(Vehiculo vehiculo);
}
