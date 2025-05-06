package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.FotoVehiculoDto;
import com.gamq.ambiente.model.FotoVehiculo;
import org.springframework.stereotype.Component;

@Component
public class FotoVehiculoMapper {
    public static FotoVehiculoDto toFotoVehiculoDto(FotoVehiculo fotoVehiculo){
        return new FotoVehiculoDto()
                .setUuid(fotoVehiculo.getUuid())
                .setNombre(fotoVehiculo.getNombre())
                .setRuta(fotoVehiculo.getRuta())
                .setNombreUsuario(fotoVehiculo.getNombreUsuario())
                .setUuidUsuario(fotoVehiculo.getUuidUsuario())
                .setEstado(fotoVehiculo.isEstado());
    }

    public static FotoVehiculo toFotoVehiculo(FotoVehiculoDto fotoVehiculoDto){
        return new FotoVehiculo()
                .setUuid(fotoVehiculoDto.getUuid())
                .setNombre(fotoVehiculoDto.getNombre())
                .setRuta(fotoVehiculoDto.getRuta())
                .setNombreUsuario(fotoVehiculoDto.getNombreUsuario())
                .setUuidUsuario(fotoVehiculoDto.getUuidUsuario())
                .setEstado(fotoVehiculoDto.isEstado());
    }
}
