package com.gamq.ambiente.dto.mapper;
import com.gamq.ambiente.dto.TipoCombustibleDto;
import com.gamq.ambiente.model.TipoCombustible;

public class TipoCombustibleMapper {
    public static TipoCombustibleDto toTipoCombustibleDto(TipoCombustible tipoCombustible){
        return new TipoCombustibleDto()
                .setUuid(tipoCombustible.getUuid())
                .setNombre(tipoCombustible.getNombre())
                .setDescripcion(tipoCombustible.getDescripcion())
                .setTipoMotor(tipoCombustible.getTipoMotor())
                .setEstado(tipoCombustible.isEstado());
    }

    public static  TipoCombustible toTipoCombustible(TipoCombustibleDto tipoCombustibleDto){
        return new TipoCombustible()
                .setUuid(tipoCombustibleDto.getUuid())
                .setNombre(tipoCombustibleDto.getNombre())
                .setDescripcion(tipoCombustibleDto.getDescripcion())
                .setTipoMotor(tipoCombustibleDto.getTipoMotor())
                .setEstado(tipoCombustibleDto.isEstado());
    }
}
