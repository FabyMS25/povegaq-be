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
import javax.validation.constraints.NotBlank;
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
public class LimiteEmisionDto {
    private String uuid;

    @NotBlank(message = "El tipo de motor es requerido")
    private String tipoMotor;
    @NotBlank(message = "El tipo de motor es requerido")
    private String tipoCombustible;
    private Integer cilindradaMinimo;
    private Integer cilindradaMaximo;
    private String categoriaVehiculo; //liviano, mediano y pesado
    private String categoria;
    private Integer yearFabricacionInicio;
    private Integer yearFabricacionFin;
    @NotNull(message = "El límite de emisión es requerido")
    private BigDecimal limite;

    private Integer pesoBrutoMinimo; // en kilogramos
    private Integer pesoBrutoMaximo; // en kilogramos
    private Integer capacidadCargaMinimo; // en kilogramos
    private Integer capacidadCargaMaximo; // en kilogramos

    private Date fechaInicio;
    private Date fechaFin;
    private Integer altitudMinima;
    private Integer altitudMaxima;
    private String claseVehiculo;  //automoviles, ciclomotores
    private String tiempoMotor;//motor de 4 tiempos , motor de 2 tiempos

    private String cicloPrueba;//"ASM2525", "IM240", "WLTC", "EURO 3 CI ETC", "ESC ETC"
    private String normativa;//"NB-98007", "EURO 4 CI", "EPA 1996"

    private boolean activo;
    private boolean estado;

    @NotNull(message = "El tipo de parámetro es requerido")
    private TipoParametroDto tipoParametroDto;

}
