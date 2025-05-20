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

import javax.validation.Valid;
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
    @NotNull(message = "El tipo de notificación es obligatorio")
    private TipoNotificacion typeNotificacion;   //      RESULTADO FALSO,  REINSPECCION PENDIENTE, INFRACCION, RECORDATORIO
    private Date fechaAsistencia;
    private LocalTime horaAsistencia;
    private String observacion;
    @NotNull(message = "La fecha de notificación es obligatoria")
    private Date fechaNotificacion;  // fecha en la que envia la notificacion
    @NotBlank(message = "El nombre del notificador es obligatorio")
    private String nombreNotificador;
    @NotBlank(message = "El UUID del usuario es obligatorio")
    private String uuidUsuario;
    @NotNull(message = "El estado de la notificación es obligatorio")
    private EstadoNotificacion statusNotificacion;
    private String actividad;
    private String direccion;
    private boolean estado;

    private InspeccionDto inspeccionDto;
    private List<InfraccionDto> infraccionDtoList = new ArrayList<InfraccionDto>();
}
