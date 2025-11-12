package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TipoContribuyenteDto {
    private String uuid;
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 250, message = "La descripcion no puede exceder 250 caracteres")
    private String descripcion;
    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 15, message = "El codigo no puede exceder 15 caracteres")
    private String codigo;
    private boolean estado;
}
