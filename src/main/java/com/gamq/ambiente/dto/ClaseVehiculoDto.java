package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClaseVehiculoDto {
    private String uuid;
    private String nombre;
    private String descripcion;
    private boolean estado;

    private List<TipoClaseVehiculoDto> tipoClaseVehiculoDtoList = new ArrayList<>();
}
