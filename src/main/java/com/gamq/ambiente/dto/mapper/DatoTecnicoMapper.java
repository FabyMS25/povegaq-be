package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.DatoTecnicoDto;
import com.gamq.ambiente.model.DatoTecnico;

public class DatoTecnicoMapper {
    public static DatoTecnicoDto toDatoTecnicoDto(DatoTecnico datoTecnico){
        return new DatoTecnicoDto()
                .setUuid(datoTecnico.getUuid())
                .setTipoVehiculo(datoTecnico.getTipoVehiculo())
                .setCapacidadCarga(datoTecnico.getCapacidadCarga())
                .setCilindrada(datoTecnico.getCilindrada())
                .setClase(datoTecnico.getClase())
                .setColor(datoTecnico.getColor())
                .setEmisionStandard(datoTecnico.getEmisionStandard())
                .setEstado(datoTecnico.isEstado())
                .setKilometraje(datoTecnico.getKilometraje())
                .setMarca(datoTecnico.getMarca())
                .setColor(datoTecnico.getColor())
                .setModelo(datoTecnico.getModelo())
                .setNumeroPuertas(datoTecnico.getNumeroPuertas())
                .setPais(datoTecnico.getPais())
                .setProcedencia(datoTecnico.getProcedencia())
                .setRadicatoria(datoTecnico.getRadicatoria())
                .setServicio(datoTecnico.getServicio())
                .setTamanoMotor(datoTecnico.getTamanoMotor())
                .setTipoCarroceria(datoTecnico.getTipoCarroceria())
                .setTipoCombustion(datoTecnico.getTipoCombustion())
                .setTraccion(datoTecnico.getTraccion())
                .setTipoMotor(datoTecnico.getTipoMotor())
                .setYearFabricacion(datoTecnico.getYearFabricacion())
                .setEstado(datoTecnico.isEstado());
    }

    public static DatoTecnico toDatoTecnico(DatoTecnicoDto datoTecnicoDto){
        return new DatoTecnico()
                .setUuid(datoTecnicoDto.getUuid())
                .setTipoVehiculo(datoTecnicoDto.getTipoVehiculo())
                .setCapacidadCarga(datoTecnicoDto.getCapacidadCarga())
                .setCilindrada(datoTecnicoDto.getCilindrada())
                .setClase(datoTecnicoDto.getClase())
                .setColor(datoTecnicoDto.getColor())
                .setEmisionStandard(datoTecnicoDto.getEmisionStandard())
                .setEstado(datoTecnicoDto.isEstado())
                .setKilometraje(datoTecnicoDto.getKilometraje())
                .setMarca(datoTecnicoDto.getMarca())
                .setColor(datoTecnicoDto.getColor())
                .setModelo(datoTecnicoDto.getModelo())
                .setNumeroPuertas(datoTecnicoDto.getNumeroPuertas())
                .setPais(datoTecnicoDto.getPais())
                .setProcedencia(datoTecnicoDto.getProcedencia())
                .setRadicatoria(datoTecnicoDto.getRadicatoria())
                .setServicio(datoTecnicoDto.getServicio())
                .setTamanoMotor(datoTecnicoDto.getTamanoMotor())
                .setTipoCarroceria(datoTecnicoDto.getTipoCarroceria())
                .setTipoCombustion(datoTecnicoDto.getTipoCombustion())
                .setTraccion(datoTecnicoDto.getTraccion())
                .setTipoMotor(datoTecnicoDto.getTipoMotor())
                .setYearFabricacion(datoTecnicoDto.getYearFabricacion())
                .setEstado(datoTecnicoDto.isEstado());
    }
}
