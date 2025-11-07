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
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaAireDto {
    private String uuid;
    @NotNull(message = "El valor mínimo es obligatorio")
    private Integer valorMinimo;
    @NotNull(message = "El valor máximo es obligatorio")
    private Integer valorMaximo;
    @NotBlank(message = "La categoría es obligatoria")
    @Size(max = 20, message = "La categoría no puede superar 20 caracteres")
    private String categoria;
    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;
    @NotBlank(message = "La categoría es obligatoria")
    private String color;
    private String norma;
    @NotBlank(message = "La recomendacion es obligatoria")
    private String recomendacion;
    private boolean activo;
    private boolean estado;
}
