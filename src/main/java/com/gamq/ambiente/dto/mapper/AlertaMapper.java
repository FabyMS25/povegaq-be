package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.*;
import com.gamq.ambiente.model.Alerta;

public class AlertaMapper {
    public static AlertaDto toAlertaDto(Alerta alerta) {
        return new AlertaDto()
                .setUuid(alerta.getUuid())
                .setTipo(alerta.getTipo())
                .setFechaAlerta(alerta.getFechaAlerta())
                .setMensaje(alerta.getMensaje())
                .setEsLeido(alerta.isEsLeido())
                .setRolDestinatario(alerta.getRolDestinatario())
                .setUuidDestinatario(alerta.getUuidDestinatario())
                .setEstado(alerta.isEstado())
                .setVehiculoDto(alerta.getVehiculo() == null ? null : new VehiculoDto()
                        .setUuid(alerta.getVehiculo().getUuid())
                        .setEsOficial(alerta.getVehiculo().isEsOficial())
                        .setFechaRegistro(alerta.getVehiculo().getFechaRegistro())
                        .setJurisdiccionOrigen(alerta.getVehiculo().getJurisdiccionOrigen())
                        .setPlaca(alerta.getVehiculo().getPlaca())
                        .setPoliza(alerta.getVehiculo().getPoliza())
                        .setVinNumeroIdentificacion(alerta.getVehiculo().getVinNumeroIdentificacion())
                        .setEsUnidadIndustrial(alerta.getVehiculo().isEsUnidadIndustrial())
                        .setEsMovil(alerta.getVehiculo().getEsMovil())
                        .setPinNumeroIdentificacion(alerta.getVehiculo().getPinNumeroIdentificacion())
                        .setNroCopiasPlaca(alerta.getVehiculo().getNroCopiasPlaca())
                        .setPlacaAnterior(alerta.getVehiculo().getPlacaAnterior())
                        .setChasis(alerta.getVehiculo().getChasis())

                        .setPropietarioDto(alerta.getVehiculo().getPropietario() == null ? null : new PropietarioDto()
                                .setUuid(alerta.getVehiculo().getPropietario().getUuid())
                                .setNombre(alerta.getVehiculo().getPropietario().getNombre())
                                .setPrimerApellido(alerta.getVehiculo().getPropietario().getPrimerApellido())
                                .setSegundoApellido(alerta.getVehiculo().getPropietario().getSegundoApellido())
                                .setApellidoEsposo(alerta.getVehiculo().getPropietario().getApellidoEsposo())
                                .setNumeroDocumento(alerta.getVehiculo().getPropietario().getNumeroDocumento())
                                .setTipoDocumento(alerta.getVehiculo().getPropietario().getTipoDocumento())
                                .setExpedido(alerta.getVehiculo().getPropietario().getExpedido())
                                .setEmail(alerta.getVehiculo().getPropietario().getEmail())
                                .setNroTelefono(alerta.getVehiculo().getPropietario().getNroTelefono())
                                .setEstado(alerta.getVehiculo().getPropietario().isEstado())
                                .setEstadoCivil(alerta.getVehiculo().getPropietario().getEstadoCivil())
                                .setGenero(alerta.getVehiculo().getPropietario().getGenero())
                                .setFechaNacimiento(alerta.getVehiculo().getPropietario().getFechaNacimiento())
                                .setTipoContribuyenteDto(alerta.getVehiculo().getPropietario().getTipoContribuyente() == null ? null : new TipoContribuyenteDto()
                                        .setUuid(alerta.getVehiculo().getPropietario().getTipoContribuyente().getUuid())
                                        .setDescripcion(alerta.getVehiculo().getPropietario().getTipoContribuyente().getDescripcion())
                                        .setEstado(alerta.getVehiculo().getPropietario().getTipoContribuyente().isEstado())
                                        .setCodigo(alerta.getVehiculo().getPropietario().getTipoContribuyente().getCodigo())
                                )
                        )
                ) ;
    }
    public static Alerta toAlerta(AlertaDto alertaDto){
        return new Alerta()
                .setUuid(alertaDto.getUuid())
                .setTipo(alertaDto.getTipo())
                .setFechaAlerta(alertaDto.getFechaAlerta())
                .setMensaje(alertaDto.getMensaje())
                .setEsLeido(alertaDto.isEsLeido())
                .setRolDestinatario(alertaDto.getRolDestinatario())
                .setEstado(alertaDto.isEstado())
                .setUuidDestinatario(alertaDto.getUuidDestinatario());
    }
}
