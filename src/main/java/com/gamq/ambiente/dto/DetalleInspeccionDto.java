package com.gamq.ambiente.dto;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.gamq.ambiente.enumeration.TipoCombustible;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.TipoParametro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "El valor no puede ser nulo")
    private BigDecimal valor;
    private boolean resultadoParcial;
    @NotNull(message = "El tipo de prueba es obligatorio")
    @Min(value = 1, message = "El tipo de prueba debe ser válido (1 = móvil, 2 = estática)")
    private Integer tipoPrueba;
    @NotNull(message = "El número de ejecución es obligatorio")
    @Min(value = 1, message = "El número de ejecución debe ser mayor o igual a 1")
    private Integer nroEjecucion;
    private BigDecimal limitePermisible;
    private boolean estado;

    @NotNull(message = "La inspección es obligatoria")
    private InspeccionDto inspeccionDto;
    @NotNull(message = "El tipo de parámetro es obligatorio")
    private TipoParametroDto tipoParametroDto;
    @NotNull(message = "El tipo de combustible es obligatorio")
    private TipoCombustibleDto tipoCombustibleDto;
}
