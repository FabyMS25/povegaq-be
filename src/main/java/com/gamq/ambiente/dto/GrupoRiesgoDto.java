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

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GrupoRiesgoDto {
    private String uuid;
    @NotBlank(message = "El nombre del grupo es obligatorio")
    private String grupo;
    private String recomendacion;
    private boolean estado;

    @NotNull(message = "La categoría de aire es obligatoria")
    private CategoriaAireDto categoriaAireDto;
}
