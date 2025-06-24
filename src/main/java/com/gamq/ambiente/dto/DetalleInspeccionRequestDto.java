package com.gamq.ambiente.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DetalleInspeccionRequestDto {
    private BigDecimal valor;
    private boolean resultadoParcial;
    private Integer tipoPrueba;
    private Integer nroEjecucion;
    private BigDecimal limitePermisible;
    private String uuidTipoParametro;
    private String uuidTipoCombustible;
}
