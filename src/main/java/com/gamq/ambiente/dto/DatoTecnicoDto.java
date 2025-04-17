package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Vehiculo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatoTecnicoDto {
    private String uuid;
    private String clase;
    private String radicatoria;
    private String marca;
    private String pais;
    private String traccion; //delantera (FWD), trasera (RWD), total (AWD) o integral (4WD)
    private String modelo;
    private String servicio; //particular, comercial, flota, etc.
    private BigDecimal cilindrada; //2.5
    private String color;
    private BigDecimal capacidadCarga;
    private String tipoVehiculo; //sedán, SUV, camioneta, furgón, camión, etc
    private String tipoCarroceria;
    private Integer yearFabricacion;
    private String procedencia; //pais de fabricacion u origen
    private Integer numeroPuertas;
    private String tipoCombustion; //gasolina , diesel, gas, electrico, hibrido
    private Integer tamanoMotor; //2500
    private String tipoMotor; //4 cilindros,v6
    private BigInteger kilometraje; //150000 km mas grandes en flotas y camiones de largo viaje
    private String emisionStandard; //Euro 6
    private boolean estado;

    private VehiculoDto vehiculoDto;
}
