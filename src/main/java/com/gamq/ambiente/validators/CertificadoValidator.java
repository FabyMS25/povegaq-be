package com.gamq.ambiente.validators;

import com.gamq.ambiente.model.Certificado;
import com.gamq.ambiente.repository.CertificadoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

//@Profile("!test") // Solo corre en prod o dev
@Service
public class CertificadoValidator {

    private final CertificadoRepository certificadoRepository;

    public CertificadoValidator(CertificadoRepository certificadoRepository) {
        this.certificadoRepository = certificadoRepository;
    }

    public void verificarCertificados() {
        LocalDate hoy = LocalDate.now();

        List<Certificado> certificados = certificadoRepository.findAll();

        certificados.forEach(certificado -> {
            LocalDate fechaVencimiento = certificado.getFechaVencimiento()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            boolean debeSerInvalido = hoy.isAfter(fechaVencimiento);//   .before( hoy.));
            if (debeSerInvalido && certificado.isEsValido()) {
                certificado.setEsValido(false);
                certificadoRepository.save(certificado);
            }
        });
    }
}
