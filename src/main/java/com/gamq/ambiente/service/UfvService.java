package com.gamq.ambiente.service;

import com.gamq.ambiente.dto.UfvDto;

import java.util.Date;
import java.util.List;

public interface UfvService {
    UfvDto obtenerUfvPorUuid(String uuid);
    UfvDto obtenerUfvPorFecha(Date fecha);
    List<UfvDto> obtenerUfves();
    UfvDto crearUfv(UfvDto ufvDto);
    UfvDto actualizarUfv(UfvDto ufvDto);
    UfvDto eliminarUfv(String uuid);
}
