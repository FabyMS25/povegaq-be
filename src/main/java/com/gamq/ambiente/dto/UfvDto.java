package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UfvDto {
    private String uuid;
    @NotNull(message = "La fecha es obligatoria")
    private Date fecha;
    @NotNull(message = "El valor es obligatorio")
    @Digits(integer = 12, fraction = 5, message = "El valor debe ser un número válido con hasta 12 enteros y 5 decimales")
    private BigDecimal valor;
    private boolean estado;
}
