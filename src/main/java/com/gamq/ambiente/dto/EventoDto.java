package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Actividad;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventoDto {
    private String uuid;
    @Size(max = 250, message = "La institución no puede superar 250 caracteres")
    private String institucion;
    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date fechaInicio;
    @NotNull(message = "La fecha de fin es obligatoria")
    private Date fechaFin;
    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;
    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;
    private Double latitud;
    private Double longitud;
    private String direccion;
    private String descripcion;
    private String distrito;
    private Integer altitud;
    private String titulo;
    private boolean estado;

    private ActividadDto actividadDto;
}
