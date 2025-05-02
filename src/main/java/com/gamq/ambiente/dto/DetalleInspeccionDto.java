package com.gamq.ambiente.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.TipoParametro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetalleInspeccionDto {
    private String uuid;
    private BigDecimal valor;
    private boolean resultadoParcial;
    private Integer tipoPrueba;
    private Integer nroEjecucion;
    private boolean estado;

    private InspeccionDto inspeccionDto;
    private TipoParametroDto tipoParametroDto;
}
