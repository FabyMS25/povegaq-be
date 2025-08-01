package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.GrupoRiesgoDto;

import java.util.List;

public interface GrupoRiesgoService {
    GrupoRiesgoDto obtenerGrupoRiesgoPorUuid(String uuid);
    GrupoRiesgoDto obtenerGrupoRiesgoPorGrupo(String grupo);
    List<GrupoRiesgoDto> obtenerGrupoRiesgos();
    List<GrupoRiesgoDto> obtenerGrupoRiesgoPorUuidCategoriaAire(String uuidCategoriaAire);
    GrupoRiesgoDto crearGrupoRiesgo(GrupoRiesgoDto grupoRiesgoDto);
    GrupoRiesgoDto actualizarGrupoRiesgo(GrupoRiesgoDto grupoRiesgoDto);
    GrupoRiesgoDto eliminarGrupoRiesgo(String uuid);
}
