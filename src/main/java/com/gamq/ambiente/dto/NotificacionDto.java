package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
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
    private String numeroNotificacion; // numero rojo de la boleta
    private String tipoNotificacion;   //      RESULTADO FALSO,  REINSPECCION PENDIENTE, INFRACCION, RECORDATORIO
    private Date fechaAsistencia;
    private LocalTime horaAsistencia;
    private String observacion;
    private Date fechaNotificacion;  // fecha en la que envia la notificacion
    private String nombreNotificador;
    private String uuidUsuario;
    private EstadoNotificacion statusNotificacion;
    private boolean recordatorio;

    //private boolean vencido;
    //private Date fechaLimite;

    private boolean estado;

    private InspeccionDto inspeccionDto;
    private List<InfraccionDto> infraccionDtoList = new ArrayList<InfraccionDto>();
}
