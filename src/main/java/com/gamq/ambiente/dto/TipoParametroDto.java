package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.DetalleInspeccion;
import com.gamq.ambiente.model.LimiteEmision;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
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
public class TipoParametroDto {
    private String uuid;
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String descripcion;
    private String unidad;
    private boolean activo;
    private boolean estado;

    private List<LimiteEmisionDto> limiteEmisionDtoList = new ArrayList<LimiteEmisionDto>();
    private List<DetalleInspeccionDto> detalleInspeccionDtoList = new ArrayList<DetalleInspeccionDto>();

}
