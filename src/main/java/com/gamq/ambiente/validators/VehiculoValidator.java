package com.gamq.ambiente.validators;

import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.repository.VehiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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

        if (tienePlaca && !tienePoliza && !tieneVin && !tienePin) return true;                     // solo placa
        if (tienePoliza && !tienePlaca && !tieneVin && !tienePin) return true;                     // solo poliza
        if (tieneVin && tienePoliza) return true;                                                  // vin + poliza
        if (tienePlaca && tienePoliza && tieneVin) return true;                                    // placa + poliza + vin
        if (!tienePlaca && !tienePoliza && !tieneVin && tienePin) return true;                     // solo pin

      //  context.disableDefaultConstraintViolation();
      //  context.buildConstraintViolationWithTemplate(
      //          "Combinación inválida. Requiere: (1) placa sola, (2) poliza sola, (3) poliza + vin, (4) placa + poliza + vin, o (5) pin solo."
      //  ).addConstraintViolation();

        return false;
    }

}
