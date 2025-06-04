package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.model.TipoContribuyente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
public class TipoInfraccionDto {
    private String uuid;
    @NotNull(message = "El grado de infracción es obligatorio")
    private GradoInfraccion grado;
    @NotNull(message = "El valor UFV es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor UFV debe ser mayor que cero")
    private BigDecimal valorUFV;
    private boolean estado;

    @NotNull(message = "El uuid del tipo de contribuyente es obligatorio")
    private TipoContribuyenteDto tipoContribuyenteDto;
    @NotNull(message = "El uuid del reglamento es obligatorio")
    private ReglamentoDto reglamentoDto;
    private List<InfraccionDto> infraccionDtoList = new ArrayList<InfraccionDto>();
}
