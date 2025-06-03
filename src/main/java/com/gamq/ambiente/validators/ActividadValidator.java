package com.gamq.ambiente.validators;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.repository.ActividadRepository;
import org.springframework.stereotype.Component;

@Component
public class ActividadValidator {
    private final ActividadRepository actividadRepository;
    public ActividadValidator(ActividadRepository actividadRepository) { this.actividadRepository =actividadRepository;}

    public boolean validateFechaInicioFinActividad(ActividadDto actividadDto){
        if (actividadDto.getFechaInicio() == null || actividadDto.getFechaFin() == null) {
            return true; // se valida con @NotNull por separado
        }
        return !actividadDto.getFechaFin().before(actividadDto.getFechaInicio());
    }
}
