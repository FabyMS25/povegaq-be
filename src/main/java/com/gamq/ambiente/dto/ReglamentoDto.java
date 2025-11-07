package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.TipoInfraccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReglamentoDto {
    private String uuid;
    @NotBlank(message = "El código es obligatorio")
    @Size(max = 15, message = "El código no puede tener más de 15 caracteres")
    private String codigo;
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 250, message = "La descripción no puede tener más de 250 caracteres")
    private String descripcion;
    @NotNull(message = "La fecha de emisión es obligatoria")
    private Date fechaEmision;
    private boolean activo;
    private boolean estado;

    private List<TipoInfraccionDto> tipoInfraccionDtoList = new ArrayList<TipoInfraccionDto>();
}
