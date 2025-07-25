package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.MedicionAireDto;

import java.util.Date;
import java.util.List;

public interface MedicionAireService {
    MedicionAireDto obtenerMedicionAirePorUuid(String uuid);
    List<MedicionAireDto> obtenerMedicionAirePorFecha(Date fecha);
    List<MedicionAireDto> obtenerMedicionesAire();
    MedicionAireDto crearMedicionAire(MedicionAireDto medicionDto);
    List<MedicionAireDto> crearMedicionesAireMasivo(List<MedicionAireDto> medicionesDto);
    MedicionAireDto actualizarMedicionAire(MedicionAireDto medicionDto);
    MedicionAireDto eliminarMedicionAire(String uuid);

}
