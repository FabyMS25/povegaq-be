package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.ClaseVehiculo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoClaseVehiculoDto {
    private String uuid;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String descripcion;
    private boolean estado;

    @NotNull(message = "La clase de vehículo es obligatoria")
    private ClaseVehiculoDto claseVehiculoDto;
}
