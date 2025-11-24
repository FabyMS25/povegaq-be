package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
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
    @Size(max = 15, message = "El numero de notificacion no puede exceder los 15 caracteres")
    private String numeroNotificacion;
    @NotNull(message = "El tipo de notificación es requerido")
    private TipoNotificacion typeNotificacion;
    @NotNull(message = "La fecha de asistencia es requerida")
    private Date fechaAsistencia;
    @NotNull(message = "La hora de notificación es obligatoria")
    private LocalTime horaAsistencia;
    private String observacion;
    @NotNull(message = "La fecha de notificación es obligatoria")
    private Date fechaNotificacion;  // fecha en la que envia la notificacion
    @NotBlank(message = "El nombre del notificador es obligatorio")
    @Size(max = 100, message = "El nombre del notificador no puede exceder los 100 caracteres")
    private String nombreNotificador;
    @NotBlank(message = "El UUID del usuario es obligatorio")
    private String uuidUsuario;
    @NotNull(message = "El estado de notificación es requerido")
    private EstadoNotificacion statusNotificacion;
    @NotNull(message = "La actividad es obligatorio")
    @Size(max = 200, message = "La actividad no puede exceder los 200 caracteres")
    private String actividad;
    @NotNull(message = "La actividad es obligatorio")
    @Size(max = 250, message = "La direccion no puede exceder los 250 caracteres")
    private String direccion;
    private int numeroIntento;
    @Size(max = 250, message = "La actividad no puede exceder los 250 caracteres")
    private String sancion;
    @NotNull(message = "Si es Denuncia es true caso contrario es una Observacion")
    private boolean esDenuncia;
    @NotNull(message = "El nombre de la persona notificada es obligatorio")
    @Size(max = 100, message = "El nombre persona notificada no puede exceder los 100 caracteres")
    private String nombrePersonaNotificada;
    @NotNull(message = "La placa es obligatorio")
    private String placa;
    private boolean generadoSistema;
    private boolean estado;

    @NotNull(message = "La inspeccion es obligatorio.")
    private InspeccionDto inspeccionDto;
    private List<InfraccionDto> infraccionDtoList = new ArrayList<InfraccionDto>();
}
