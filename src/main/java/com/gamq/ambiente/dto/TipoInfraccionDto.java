package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.gamq.ambiente.model.TipoContribuyente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
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
public class TipoInfraccionDto {
    private String uuid;
    private String grado;
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal valorUFV;
    private boolean estado;

    private TipoContribuyenteDto tipoContribuyenteDto;
    private List<InfraccionDto> infraccionDtoList = new ArrayList<InfraccionDto>();
}
