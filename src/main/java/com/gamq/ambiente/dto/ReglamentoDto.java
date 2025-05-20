package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.TipoInfraccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class ReglamentoDto {
    private String uuid;
    private String codigo;
    private String descripcion;
    private Date fechaEmision;
    private boolean activo;
    private boolean estado;

    private List<TipoInfraccionDto> tipoInfraccionDtoList = new ArrayList<TipoInfraccionDto>();
}
