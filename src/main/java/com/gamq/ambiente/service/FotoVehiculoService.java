package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.FotoVehiculoDto;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FotoVehiculoService {
    FotoVehiculoDto obtenerFotoVehiculoPorUuid(String uuid);
    List<FotoVehiculoDto> obtenerFotoVehiculos();
    FotoVehiculoDto crearFotoVehiculo(FotoVehiculoDto fotoVehiculoDto);
    FotoVehiculoDto actualizarFotoVehiculo(FotoVehiculoDto fotoVehiculoDto);
    FotoVehiculoDto eliminarFotoVehiculo(String uuid);
    Resource descargarArchivo(String nombreFile);
}
