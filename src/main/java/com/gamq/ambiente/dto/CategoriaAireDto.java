package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaAireDto {
    private String uuid;
    private Integer valorMinimo;
    private Integer valorMaximo;
    private String categoria;
    private String descripcion;
    private String color;
    private String norma;
    private String recomendacion;
    private boolean activo;
    private boolean estado;
}
