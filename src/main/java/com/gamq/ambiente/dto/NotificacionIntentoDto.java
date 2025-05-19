package com.gamq.ambiente.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionIntentoDto {
    private String uuidInspeccion;
    private int intentosValidos;
    private boolean puedeEmitirNuevaNotificacion;
    private String mensaje;
}
