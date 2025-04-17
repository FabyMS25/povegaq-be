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
                        .setLugarInspeccion(certificado.getInspeccion().getLugarInspeccion())
                        .setNombreInspector(certificado.getInspeccion().getNombreInspector())
                        .setObservacion(certificado.getInspeccion().getObservacion())
                        .setResultado(certificado.getInspeccion().isResultado())
                        .setUuidUsuario(certificado.getInspeccion().getUuidUsuario())
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
                        .setLugarInspeccion(certificadoDto.getInspeccionDto().getLugarInspeccion())
                        .setNombreInspector(certificadoDto.getInspeccionDto().getNombreInspector())
                        .setObservacion(certificadoDto.getInspeccionDto().getObservacion())
                        .setResultado(certificadoDto.getInspeccionDto().isResultado())
                        .setUuidUsuario(certificadoDto.getInspeccionDto().getUuidUsuario())
                        .setEstado(certificadoDto.getInspeccionDto().isEstado())
                );
    }
}
