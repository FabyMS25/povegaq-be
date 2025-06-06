package com.gamq.ambiente.dto;

import com.gamq.ambiente.enumeration.TipoNotificacion;

public interface NotificacionIntentoView {
    String getUuidVehiculo();
    Integer getIntentosValidos();
    Boolean getPuedeEmitirNuevaNotificacion();
    TipoNotificacion getProximoTipoNotificacion();
}
