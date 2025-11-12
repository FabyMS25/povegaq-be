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
import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 250, message = "La direccion no puede superar 250 caracteres")
    private String direccion;
    private String descripcion;
    @Size(max = 15, message = "El distrito no puede superar 15 caracteres")
    private String distrito;
    private Integer altitud;
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 100, message = "El titulo no puede superar 100 caracteres")
    private String titulo;
    private boolean estado;

    @NotNull(message = "La actividad es obligatorio.")
    private ActividadDto actividadDto;
}
