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
    private String institucion;
    private Date fechaInicio;
    private Date fechaFin;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double latitud;
    private Double longitud;
    private String direccion;
    private String descripcion;
    private String distrito;
    private Integer altitud;
    private boolean estado;

    private ActividadDto actividadDto;
}
