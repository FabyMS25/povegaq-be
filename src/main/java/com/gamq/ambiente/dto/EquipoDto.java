package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Equipo;
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
public class EquipoDto {
    private String uuid;
    @NotNull
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 250, message = "El nombre no puede exceder los 200 caracteres")
    private String nombre;
    @Size(max = 250, message = "El nombre no puede exceder los 100 caracteres")
    private String version;
    private boolean estado;
}
