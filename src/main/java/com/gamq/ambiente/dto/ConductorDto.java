package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Inspeccion;
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
public class ConductorDto {
    private String uuid;
    private String nombreCompleto;
    private String tipoContribuyente; //NATURAL, JURIDICO
    private String codigoContribuyente;
    private String tipoDocumento;
    private String numeroDocumento;
    private Integer expedido;
    private boolean estado;

    private InspeccionDto inspeccionDto;
}
