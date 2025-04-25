package com.gamq.ambiente.service;



import com.gamq.ambiente.dto.CertificadoDto;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CertificadoService {
    CertificadoDto obtenerCertificadoPorUuid(String uuid);
    CertificadoDto obtenerCertificadoPorCodigo(String codigo);
    List<CertificadoDto> obtenerCertificados();
    CertificadoDto crearCertificado(CertificadoDto certificadoDto);
    CertificadoDto actualizarCertificado(CertificadoDto certificadoDto);
    CertificadoDto eliminarCertificado(String uuid);
    void generarReporteCertificado(String certificadoUuid, String usuario, String baseUrl, HttpServletResponse response);
    CertificadoDto actualizarCertificadoValido(String uuid, boolean nuevoValido);
}
