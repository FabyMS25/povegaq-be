package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Infraccion;
import com.gamq.ambiente.model.Inspeccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalTime;
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
public class NotificacionDto {
    private String uuid;
    private String numeroNotificacion;
    private String tipoNotificacion;
    private Date fechaLimite;
    private boolean vencido;
    private Date fechaAsistencia;
    private LocalTime horaAsistencia;
    private String observacion;
    private String statusNotificacion;
    private boolean recordatorio;
    private Date fechaNotificacion;
    private String nombreNotificador;
    private String uuidUsuario;
    private boolean estado;

    private InspeccionDto inspeccionDto;
    private List<InfraccionDto> infraccionDtoList = new ArrayList<InfraccionDto>();
}
