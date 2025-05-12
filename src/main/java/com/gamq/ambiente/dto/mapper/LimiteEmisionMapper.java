package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.LimiteEmisionDto;
import com.gamq.ambiente.dto.TipoParametroDto;
import com.gamq.ambiente.model.LimiteEmision;
import com.gamq.ambiente.model.TipoParametro;

public class LimiteEmisionMapper {
    public static LimiteEmisionDto toLimiteEmisionDto(LimiteEmision limiteEmision){
        return new LimiteEmisionDto()
                .setUuid(limiteEmision.getUuid())
                .setTipoMotor(limiteEmision.getTipoMotor())
                .setTipoCombustible(limiteEmision.getTipoCombustible())
                .setCilindradaMinimo(limiteEmision.getCilindradaMinimo())
                .setCilindradaMaximo(limiteEmision.getCilindradaMaximo())
                .setCategoriaVehiculo(limiteEmision.getCategoriaVehiculo())
                .setCategoria(limiteEmision.getCategoria())
                .setYearFabricacionInicio(limiteEmision.getYearFabricacionInicio())
                .setYearFabricacionFin(limiteEmision.getYearFabricacionFin())
                .setLimite(limiteEmision.getLimite())
                .setPeso(limiteEmision.getPeso())
                .setFechaInicio(limiteEmision.getFechaInicio())
                .setFechaFin(limiteEmision.getFechaFin())
                .setAltitudMinima(limiteEmision.getAltitudMinima())
                .setAltitudMaxima(limiteEmision.getAltitudMaxima())
                .setClaseVehiculo(limiteEmision.getClaseVehiculo())
                .setTiempoMotor(limiteEmision.getTiempoMotor())
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
                .setTipoMotor(limiteEmisionDto.getTipoMotor())
                .setTipoCombustible(limiteEmisionDto.getTipoCombustible())
                .setCilindradaMinimo(limiteEmisionDto.getCilindradaMinimo())
                .setCilindradaMaximo(limiteEmisionDto.getCilindradaMaximo())
                .setCategoriaVehiculo(limiteEmisionDto.getCategoriaVehiculo())
                .setCategoria(limiteEmisionDto.getCategoria())
                .setYearFabricacionInicio(limiteEmisionDto.getYearFabricacionInicio())
                .setYearFabricacionFin(limiteEmisionDto.getYearFabricacionFin())
                .setLimite(limiteEmisionDto.getLimite())
                .setPeso(limiteEmisionDto.getPeso())
                .setFechaInicio(limiteEmisionDto.getFechaInicio())
                .setFechaFin(limiteEmisionDto.getFechaFin())
                .setAltitudMinima(limiteEmisionDto.getAltitudMinima())
                .setAltitudMaxima(limiteEmisionDto.getAltitudMaxima())
                .setClaseVehiculo(limiteEmisionDto.getClaseVehiculo())
                .setTiempoMotor(limiteEmisionDto.getTiempoMotor())
                .setActivo(limiteEmisionDto.isActivo())
                .setEstado(limiteEmisionDto.isEstado())
                .setTipoParametro(limiteEmisionDto.getTipoParametroDto()==null? null: new TipoParametro()
                        .setUuid(limiteEmisionDto.getTipoParametroDto().getUuid())
                        .setNombre(limiteEmisionDto.getTipoParametroDto().getNombre())
                        .setDescripcion(limiteEmisionDto.getTipoParametroDto().getDescripcion())
                        .setUnidad(limiteEmisionDto.getTipoParametroDto().getUnidad())
                        .setActivo(limiteEmisionDto.getTipoParametroDto().isActivo())
                        .setEstado(limiteEmisionDto.getTipoParametroDto().isEstado())
                );
    }

}
