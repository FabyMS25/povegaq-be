package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.DatoTecnicoDto;
import com.gamq.ambiente.dto.PropietarioDto;
import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.Vehiculo;

public class VehiculoMapper {
    public static VehiculoDto toVehiculoDto(Vehiculo vehiculo){
        return new VehiculoDto()
                .setUuid(vehiculo.getUuid())
                .setEsOficial(vehiculo.isEsOficial())
                .setFechaRegistro(vehiculo.getFechaRegistro())
                .setJuridiccionOrigen(vehiculo.getJuridiccionOrigen())
                .setPlaca(vehiculo.getPlaca())
                .setPoliza(vehiculo.getPoliza())
                .setVinNumeroIdentificacion(vehiculo.getVinNumeroIdentificacion())
                .setEstado(vehiculo.isEstado())
                .setEsUnidadIndustrial(vehiculo.isEsUnidadIndustrial())
                .setEsMovil(vehiculo.getEsMovil())
                .setPinNumeroIdentificacion(vehiculo.getPinNumeroIdentificacion())
                .setCategoriaVehicular(vehiculo.getCategoriaVehicular())
                .setDatoTecnicoDto(vehiculo.getDatoTecnico()== null? null: new DatoTecnicoDto()
                        .setUuid(vehiculo.getDatoTecnico().getUuid())
                        .setTipoVehiculo(vehiculo.getDatoTecnico().getTipoVehiculo())
                        .setCapacidadCarga(vehiculo.getDatoTecnico().getCapacidadCarga())
                        .setCilindrada(vehiculo.getDatoTecnico().getCilindrada())
                        .setClase(vehiculo.getDatoTecnico().getClase())
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
                        .setTipoCombustion(vehiculo.getDatoTecnico().getTipoCombustion())
                        .setTraccion(vehiculo.getDatoTecnico().getTraccion())
                        .setTipoMotor(vehiculo.getDatoTecnico().getTipoMotor())
                        .setYearFabricacion(vehiculo.getDatoTecnico().getYearFabricacion())
                        .setEstado(vehiculo.getDatoTecnico().isEstado())
                )

                .setPropietarioDto( vehiculo.getPropietario() == null? null: new PropietarioDto()
                        .setUuid(vehiculo.getPropietario().getUuid())
                        .setNombreCompleto(vehiculo.getPropietario().getNombreCompleto())
                        .setNroDocumento(vehiculo.getPropietario().getNroDocumento())
                        .setTipoDocumento(vehiculo.getPropietario().getTipoDocumento())
                        .setExpedido(vehiculo.getPropietario().getExpedido())
                        .setEmail(vehiculo.getPropietario().getEmail())
                        .setNroTelefono(vehiculo.getPropietario().getNroTelefono())
                        .setEstado(vehiculo.getPropietario().isEstado())
                        .setTipoContribuyenteDto(vehiculo.getPropietario() == null? null: new TipoContribuyenteDto()
                                .setUuid(vehiculo.getPropietario().getTipoContribuyente().getUuid())
                                .setDescripcion(vehiculo.getPropietario().getTipoContribuyente().getDescripcion())
                                .setEstado(vehiculo.getPropietario().getTipoContribuyente().isEstado())
                        )
                );

    }

    public static Vehiculo toVehiculo(VehiculoDto vehiculoDto){
        return new Vehiculo()
                .setUuid(vehiculoDto.getUuid())
                .setEsOficial(vehiculoDto.isEsOficial())
                .setFechaRegistro(vehiculoDto.getFechaRegistro())
                .setJuridiccionOrigen(vehiculoDto.getJuridiccionOrigen())
                .setPlaca(vehiculoDto.getPlaca())
                .setPoliza(vehiculoDto.getPoliza())
                .setVinNumeroIdentificacion(vehiculoDto.getVinNumeroIdentificacion())
                .setEstado(vehiculoDto.isEstado())
                .setEsMovil(vehiculoDto.getEsMovil())
                .setEsUnidadIndustrial(vehiculoDto.isEsUnidadIndustrial())
                .setPinNumeroIdentificacion(vehiculoDto.getPinNumeroIdentificacion())
                .setCategoriaVehicular(vehiculoDto.getCategoriaVehicular())
                .setPropietario( vehiculoDto.getPropietarioDto() == null? null: new Propietario()
                        .setUuid(vehiculoDto.getPropietarioDto().getUuid())
                        .setNombreCompleto(vehiculoDto.getPropietarioDto().getNombreCompleto())
                        .setNroDocumento(vehiculoDto.getPropietarioDto().getNroDocumento())
                        .setTipoDocumento(vehiculoDto.getPropietarioDto().getTipoDocumento())
                        .setExpedido(vehiculoDto.getPropietarioDto().getExpedido())
                        .setEmail(vehiculoDto.getPropietarioDto().getEmail())
                        .setNroTelefono(vehiculoDto.getPropietarioDto().getNroTelefono())
                        .setEstado(vehiculoDto.getPropietarioDto().isEstado())
                        .setTipoContribuyente(vehiculoDto.getPropietarioDto() == null? null: new TipoContribuyente()
                                .setUuid(vehiculoDto.getPropietarioDto().getTipoContribuyenteDto().getUuid())
                                .setDescripcion(vehiculoDto.getPropietarioDto().getTipoContribuyenteDto().getDescripcion())
                                .setEstado(vehiculoDto.getPropietarioDto().getTipoContribuyenteDto().isEstado())
                        ))
                ;
    }
}
