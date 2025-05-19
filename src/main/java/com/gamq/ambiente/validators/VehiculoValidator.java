package com.gamq.ambiente.validators;

import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.VehiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VehiculoValidator {
    private final VehiculoRepository vehiculoRepository;
    public VehiculoValidator(VehiculoRepository vehiculoRepository){
        this.vehiculoRepository   = vehiculoRepository;
    }

    public void validatePlacaVehiculo(VehiculoDto vehiculoDto){
        String placa = vehiculoDto.getPlaca().trim().toLowerCase();
        if (vehiculoRepository.findByPlaca(placa).isPresent()) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "la placa del vehiculo ya existe");
        }
    }

    public void validateExisteVehiculoLikePlaca(VehiculoDto vehiculoDto){
        String placa = vehiculoDto.getPlaca().trim().toLowerCase();
        if(vehiculoRepository.exitsVehiculoLikePlaca(placa, vehiculoDto.getUuid())){
            throw  new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,"existe un vehiculo con esa placa");
        }
    }

    public boolean validateVehiculo(VehiculoDto vehiculoDto){
        boolean tienePlaca = vehiculoDto.getPlaca() != null && !vehiculoDto.getPlaca().isBlank();
        boolean tienePoliza = vehiculoDto.getPoliza() != null && !vehiculoDto.getPoliza().isBlank();
        boolean tieneVin = vehiculoDto.getVinNumeroIdentificacion() != null && !vehiculoDto.getVinNumeroIdentificacion().isBlank();
        boolean tienePin = vehiculoDto.getPinNumeroIdentificacion() != null && !vehiculoDto.getPinNumeroIdentificacion().isBlank();

        if (tienePlaca && !tienePoliza && !tieneVin && !tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(vehiculoDto.getPlaca());
            if(vehiculoOptional.isEmpty()){
                return true;
            }
        }

        if (!tienePlaca && tienePoliza && !tieneVin && tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPoliza(vehiculoDto.getPoliza());
            if(vehiculoOptional.isEmpty()) {
                 vehiculoOptional = vehiculoRepository.findByPinNumeroIdentificacion(vehiculoDto.getPoliza());
                if(vehiculoOptional.isEmpty()) {
                    return true;
                }
            }
        }

        if (!tienePlaca && tienePoliza && !tieneVin && !tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPoliza(vehiculoDto.getPoliza());
            if(vehiculoOptional.isEmpty()) {
                return true;
            }
        }

        if (!tienePlaca && tieneVin && tienePoliza && !tienePin ){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByVinNumeroIdentificacion(vehiculoDto.getVinNumeroIdentificacion());
            if(vehiculoOptional.isEmpty()) {
                vehiculoOptional = vehiculoRepository.findByPoliza(vehiculoDto.getPoliza());
                if(vehiculoOptional.isEmpty()) {
                    return true;
                }
            }
        }

        if (tienePlaca && tienePoliza && tieneVin && !tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(vehiculoDto.getPlaca());
            if (vehiculoOptional.isEmpty()) {
                vehiculoOptional = vehiculoRepository.findByPoliza(vehiculoDto.getPoliza());
                if (vehiculoOptional.isEmpty()) {
                    vehiculoOptional = vehiculoRepository.findByVinNumeroIdentificacion(vehiculoDto.getVinNumeroIdentificacion());
                    if (vehiculoOptional.isEmpty()) {
                        return true;
                    }
                }
            }
        }

        if (tienePlaca && tienePoliza && !tieneVin && !tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(vehiculoDto.getPlaca());
            if (vehiculoOptional.isEmpty()) {
                vehiculoOptional = vehiculoRepository.findByPoliza(vehiculoDto.getPoliza());
                if (vehiculoOptional.isEmpty()) {
                    return true;
                }
            }
        }

        if (!tienePlaca && !tienePoliza && !tieneVin && tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPinNumeroIdentificacion(vehiculoDto.getPinNumeroIdentificacion());
            if(!vehiculoOptional.isPresent()) {
                return true;
            }
        }

        if (tienePlaca && !tienePoliza && tieneVin && !tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(vehiculoDto.getPlaca());
            if(vehiculoOptional.isEmpty()) {
                vehiculoOptional = vehiculoRepository.findByVinNumeroIdentificacion(vehiculoDto.getVinNumeroIdentificacion());
                if (vehiculoOptional.isEmpty()) {
                    return true;
                }
            }
        }

        if (!tienePlaca && !tienePoliza && tieneVin && !tienePin){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByVinNumeroIdentificacion(vehiculoDto.getVinNumeroIdentificacion());
            if(!vehiculoOptional.isPresent()) {
                return true;
            }
        }
        return false;
    }

}
