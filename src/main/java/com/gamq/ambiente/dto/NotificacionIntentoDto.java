package com.gamq.ambiente.dto;

import com.gamq.ambiente.enumeration.TipoNotificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionIntentoDto {
    private String uuidVehiculo;
    private int intentosValidos;
    private boolean puedeEmitirNuevaNotificacion;
    private TipoNotificacion proximoTipoNotificacion;
}
