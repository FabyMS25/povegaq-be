package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.DatoTecnicoDto;
import com.gamq.ambiente.model.DatoTecnico;

public class DatoTecnicoMapper {
    public static DatoTecnicoDto toDatoTecnicoDto(DatoTecnico datoTecnico){
        return new DatoTecnicoDto()
                .setUuid(datoTecnico.getUuid())
                .setClase(datoTecnico.getClase())
                .setMarca(datoTecnico.getMarca())
                .setPais(datoTecnico.getPais())
                .setTraccion(datoTecnico.getTraccion())
                .setModelo(datoTecnico.getModelo())
                .setServicio(datoTecnico.getServicio())
                .setCilindrada(datoTecnico.getCilindrada())
                .setColor(datoTecnico.getColor())
                .setCapacidadCarga(datoTecnico.getCapacidadCarga())
                .setTipoVehiculo(datoTecnico.getTipoVehiculo())
                .setTipoCarroceria(datoTecnico.getTipoCarroceria())
                .setYearFabricacion(datoTecnico.getYearFabricacion())
                .setNumeroPuertas(datoTecnico.getNumeroPuertas())
                .setTamanoMotor(datoTecnico.getTamanoMotor())
                .setTipoMotor(datoTecnico.getTipoMotor())
                .setKilometraje(datoTecnico.getKilometraje())
                .setEmisionStandard(datoTecnico.getEmisionStandard())
                .setClasificacion(datoTecnico.getClasificacion())
                .setNumeroAsientos(datoTecnico.getNumeroAsientos())
                .setTiempoMotor(datoTecnico.getTiempoMotor())
                .setCategoriaVehiculo(datoTecnico.getCategoriaVehiculo())
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
                .setServicio(datoTecnicoDto.getServicio())
                .setTamanoMotor(datoTecnicoDto.getTamanoMotor())
                .setTipoCarroceria(datoTecnicoDto.getTipoCarroceria())
                .setTraccion(datoTecnicoDto.getTraccion())
                .setTipoMotor(datoTecnicoDto.getTipoMotor())
                .setYearFabricacion(datoTecnicoDto.getYearFabricacion())
                .setClasificacion(datoTecnicoDto.getClasificacion())
                .setNumeroAsientos(datoTecnicoDto.getNumeroAsientos())
                .setTiempoMotor(datoTecnicoDto.getTiempoMotor())
                .setCategoriaVehiculo(datoTecnicoDto.getCategoriaVehiculo())
                .setEstado(datoTecnicoDto.isEstado());
    }
}
