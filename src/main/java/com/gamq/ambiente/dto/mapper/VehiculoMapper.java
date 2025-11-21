package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.*;
import com.gamq.ambiente.model.DatoTecnico;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.Vehiculo;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VehiculoMapper {
    public static VehiculoDto toVehiculoDto(Vehiculo vehiculo){
        return new VehiculoDto()
                .setUuid(vehiculo.getUuid())
                .setEsOficial(vehiculo.isEsOficial())
                .setFechaRegistro(vehiculo.getFechaRegistro())
                .setJurisdiccionOrigen(vehiculo.getJurisdiccionOrigen())
                .setPlaca(vehiculo.getPlaca())
                .setPoliza(vehiculo.getPoliza())
                .setVinNumeroIdentificacion(vehiculo.getVinNumeroIdentificacion())
                .setEstado(vehiculo.isEstado())
                .setEsUnidadIndustrial(vehiculo.isEsUnidadIndustrial())
                .setEsMovil(vehiculo.getEsMovil())
                .setPinNumeroIdentificacion(vehiculo.getPinNumeroIdentificacion())
                .setNroCopiasPlaca(vehiculo.getNroCopiasPlaca())
                .setPlacaAnterior(vehiculo.getPlacaAnterior())
             //   .setChasis(vehiculo.getChasis())
                .setDatoTecnicoDto(vehiculo.getDatoTecnico()== null? null: new DatoTecnicoDto()
                        .setUuid(vehiculo.getDatoTecnico().getUuid())
                        .setTipoVehiculo(vehiculo.getDatoTecnico().getTipoVehiculo())
                        .setCapacidadCarga(vehiculo.getDatoTecnico().getCapacidadCarga())
                        .setCilindrada(vehiculo.getDatoTecnico().getCilindrada())
                       // .setClase(vehiculo.getDatoTecnico().getClase())
                        .setColor(vehiculo.getDatoTecnico().getColor())
                        .setEmisionStandard(vehiculo.getDatoTecnico().getEmisionStandard())
                        .setEstado(vehiculo.getDatoTecnico().isEstado())
                        .setKilometraje(vehiculo.getDatoTecnico().getKilometraje())
                        .setMarca(vehiculo.getDatoTecnico().getMarca())
                        .setColor(vehiculo.getDatoTecnico().getColor())
                        .setModelo(vehiculo.getDatoTecnico().getModelo())
                        .setNumeroPuertas(vehiculo.getDatoTecnico().getNumeroPuertas())
                        .setPais(vehiculo.getDatoTecnico().getPais())
                        .setServicio(vehiculo.getDatoTecnico().getServicio())
                        .setTamanoMotor(vehiculo.getDatoTecnico().getTamanoMotor())
                        .setTipoCarroceria(vehiculo.getDatoTecnico().getTipoCarroceria())
                        //.setTipoCombustion(vehiculo.getDatoTecnico().getTipoCombustion())
                        .setTraccion(vehiculo.getDatoTecnico().getTraccion())
                        .setTipoMotor(vehiculo.getDatoTecnico().getTipoMotor())
                        .setYearFabricacion(vehiculo.getDatoTecnico().getYearFabricacion())
                        .setClasificacion(vehiculo.getDatoTecnico().getClasificacion())
                        .setNumeroAsientos(vehiculo.getDatoTecnico().getNumeroAsientos())
                        .setTiempoMotor(vehiculo.getDatoTecnico().getTiempoMotor())
                        .setCategoriaVehiculo(vehiculo.getDatoTecnico().getCategoriaVehiculo())
                        .setEstado(vehiculo.getDatoTecnico().isEstado())
                        .setTipoClaseVehiculoDto(vehiculo.getDatoTecnico().getTipoClaseVehiculo()==null? null: new TipoClaseVehiculoDto()
                                .setUuid(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getUuid())
                                .setNombre(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getNombre())
                                .setDescripcion(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getDescripcion())
                                .setEstado(vehiculo.getDatoTecnico().getTipoClaseVehiculo().isEstado())
                                .setClaseVehiculoDto(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getClaseVehiculo() == null? null: new ClaseVehiculoDto()
                                        .setUuid(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getClaseVehiculo().getUuid())
                                        .setNombre(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getClaseVehiculo().getNombre())
                                        .setDescripcion(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getClaseVehiculo().getDescripcion())
                                        .setEstado(vehiculo.getDatoTecnico().getTipoClaseVehiculo().getClaseVehiculo().isEstado())
                                )
                        )
                )

                .setPropietarioDto( vehiculo.getPropietario() == null? null: new PropietarioDto()
                        .setUuid(vehiculo.getPropietario().getUuid())
                      //  .setNombreCompleto(Stream.of(
                      //                  vehiculo.getPropietario().getNombre(),
                      //                  vehiculo.getPropietario().getPrimerApellido(),
                      //                  vehiculo.getPropietario().getSegundoApellido(),
                      //                  vehiculo.getPropietario().getApellidoEsposo()
                      //          )
                      //          .filter(Objects::nonNull)
                      //          .map(String::trim)
                      //          .filter(s -> !s.isEmpty())
                      //          .collect(Collectors.joining(" "))
                       // )
                        //.setNombreCompleto(vehiculo.getPropietario().getNombreCompleto())
                        .setNombre(vehiculo.getPropietario().getNombre())
                        .setPrimerApellido(vehiculo.getPropietario().getPrimerApellido())
                        .setSegundoApellido(vehiculo.getPropietario().getSegundoApellido())
                        .setApellidoEsposo(vehiculo.getPropietario().getApellidoEsposo())
                        .setNumeroDocumento(vehiculo.getPropietario().getNumeroDocumento())
                        .setTipoDocumento(vehiculo.getPropietario().getTipoDocumento())
                        .setExpedido(vehiculo.getPropietario().getExpedido())
                        .setEmail(vehiculo.getPropietario().getEmail())
                        .setNroTelefono(vehiculo.getPropietario().getNroTelefono())
                        .setEstado(vehiculo.getPropietario().isEstado())

                        .setEstadoCivil(vehiculo.getPropietario().getEstadoCivil())
                        .setGenero(vehiculo.getPropietario().getGenero())
                        .setFechaNacimiento(vehiculo.getPropietario().getFechaNacimiento())
                        .setTipoContribuyenteDto(vehiculo.getPropietario() == null? null: new TipoContribuyenteDto()
                                .setUuid(vehiculo.getPropietario().getTipoContribuyente().getUuid())
                                .setDescripcion(vehiculo.getPropietario().getTipoContribuyente().getDescripcion())
                                .setCodigo(vehiculo.getPropietario().getTipoContribuyente().getCodigo())
                                .setEstado(vehiculo.getPropietario().getTipoContribuyente().isEstado())
                        )
                )
                .setFotoVehiculoDtoList(vehiculo.getFotoVehiculoList().stream().map(fotoVehiculo -> {
                    return FotoVehiculoMapper.toFotoVehiculoDto(fotoVehiculo);
                }).collect(Collectors.toList()))
                .setVehiculoTipoCombustibleDtoList(vehiculo.getVehiculoTipoCombustibleList().stream().map(vehiculoTipoCombustible -> {
                    return VehiculoTipoCombustibleMapper.toDtoSinVehiculo(vehiculoTipoCombustible);
                }).collect(Collectors.toList()))
                ;
    }

    public static Vehiculo toVehiculo(VehiculoDto vehiculoDto){
        return new Vehiculo()
                .setUuid(vehiculoDto.getUuid())
                .setEsOficial(vehiculoDto.isEsOficial())
                .setFechaRegistro(vehiculoDto.getFechaRegistro())
                .setJurisdiccionOrigen(vehiculoDto.getJurisdiccionOrigen())
                .setPlaca(vehiculoDto.getPlaca())
                .setPoliza(vehiculoDto.getPoliza())
                .setVinNumeroIdentificacion(vehiculoDto.getVinNumeroIdentificacion())
                .setEstado(vehiculoDto.isEstado())
                .setEsMovil(vehiculoDto.getEsMovil())
                .setEsUnidadIndustrial(vehiculoDto.isEsUnidadIndustrial())
                .setPinNumeroIdentificacion(vehiculoDto.getPinNumeroIdentificacion())
                .setNroCopiasPlaca(vehiculoDto.getNroCopiasPlaca())
                .setPlacaAnterior(vehiculoDto.getPlacaAnterior())
             //   .setChasis(vehiculoDto.getChasis())
                ;
    }
}
