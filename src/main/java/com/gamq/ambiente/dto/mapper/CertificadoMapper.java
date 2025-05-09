package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.CertificadoDto;
import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.model.Certificado;
import com.gamq.ambiente.model.Inspeccion;

public class CertificadoMapper {
    public static CertificadoDto toCertificadoDto(Certificado certificado){
        return new CertificadoDto()
                .setUuid(certificado.getUuid())
                .setCodigo(certificado.getCodigo())
                .setEsValido(certificado.isEsValido())
                .setFechaEmision(certificado.getFechaEmision())
                .setFechaVencimiento(certificado.getFechaVencimiento())
                .setQrContent(certificado.getQrContent())
                .setEstado(certificado.isEstado())
                .setInspeccionDto(certificado.getInspeccion() == null? null: new InspeccionDto()
                        .setUuid(certificado.getInspeccion().getUuid())
                        .setFechaInspeccion(certificado.getInspeccion().getFechaInspeccion())
                        .setResultado(certificado.getInspeccion().isResultado())
                        .setObservacion(certificado.getInspeccion().getObservacion())
                        .setLugarInspeccion(certificado.getInspeccion().getLugarInspeccion())
                        .setNombreInspector(certificado.getInspeccion().getNombreInspector())
                        .setEquipo(certificado.getInspeccion().getEquipo())
                        .setUuidUsuario(certificado.getInspeccion().getUuidUsuario())
                        .setAltitud(certificado.getInspeccion().getAltitud())
                        .setEstado(certificado.getInspeccion().isEstado())
                );
    }
    public static Certificado toCertificado(CertificadoDto certificadoDto){
        return new Certificado()
                .setUuid(certificadoDto.getUuid())
                .setCodigo(certificadoDto.getCodigo())
                .setEsValido(certificadoDto.isEsValido())
                .setFechaEmision(certificadoDto.getFechaEmision())
                .setFechaVencimiento(certificadoDto.getFechaVencimiento())
                .setQrContent(certificadoDto.getQrContent())
                .setEstado(certificadoDto.isEstado())
                .setInspeccion(certificadoDto.getInspeccionDto() == null? null: new Inspeccion()
                        .setUuid(certificadoDto.getInspeccionDto().getUuid())
                        .setFechaInspeccion(certificadoDto.getInspeccionDto().getFechaInspeccion())
                        .setResultado(certificadoDto.getInspeccionDto().isResultado())
                        .setObservacion(certificadoDto.getInspeccionDto().getObservacion())
                        .setLugarInspeccion(certificadoDto.getInspeccionDto().getLugarInspeccion())
                        .setNombreInspector(certificadoDto.getInspeccionDto().getNombreInspector())
                        .setUuidUsuario(certificadoDto.getInspeccionDto().getUuidUsuario())
                        .setAltitud(certificadoDto.getInspeccionDto().getAltitud())
                        .setEquipo(certificadoDto.getInspeccionDto().getEquipo())
                        .setEstado(certificadoDto.getInspeccionDto().isEstado())
                );
    }
}
