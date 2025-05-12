package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.TipoContribuyente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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
    private String tipoDocumento;
    private String numeroDocumento;
    private Integer expedido;
    private String email;
    private boolean estado;

    private TipoContribuyenteDto tipoContribuyenteDto;
    private List<InspeccionDto> inspeccionDtoList = new ArrayList<InspeccionDto>();
    private List<VehiculoDto> vehiculoDtoList = new ArrayList<VehiculoDto>();
}
