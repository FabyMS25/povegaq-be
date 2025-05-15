package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.RequisitoInspeccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequisitoDto {
    private String uuid;
    @NotEmpty(message = "La descripción no puede ser vacio")
    @Size(max = 200, message = "La descripción no puede exceder los 200 caracteres")
    private String descripcion;
    @NotNull(message = "El campo obligatorio no puede ser vacio")
    @Column(name = "obligatorio", nullable = false)
    private Boolean obligatorio;
    private boolean estado;

    private List<RequisitoInspeccionDto> requisitoInspeccionDtoList = new ArrayList<RequisitoInspeccionDto>();
}
