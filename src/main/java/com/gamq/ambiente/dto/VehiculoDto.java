package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.DatoTecnico;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

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
public class VehiculoDto {
    private String uuid;
    private String placa;
    private String poliza;
    private String vinNumeroIdentificacion;
    private boolean esOficial;
    private Date fechaRegistro;
    private String juridiccionOrigen;
    private boolean estado;

    private DatoTecnicoDto datoTecnicoDto;
    private List<InspeccionDto> inspeccionDtoList = new ArrayList<InspeccionDto>();

}
