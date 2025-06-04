package com.gamq.ambiente.validators;

import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Notificacion;
import com.gamq.ambiente.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificacionValidator {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public void verificarReinspeccionesVencidas() {
        Date fechaActual = new Date();

        List<Notificacion> vencidas = notificacionRepository.findByTypeNotificacionAndStatusNotificacionInAndFechaAsistenciaBefore(
                TipoNotificacion.REINSPECCION_PENDIENTE,
                List.of(EstadoNotificacion.PENDIENTE, EstadoNotificacion.ENVIADA, EstadoNotificacion.ENTREGADA),
                fechaActual
        );

        for (Notificacion noticion : vencidas) {
            noticion.setStatusNotificacion(EstadoNotificacion.VENCIDA);
            notificacionRepository.save(noticion);
        }
    }
}
