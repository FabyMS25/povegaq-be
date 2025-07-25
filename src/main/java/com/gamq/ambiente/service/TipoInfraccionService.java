package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.TipoInfraccionDto;
import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.model.TipoContribuyente;

import java.util.List;

public interface TipoInfraccionService {
    TipoInfraccionDto obtenerTipoInfraccionPorUuid(String uuid);
    List<TipoInfraccionDto> obtenerTipoInfraccionPorGrado(GradoInfraccion grado);
    TipoInfraccionDto obtenerTipoInfraccionPorArticuloYGradoYTipoContribuyente(String articulo, GradoInfraccion gradoInfraccion, TipoContribuyente tipoContribuyente);
    List<TipoInfraccionDto> obtenerTipoInfracciones();
    TipoInfraccionDto crearTipoInfraccion(TipoInfraccionDto tipoInfraccionDto);
    TipoInfraccionDto actualizarTipoInfraccion(TipoInfraccionDto tipoInfraccionDto);
    TipoInfraccionDto eliminarTipoInfraccion(String uuid);
    List<TipoInfraccionDto> obtenerTipoInfraccionNoAutomativoPorUuidTipoContribuyente(String uuidTipoContribuyente);
}
