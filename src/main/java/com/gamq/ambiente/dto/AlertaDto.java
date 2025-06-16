package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "El tipo de alerta es obligatorio")
    private String tipo;
    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(max = 250, message = "El mensaje no puede exceder 250 caracteres")
    private String mensaje;
    @NotNull(message = "La fecha de alerta es obligatoria")
    private Date fechaAlerta;
    private String uuidDestinatario;
    private String rolDestinatario;
    private boolean esLeido;
    private boolean estado;

    private NotificacionDto notificacionDto;
    private InfraccionDto infraccionDto;
    private VehiculoDto vehiculoDto;
}
