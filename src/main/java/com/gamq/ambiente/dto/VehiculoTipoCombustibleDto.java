package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiculoTipoCombustibleDto {
    private String uuid;
    private Boolean esPrimario;
    private boolean estado;

    @NotNull(message = "El vehículo es obligatoria")
    private VehiculoDto vehiculoDto;
    @NotNull(message = "El tipo combustible es obligatoria")
    private TipoCombustibleDto tipoCombustibleDto;
}
