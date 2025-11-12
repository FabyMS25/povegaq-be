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
public class TipoParametroDto {
    private String uuid;
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;
    @Size(max = 250, message = "La descripcion no puede exceder los 250 caracteres")
    private String descripcion;
    @Size(max = 50, message = "La unidad no puede exceder los 50 caracteres")
    private String unidad;
    private boolean activo;
    private boolean estado;

    private List<LimiteEmisionDto> limiteEmisionDtoList = new ArrayList<LimiteEmisionDto>();
    private List<DetalleInspeccionDto> detalleInspeccionDtoList = new ArrayList<DetalleInspeccionDto>();

}
