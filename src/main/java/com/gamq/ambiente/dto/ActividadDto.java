package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Inspeccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class ActividadDto {
    private String uuid;
    @NotBlank(message = "El tipo de actividad es obligatorio")
    @Size(max = 50, message = "El tipo de actividad no puede superar los 50 caracteres")
    private String tipoActividad;
    @NotNull(message = "La fecha de inicio es obligatoria")
    private Date fechaInicio;
    @NotNull(message = "La fecha de fin es obligatoria")
    private Date fechaFin;
    private boolean activo;
    private boolean estado;

    private List<InspeccionDto> inspeccionDtoList = new ArrayList<InspeccionDto>();
}
