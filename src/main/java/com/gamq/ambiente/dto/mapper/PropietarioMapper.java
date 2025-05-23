package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.PropietarioDto;
import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.TipoContribuyente;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropietarioMapper {
    public static PropietarioDto toPropietarioDto(Propietario propietario){
        return new PropietarioDto()
                .setUuid(propietario.getUuid())
           //     .setNombreCompleto(
            //            Stream.of(
            //                            safeTrim(propietario.getNombre()),
            //                            safeTrim(propietario.getPrimerApellido()),
            //                            safeTrim(propietario.getSegundoApellido()),
            //                            safeTrim(propietario.getApellidoEsposo())
            //                    )
            //                    .filter(Objects::nonNull)
            //                    .map(String::trim)
            //                    .filter(s ->  s != null && !s.isEmpty())
            //                    .collect(Collectors.joining(" "))
            //    )
                .setNombre(propietario.getNombre())
                .setPrimerApellido(propietario.getPrimerApellido())
                .setSegundoApellido(propietario.getSegundoApellido())
                .setApellidoEsposo(propietario.getApellidoEsposo())
                .setEstadoCivil(propietario.getEstadoCivil())
                .setGenero(propietario.getGenero())
                .setFechaNacimiento(propietario.getFechaNacimiento())

                .setNumeroDocumento(propietario.getNumeroDocumento())
                .setTipoDocumento(propietario.getTipoDocumento())
                .setExpedido(propietario.getExpedido())
                .setEmail(propietario.getEmail())
                .setNroTelefono(propietario.getNroTelefono())
                .setEstado(propietario.isEstado())
                .setTipoContribuyenteDto(propietario.getTipoContribuyente() == null? null: new TipoContribuyenteDto()
                        .setUuid(propietario.getTipoContribuyente().getUuid())
                        .setDescripcion(propietario.getTipoContribuyente().getDescripcion())
                        .setCodigo(propietario.getTipoContribuyente().getCodigo())
                        .setEstado(propietario.getTipoContribuyente().isEstado())
                )
                .setVehiculoDtoList(propietario.getVehiculoList().stream().map( vehiculo -> {
                    return VehiculoMapper.toVehiculoDto(vehiculo);
                }).collect(Collectors.toList()))
                ;
    }

    public static Propietario toPropietario(PropietarioDto propietarioDto){
        return new Propietario()
                .setUuid(propietarioDto.getUuid())
                .setNombre(propietarioDto.getNombre())
                .setPrimerApellido(propietarioDto.getPrimerApellido())
                .setSegundoApellido(propietarioDto.getSegundoApellido())
                .setApellidoEsposo(propietarioDto.getApellidoEsposo())
                .setEstadoCivil(propietarioDto.getEstadoCivil())
                .setGenero(propietarioDto.getGenero())
                .setFechaNacimiento(propietarioDto.getFechaNacimiento())
                .setNumeroDocumento(propietarioDto.getNumeroDocumento())
                .setTipoDocumento(propietarioDto.getTipoDocumento())
                .setExpedido(propietarioDto.getExpedido())
                .setEmail(propietarioDto.getEmail())
                .setNroTelefono(propietarioDto.getNroTelefono())
                .setEstado(propietarioDto.isEstado())
                .setTipoContribuyente(propietarioDto.getTipoContribuyenteDto() == null? null: new TipoContribuyente()
                        .setUuid(propietarioDto.getTipoContribuyenteDto().getUuid())
                        .setDescripcion(propietarioDto.getTipoContribuyenteDto().getDescripcion())
                        .setCodigo(propietarioDto.getTipoContribuyenteDto().getCodigo())
                        .setEstado(propietarioDto.getTipoContribuyenteDto().isEstado())
                )
                ;
    }

    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }
}
