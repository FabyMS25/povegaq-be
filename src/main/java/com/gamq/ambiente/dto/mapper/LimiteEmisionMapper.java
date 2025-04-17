package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.LimiteEmisionDto;
import com.gamq.ambiente.dto.TipoParametroDto;
import com.gamq.ambiente.model.LimiteEmision;
import com.gamq.ambiente.model.TipoParametro;

public class LimiteEmisionMapper {
    public static LimiteEmisionDto toLimiteEmisionDto(LimiteEmision limiteEmision){
        return new LimiteEmisionDto()
                .setUuid(limiteEmision.getUuid())
                .setTipoCombustible(limiteEmision.getTipoCombustible())
                .setYearFabricacionInicio(limiteEmision.getYearFabricacionInicio())
                .setYearFabricacionFin(limiteEmision.getYearFabricacionFin())
                .setTipoMotor(limiteEmision.getTipoMotor())
                .setLimite(limiteEmision.getLimite())
                .setActivo(limiteEmision.isActivo())
                .setEstado(limiteEmision.isEstado())
                .setTipoParametroDto(limiteEmision.getTipoParametro()==null? null: new TipoParametroDto()
                        .setUuid(limiteEmision.getTipoParametro().getUuid())
                        .setNombre(limiteEmision.getTipoParametro().getNombre())
                        .setDescripcion(limiteEmision.getTipoParametro().getDescripcion())
                        .setUnidad(limiteEmision.getTipoParametro().getUnidad())
                        .setActivo(limiteEmision.getTipoParametro().isActivo())
                        .setEstado(limiteEmision.getTipoParametro().isEstado())
                        .setUuid(limiteEmision.getTipoParametro().getUuid())
                );
    }

    public static LimiteEmision toLimiteEmision(LimiteEmisionDto limiteEmisionDto){
        return  new LimiteEmision()
                .setUuid(limiteEmisionDto.getUuid())
                .setTipoCombustible(limiteEmisionDto.getTipoCombustible())
                .setYearFabricacionInicio(limiteEmisionDto.getYearFabricacionInicio())
                .setYearFabricacionFin(limiteEmisionDto.getYearFabricacionFin())
                .setTipoMotor(limiteEmisionDto.getTipoMotor())
                .setLimite(limiteEmisionDto.getLimite())
                .setCategoriaVehiculo(limiteEmisionDto.getCategoriaVehiculo())
                .setCategoria(limiteEmisionDto.getCategoria())
                .setPeso(limiteEmisionDto.getPeso())
                .setFechaInicio(limiteEmisionDto.getFechaInicio())
                .setFechaFin(limiteEmisionDto.getFechaFin())
                .setActivo(limiteEmisionDto.isActivo())
                .setEstado(limiteEmisionDto.isEstado())
                .setTipoParametro(limiteEmisionDto.getTipoParametroDto()==null? null: new TipoParametro()
                        .setUuid(limiteEmisionDto.getTipoParametroDto().getUuid())
                        .setUuid(limiteEmisionDto.getTipoParametroDto().getUuid())
                        .setNombre(limiteEmisionDto.getTipoParametroDto().getNombre())
                        .setUnidad(limiteEmisionDto.getTipoParametroDto().getUnidad())
                        .setActivo(limiteEmisionDto.getTipoParametroDto().isActivo())
                        .setEstado(limiteEmisionDto.getTipoParametroDto().isEstado())
                        .setUuid(limiteEmisionDto.getTipoParametroDto().getUuid())
                );
    }

}
