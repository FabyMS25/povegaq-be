package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatoTecnicoDto {
    private String uuid;
   // @NotBlank(message = "La clase no puede ser nula ni vacía")
   // private String clase;
    @NotBlank(message = "La marca no puede ser nula ni vacía")
    private String marca; //no
    @NotBlank(message = "El pais no puede ser nula ni vacía")
    private String pais;  //pais de fabricacion u origen procedencia
    private String traccion; //delantera (FWD), trasera (RWD), total (AWD) o integral (4WD)
    @NotBlank(message = "El modelo no puede ser nula ni vacía")
    private String modelo; //si el year modelo 2000, 1999
    private String servicio; //particular, comercial, flota, etc.
    private BigDecimal cilindrada; //2.5
    @NotBlank(message = "El color no puede ser nula ni vacía")
    private String color;
    private BigDecimal capacidadCarga; //
    private String tipoVehiculo; //sedán, SUV, camioneta, furgón, camión, etc
    private String tipoCarroceria;
    @NotNull(message = "El año de fabricación es obligatorio")
    //@Min(value = 1900, message = "El año de fabricación no puede ser menor a 1900")
    //@ValidYearFabricacion
    //@Max(value = YearNowValidator.CURRENT_YEAR, message = "El año de fabricación no puede ser mayor al año actual")
    private Integer yearFabricacion;
    private Integer numeroPuertas;
    //@NotBlank(message = "El tipo de combustion no puede ser nula ni vacía")
    //private TipoCombustible tipoCombustion; //si gasolina , diesel, gas, electrico, hibrido
    private Integer tamanoMotor; //2500
    @NotBlank(message = "El tipo de motor no puede ser nula ni vacía")
    private String tipoMotor; // si es gasolina chispa 4 cilindros,v6
    private BigInteger kilometraje; //150000 km mas grandes en flotas y camiones de largo viaje
    private String emisionStandard; //Euro 6
    @NotNull(message = "La clasificacion vehicular es requerido")
    private String clasificacion; //m1 y m2  liviano, mediano, pesado
    private Integer numeroAsientos;
    @NotBlank(message = "El tiempo de motor no puede ser nula ni vacía")
    private String tiempoMotor;
    private String categoriaVehiculo; // liviano, mediano, pesado
    private boolean estado;

    private VehiculoDto vehiculoDto;

    private TipoClaseVehiculoDto tipoClaseVehiculoDto;

    // Clase interna para obtener el año actual (puede actualizarse automáticamente en tiempo de compilación)
    public static class YearNowValidator {
        public static final int CURRENT_YEAR = Year.now().getValue();
    }
}
