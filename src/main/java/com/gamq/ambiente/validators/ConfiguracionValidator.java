package com.gamq.ambiente.validators;

import com.gamq.ambiente.dto.ConfiguracionDto;
import com.gamq.ambiente.repository.ConfiguracionRepository;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracionValidator {
    private final ConfiguracionRepository configuracionRepository;
    public ConfiguracionValidator(ConfiguracionRepository configuracionRepository) { this.configuracionRepository =configuracionRepository;}

    public boolean validateFechaInicioFinConfiguracion(ConfiguracionDto configuracionDto){
        if (configuracionDto.getFechaInicio() == null || configuracionDto.getFechaFin() == null) {
            return true; // se valida con @NotNull por separado
        }
        return !configuracionDto.getFechaFin().before(configuracionDto.getFechaInicio());
    }
}
