package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.ArchivoAdjunto;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Requisito;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
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
public class RequisitoInspeccionDto {
    private String uuid;
    private boolean cumple;
    private Date fechaPresentacion;
    private boolean estado;

    private RequisitoDto requisitoDto;
    private InspeccionDto inspeccionDto;
    private List<ArchivoAdjuntoDto> archivoAdjuntoDtoList = new ArrayList<ArchivoAdjuntoDto>();
}
