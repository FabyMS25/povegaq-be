package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.MedicionAireDto;
import com.gamq.ambiente.model.MedicionAire;

public class MedicionAireMapper {
    public static MedicionAireDto toMedicionAireDto(MedicionAire medicionAire){
        return new MedicionAireDto()
                .setUuid(medicionAire.getUuid())
                .setFecha(medicionAire.getFecha())
                .setMes(medicionAire.getMes())
                .setDia(medicionAire.getDia())
                .setValor(medicionAire.getValor())
                .setEstacion(medicionAire.getEstacion())
                .setEstado(medicionAire.isEstado());
    }

    public static MedicionAire toMedicionAire(MedicionAireDto medicionAireDto){
        return new MedicionAire()
                .setUuid(medicionAireDto.getUuid())
                .setFecha(medicionAireDto.getFecha())
                .setMes(medicionAireDto.getMes())
                .setDia(medicionAireDto.getDia())
                .setValor(medicionAireDto.getValor())
                .setEstacion(medicionAireDto.getEstacion())
                .setEstado(medicionAireDto.isEstado());
    }
}
