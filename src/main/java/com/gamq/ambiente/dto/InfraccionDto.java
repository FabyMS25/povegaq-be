package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;
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
    @NotNull
    @NotBlank(message = "El motivo es requerido")
    @Size(max = 250, message = "El motivo no puede exceder los 250 caracteres")
    @Column(name = "motivo", nullable = false, length = 250)
    private String motivo;
    @NotBlank(message = "El nombre notificador es requerido")
    @Size(max = 100, message = "El nombre notificador no puede exceder los 100 caracteres")
    private String nombreRegistrador;
    @NotBlank(message = "El uuid del usuario es requerido")
    @Size(max = 64, message = "El uuidUsuario no puede exceder los 64 caracteres")
    private String uuidUsuario;
    //2025
    //@NotNull
    //private String uuidVehiculo;
    private boolean estado;

    //@Valid
    @NotNull
    private TipoInfraccionDto tipoInfraccionDto;
    @NotNull
    private VehiculoDto vehiculoDto;

    // @Valid
   // @NotNull
    private InspeccionDto inspeccionDto;
    private NotificacionDto notificacionDto;
}
