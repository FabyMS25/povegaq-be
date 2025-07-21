package com.gamq.ambiente.scheduler;

import com.gamq.ambiente.validators.InfraccionValidator;
import org.springframework.scheduling.annotation.Scheduled;

public class InfraccionScheduledTask {
    private final InfraccionValidator infraccionValidator;

    public InfraccionScheduledTask(InfraccionValidator infraccionValidator){
        this.infraccionValidator = infraccionValidator;
    }

    @Scheduled(cron = "0 0 0 * * ?") // diario a medianoche
    public void marcarVencidas() {
        infraccionValidator.marcarInfraccionesVencidas();
    }
}
