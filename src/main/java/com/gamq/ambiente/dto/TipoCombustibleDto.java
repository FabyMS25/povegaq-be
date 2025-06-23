package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.VehiculoTipoCombustible;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoCombustibleDto {
    private String uuid;
    @NotBlank(message = "El nombre de alerta es obligatorio")
    @Size(max = 50, message = "El tipo de motor no puede exceder 50 caracteres")
    private String nombre;
    @NotBlank(message = "La descripcion no puede estar vacío")
    @Size(max = 250, message = "La descripcion no puede exceder 250 caracteres")
    private String descripcion;
    @NotBlank(message = "La descripcion no puede estar vacío")
    @Size(max = 50, message = "El tipo de motor no puede exceder 50 caracteres")
    private String tipoMotor;
    private boolean estado;

    // elimar recursion infinita
   /// private List<VehiculoTipoCombustibleDto> vehiculoTipoCombustibleDtoList = new ArrayList<>();
}
