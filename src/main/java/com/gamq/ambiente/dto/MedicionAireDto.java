package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Estacion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MedicionAireDto {
    private String uuid;
    private Date fecha;
    @NotBlank(message = "El mes es obligatorio")
    @Size(max = 100, message = "El mes no puede exceder los 100 caracteres")
    private String mes;
    @NotNull(message = "El dia es obligatorio")
    private Integer dia;
    @NotNull(message = "El valor es requerido")
    @DecimalMin(value = "0.0000", inclusive = true, message = "El valor no puede ser negativo")
    private BigDecimal valor;
    private boolean estado;

    @NotNull(message = "La estacion es obligatorio.")
    private EstacionDto estacionDto;
    @NotNull(message = "El contaminante es obligatorio.")
    private ContaminanteDto contaminanteDto;
}
