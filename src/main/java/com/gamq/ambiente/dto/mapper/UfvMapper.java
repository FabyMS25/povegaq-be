package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.UfvDto;
import com.gamq.ambiente.model.Ufv;

public class UfvMapper {
    public static UfvDto toUfvDto(Ufv ufv) {
        return new UfvDto()
                .setUuid(ufv.getUuid())
                .setValor(ufv.getValor())
                .setFecha(ufv.getFecha())
                .setEstado(ufv.isEstado());
    }

    public static  Ufv toUfv(UfvDto ufvDto){
        return new Ufv()
                .setUuid(ufvDto.getUuid())
                .setValor(ufvDto.getValor())
                .setFecha(ufvDto.getFecha())
                .setEstado(ufvDto.isEstado());
    }
}
