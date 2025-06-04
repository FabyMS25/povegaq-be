package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Inspeccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificadoDto {
    private String uuid;
    private String codigo;
    private String qrContent;
    private Date fechaEmision;
    @Future(message = "La fecha de vencimiento debe ser una fecha futura")
    @NotNull(message = "La fecha de vencimiento no puede ser nula")
    private Date fechaVencimiento;
    @NotNull(message = "El campo esValido es obligatorio")
    private boolean esValido;
    private boolean estado;

    @NotNull(message = "El uuid de la inspeccion es obligatorio")
    private InspeccionDto inspeccionDto;
}
