package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfraccionDto {
    private String uuid;
    private Date fechaInfraccion;
    private BigDecimal montoTotal;
    private String statusInfraccion;
    private boolean estadoPago;
    private Date fechaPago;
    private String numeroTasa;
    private boolean estado;

    private TipoInfraccionDto tipoInfraccionDto;
    private InspeccionDto inspeccionDto;
    private NotificacionDto notificacionDto;
}
