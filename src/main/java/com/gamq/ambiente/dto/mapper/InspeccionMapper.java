package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.dto.EventoDto;
import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.model.Actividad;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Vehiculo;

import java.util.stream.Collectors;

public class InspeccionMapper {
    public static InspeccionDto toInspeccionDto(Inspeccion inspeccion){
        return new InspeccionDto()
                .setUuid(inspeccion.getUuid())
                .setResultado(inspeccion.isResultado())
                .setObservacion(inspeccion.getObservacion())
                .setFechaInspeccion(inspeccion.getFechaInspeccion())
                .setLugarInspeccion(inspeccion.getLugarInspeccion())
                .setNombreInspector(inspeccion.getNombreInspector())
                .setUuidUsuario(inspeccion.getUuidUsuario())
                .setEstado(inspeccion.isEstado())
                .setActividadDto(inspeccion.getActividad() == null? null: new ActividadDto()
                        .setUuid(inspeccion.getActividad().getUuid())
                        .setActivo(inspeccion.getActividad().isActivo())
                        .setTipoActividad(inspeccion.getActividad().getTipoActividad())
                        .setFechaInicio(inspeccion.getActividad().getFechaInicio())
                        .setFechaFin(inspeccion.getActividad().getFechaFin())
                        .setEstado(inspeccion.getActividad().isEstado())
                )
                .setVehiculoDto(inspeccion.getVehiculo() == null? null: new VehiculoDto()
                        .setUuid(inspeccion.getVehiculo().getUuid())
                        .setFechaRegistro(inspeccion.getVehiculo().getFechaRegistro())
                        .setPlaca(inspeccion.getVehiculo().getPlaca())
                        .setPoliza(inspeccion.getVehiculo().getPoliza())
                        .setVinNumeroIdentificacion(inspeccion.getVehiculo().getVinNumeroIdentificacion())
                        .setJuridiccionOrigen(inspeccion.getVehiculo().getJuridiccionOrigen())
                        .setEsOficial(inspeccion.getVehiculo().isEsOficial())
                        .setEstado(inspeccion.getVehiculo().isEstado())
                )
                .setEventoDto( inspeccion.getEvento() == null ? null: new EventoDto()
                        .setUuid(inspeccion.getVehiculo().getUuid())
                        .setInstitucion(inspeccion.getEvento().getInstitucion())
                        .setFechaInicio(inspeccion.getEvento().getFechaInicio())
                        .setFechaFin(inspeccion.getEvento().getFechaFin())
                        .setEstado(inspeccion.getEvento().isEstado())
                )
                .setDetalleInspeccionDtoList(inspeccion.getDetalleInspeccionList().stream().map( detalleInspeccion -> {
                        return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccion);
                }).collect(Collectors.toList()));
    }

    public static Inspeccion toInspeccion(InspeccionDto inspeccionDto){
        return new Inspeccion()
                .setUuid(inspeccionDto.getUuid())
                .setResultado(inspeccionDto.isResultado())
                .setObservacion(inspeccionDto.getObservacion())
                .setFechaInspeccion(inspeccionDto.getFechaInspeccion())
                .setLugarInspeccion(inspeccionDto.getLugarInspeccion())
                .setNombreInspector(inspeccionDto.getNombreInspector())
                .setUuidUsuario(inspeccionDto.getUuidUsuario())
                .setEstado(inspeccionDto.isEstado())
              /*  .setActividad(inspeccionDto.getActividadDto() == null? null: new Actividad()
                        .setUuid(inspeccionDto.getActividadDto().getUuid())
                        .setActivo(inspeccionDto.getActividadDto().isActivo())
                        .setTipoActividad(inspeccionDto.getActividadDto().getTipoActividad())
                        .setFechaInicio(inspeccionDto.getActividadDto().getFechaInicio())
                        .setFechaFin(inspeccionDto.getActividadDto().getFechaFin())
                        .setEstado(inspeccionDto.getActividadDto().isEstado())
                )

                .setVehiculo(inspeccionDto.getVehiculoDto() == null? null: new Vehiculo()
                        .setUuid(inspeccionDto.getVehiculoDto().getUuid())
                        .setFechaRegistro(inspeccionDto.getVehiculoDto().getFechaRegistro())
                        .setPlaca(inspeccionDto.getVehiculoDto().getPlaca())
                        .setPoliza(inspeccionDto.getVehiculoDto().getPoliza())
                        .setVinNumeroIdentificacion(inspeccionDto.getVehiculoDto().getVinNumeroIdentificacion())
                        .setJuridiccionOrigen(inspeccionDto.getVehiculoDto().getJuridiccionOrigen())
                        .setEsOficial(inspeccionDto.getVehiculoDto().isEsOficial())
                        .setEstado(inspeccionDto.getVehiculoDto().isEstado())
                )*/
                ;
    }
}
