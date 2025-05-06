package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.TipoParametro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LimiteEmisionDto {
    private String uuid;
    private String tipoCombustible;
    private String tipoMotor;
    private Integer yearFabricacionInicio;
    private Integer yearFabricacionFin;
    private BigDecimal limite;
    private String categoriaVehiculo;
    private String categoria;
    private String peso;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer altitudMinima;
    private Integer altitudMaxima;
    private boolean activo;
    private boolean estado;

    private TipoParametroDto tipoParametroDto;

}
