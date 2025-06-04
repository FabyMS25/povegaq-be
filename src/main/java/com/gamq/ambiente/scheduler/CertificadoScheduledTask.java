package com.gamq.ambiente.scheduler;

import com.gamq.ambiente.validators.CertificadoValidator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CertificadoScheduledTask {
    private final CertificadoValidator certificadoValidator;

    public CertificadoScheduledTask(CertificadoValidator certificadoValidator) {
        this.certificadoValidator = certificadoValidator;
    }

    @Scheduled(cron = "0 1 0 * * *") // todos los días a las 00:01 AM
    public void ejecutarValidacionDiaria() {
        certificadoValidator.verificarCertificados();
    }
}
