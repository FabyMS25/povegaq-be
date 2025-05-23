package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Infraccion;
import com.gamq.ambiente.model.Inspeccion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "El número de notificación es obligatorio")
    private String numeroNotificacion; // numero rojo de la boleta
    private TipoNotificacion typeNotificacion;   //      REINSPECCION PENDIENTE, INFRACCION, RECORDATORIO
    private Date fechaAsistencia;
    @NotNull(message = "La hora de notificación es obligatoria")
    private LocalTime horaAsistencia;
    private String observacion;
    private Date fechaNotificacion;  // fecha en la que envia la notificacion
    @NotBlank(message = "El nombre del notificador es obligatorio")
    private String nombreNotificador;
    @NotBlank(message = "El UUID del usuario es obligatorio")
    private String uuidUsuario;
    private EstadoNotificacion statusNotificacion;
    @NotNull(message = "La actividad es obligatorio")
    private String actividad;
    @NotNull(message = "La actividad es obligatorio")
    private String direccion;
    private int numeroIntento;
    private String sancion;
    @NotNull(message = "Si es Denuncia es true caso contrario es una Observacion")
    private boolean esDenuncia;

    private boolean estado;

    private InspeccionDto inspeccionDto;
    private List<InfraccionDto> infraccionDtoList = new ArrayList<InfraccionDto>();
}
