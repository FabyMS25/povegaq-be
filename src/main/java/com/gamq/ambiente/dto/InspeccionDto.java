package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InspeccionDto {
    private String uuid;
    private Date fechaInspeccion;
    private boolean resultado;
    private String observacion;
    private String lugarInspeccion;
    private String nombreInspector;
    private String uuidUsuario;
    private Integer altitud;
    private String equipo;
    private boolean examenVisualConforme;
    private boolean gasesEscapeConforme;
    private Date fechaProximaInspeccion;

    private boolean estado;

    private VehiculoDto vehiculoDto;
    private ActividadDto actividadDto;
    private PropietarioDto propietarioDto;
    private ConductorDto conductorDto;
    private EventoDto eventoDto;

    private List<CertificadoDto> certificadoDtoList = new ArrayList<>();
    private List<NotificacionDto> notificacionDtoList = new ArrayList<>();
    private List<DetalleInspeccionDto> detalleInspeccionDtoList = new ArrayList<>();
    private List<InfraccionDto> infraccionDtoList = new ArrayList<>();
    private List<RequisitoInspeccionDto> requisitoInspeccionDtoList = new ArrayList<>();
}
