package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.MedicionAire;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
public class EstacionDto {
    private String uuid;
    @NotEmpty(message = "El nombre no puede ser vacio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;
    @NotEmpty(message = "El tipo no puede ser vacio")
    @Size(max = 50, message = "El tipo no puede exceder los 50 caracteres")
    private String tipo; // Ej: "Fija", "Móvil", "Automática"
    @NotEmpty(message = "La ubicacion no puede ser vacio")
    @Size(max = 250, message = "La ubicacion no puede exceder los 250 caracteres")
    private String ubicacion;
    @Size(max = 250, message = "La descripcion no puede exceder los 250 caracteres")
    private String descripcion;
    private boolean estado;

    private List<MedicionAireDto> medicionAireDtoList = new ArrayList<MedicionAireDto>();
}
