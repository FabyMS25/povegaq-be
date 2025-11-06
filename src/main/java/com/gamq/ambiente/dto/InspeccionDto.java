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
import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull(message = "La fecha de inspección es obligatoria.")
    private Date fechaInspeccion;
    private boolean resultado;
    @Size(max = 250, message = "La observación no puede superar los 250 caracteres.")
    private String observacion;
    @Size(max = 250, message = "El lugar de inspección no puede superar los 250 caracteres.")
    private String lugarInspeccion;
    @NotBlank(message = "El nombre del inspector es obligatorio.")
    @Size(max = 100, message = "El nombre del inspector no puede superar los 100 caracteres.")
    private String nombreInspector;
    private String uuidUsuario;
    private Integer altitud;
    private boolean examenVisualConforme;
    private boolean gasesEscapeConforme;
    private Date fechaProximaInspeccion;

    private boolean estado;

    @NotNull(message = "El vehiculo es obligatorio")
    private VehiculoDto vehiculoDto;
    @NotNull(message = "La Actividad es obligatorio")
    private ActividadDto actividadDto;
    @NotNull(message = "El Equipo es obligatorio")
    private EquipoDto equipoDto;
    private ConductorDto conductorDto;
    private EventoDto eventoDto;

    private List<CertificadoDto> certificadoDtoList = new ArrayList<>();
    private List<NotificacionDto> notificacionDtoList = new ArrayList<>();
    private List<DetalleInspeccionDto> detalleInspeccionDtoList = new ArrayList<>();
    private List<InfraccionDto> infraccionDtoList = new ArrayList<>();
    private List<RequisitoInspeccionDto> requisitoInspeccionDtoList = new ArrayList<>();

    @AssertTrue(message = "La fecha próxima de inspección debe ser mayor que la fecha de inspección.")
    public boolean isFechasValidas() {
        if (fechaInspeccion == null || fechaProximaInspeccion == null) {
            // Si alguna es nula, no aplica esta validación (permitido)
            return true;
        }
        return fechaProximaInspeccion.after(fechaInspeccion);
    }
}
