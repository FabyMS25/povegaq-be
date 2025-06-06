package com.gamq.ambiente.scheduler;

import com.gamq.ambiente.validators.NotificacionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificacionScheduledTask {
    @Autowired
    private NotificacionValidator notificacionValidator;

    @Scheduled(cron = "0 1 1 * * ?") // Todos los días a medianoche
    public void procesarVencimientos() {
        notificacionValidator.verificarReinspeccionesVencidas();
        notificacionValidator.verificarInfraccionesVencidas();
    }
}
