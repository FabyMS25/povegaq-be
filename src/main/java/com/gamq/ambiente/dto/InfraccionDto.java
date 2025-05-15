package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
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
    @NotNull
    @PastOrPresent
    private Date fechaInfraccion;
    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal montoTotal;
    @NotNull
    private String statusInfraccion; //pendiente pagado
    private boolean estadoPago;
    private Date fechaPago;
    private String numeroTasa;
    private boolean estado;

    @Valid
    @NotNull
    private TipoInfraccionDto tipoInfraccionDto;
    @Valid
    @NotNull
    private InspeccionDto inspeccionDto;
    private NotificacionDto notificacionDto;
}
