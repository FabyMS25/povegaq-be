package com.gamq.ambiente.validators;

import com.gamq.ambiente.enumeration.StatusInfraccion;
import com.gamq.ambiente.model.Infraccion;
import com.gamq.ambiente.repository.InfraccionRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class InfraccionValidator {

    private final InfraccionRepository infraccionRepository;

    public InfraccionValidator(InfraccionRepository infraccionRepository){
        this.infraccionRepository = infraccionRepository;
    }

    public void marcarInfraccionesVencidas(){
        List<StatusInfraccion> activos = Arrays.asList(
                StatusInfraccion.PENDIENTE,
                StatusInfraccion.NOTIFICADA
        );

        List<Infraccion> infraccionList = infraccionRepository.findByStatusInfraccionIn(activos);

        for (Infraccion infraccion : infraccionList) {
            if (!infraccion.isEnPlazo()) {
                infraccion.setStatusInfraccion(StatusInfraccion.VENCIDA);
                infraccionRepository.save(infraccion);
            }
        }
    }
}
