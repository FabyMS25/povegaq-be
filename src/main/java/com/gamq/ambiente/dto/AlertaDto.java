package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlertaDto {
    private String uuid;
    private String tipo;
    private String mensaje;
    private Date fechaAlerta;
    private String uuidDestinatario;
    private String rolDestinatario;
    private boolean esLeido;
    private boolean estado;

    private NotificacionDto notificacionDto;

    private InfraccionDto infraccionDto;

    private VehiculoDto vehiculoDto;

}
