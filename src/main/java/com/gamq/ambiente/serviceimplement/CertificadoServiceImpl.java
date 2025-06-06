package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.CertificadoDto;
import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.mapper.CertificadoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Certificado;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.repository.CertificadoRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.service.CertificadoService;
import com.gamq.ambiente.utils.FechaUtil;
import com.gamq.ambiente.utils.GeneradorReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CertificadoServiceImpl implements CertificadoService {
    @Autowired
    private CertificadoRepository certificadoRepository;
    @Autowired
    private InspeccionRepository inspeccionRepository;
    @Autowired
    private GeneradorReporte generadorReporte;


    @Override
    public CertificadoDto obtenerCertificadoPorUuid(String uuid) {
        Optional<Certificado> certificadoOptional = certificadoRepository.findByUuid(uuid);
        if(certificadoOptional.isPresent()){
            return CertificadoMapper.toCertificadoDto(certificadoOptional.get());
        }
        throw new ResourceNotFoundException("Certificado", "uuid", uuid);
    }

    @Override
    public CertificadoDto obtenerCertificadoPorCodigo(String codigo) {
        Optional<Certificado> certificadoOptional = certificadoRepository.findByCodigo(codigo);
        if(certificadoOptional.isPresent()){
            return CertificadoMapper.toCertificadoDto(certificadoOptional.get());
        }
        throw new ResourceNotFoundException("Certificado", "codigo", codigo.toString());
    }

    @Override
    public List<CertificadoDto> obtenerCertificados() {
        List<Certificado> certificadoList = certificadoRepository.findAll();
        return  certificadoList.stream().map( certificado -> {
            return  CertificadoMapper.toCertificadoDto(certificado);
        }).collect(Collectors.toList());
    }

    @Override
    public CertificadoDto crearCertificado(CertificadoDto certificadoDto) {
        Optional<Certificado> certificadoOptional = certificadoRepository.findByCodigo(certificadoDto.getCodigo());
        if(certificadoOptional.isEmpty()){
            Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(certificadoDto.getInspeccionDto().getUuid());
            if (inspeccionOptional.isPresent()) {
                if (!inspeccionOptional.get().isResultado()){
                    throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "No puede emitir certificado por el resultado de la inspeccion  es Negativo(no paso la prueba)");
                }
                if (inspeccionOptional.get().getCertificadoList().size() > 0) {
                    throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "La inspeccion ya tiene un certificado");
                }
                if (inspeccionOptional.get().getDetalleInspeccionList().size()==0){
                    throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "La inspeccion no tiene Detalles de inspeccion");
                }
                String codigo = UUID.randomUUID().toString();
                Certificado nuevoCertificado = CertificadoMapper.toCertificado(certificadoDto);
                nuevoCertificado.setCodigo(codigo);
                nuevoCertificado.setQrContent(codigo);
                nuevoCertificado.setFechaVencimiento(FechaUtil.calcularFechaVencimiento(nuevoCertificado.getFechaVencimiento()));
                nuevoCertificado.setInspeccion(inspeccionOptional.get());
                return CertificadoMapper.toCertificadoDto(certificadoRepository.save(nuevoCertificado));
            } else {
                throw new ResourceNotFoundException("Inspeccion", "uuid", certificadoDto.getInspeccionDto().getUuid());
            }
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Certificado ya existe");
    }

    @Override
    public CertificadoDto actualizarCertificado(CertificadoDto certificadoDto) {
        Optional<Certificado> certificadoOptional = certificadoRepository.findByUuid(certificadoDto.getUuid());
        if(certificadoOptional.isPresent()) {
            Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(certificadoDto.getInspeccionDto().getUuid());
            if (inspeccionOptional.isPresent()){
                Certificado updateCertificado = CertificadoMapper.toCertificado(certificadoDto);
                updateCertificado.setIdCertificado(certificadoOptional.get().getIdCertificado());
                updateCertificado.setInspeccion(inspeccionOptional.get());
                return CertificadoMapper.toCertificadoDto(certificadoRepository.save(updateCertificado));
            } else {
                throw new ResourceNotFoundException("Inspeccion", "uuid", certificadoDto.getInspeccionDto().getUuid());
            }
        }
        throw new ResourceNotFoundException("Certificado", "uuid",certificadoDto.getUuid());
    }

    @Override
    public CertificadoDto eliminarCertificado(String uuid) {
        Certificado certificadoQBE = new Certificado(uuid);
        Optional<Certificado> optionalCertificado = certificadoRepository.findOne(Example.of(certificadoQBE));
        if(optionalCertificado.isPresent()){
            Certificado certificado = optionalCertificado.get();
            certificadoRepository.delete(certificado);
            return CertificadoMapper.toCertificadoDto(certificado);
        }
        throw new ResourceNotFoundException("Certificado","uuid", uuid);
    }

    @Override
    public void generarReporteCertificadoOpacidad(String certificadoUuid, String usuario, String baseUrl, HttpServletResponse response) {
        try {
        CertificadoDto certificado = obtenerCertificadoPorUuid(certificadoUuid);

        if (certificado == null || !certificado.isEsValido() || certificado.getFechaVencimiento().before(new Date())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El certificado no es válido o está vencido");
        }
        HashMap<String, Object> parametros = prepararParametros(certificado, usuario, baseUrl);

        generadorReporte.generarSqlReportePdf(
                "reporte_certificado_opacidad",
                "classpath:report/reporte_certificado_opacidad.jrxml",
                parametros,
                response
        );
        } catch (SQLException e) {
            throw new BlogAPIException("409_CONFLICT", HttpStatus.CONFLICT, e.getMessage() + " " + e.getLocalizedMessage());
        }
    }

    @Override
    public void generarReporteCertificadoGnv(String certificadoUuid, String usuario, String baseUrl, HttpServletResponse response) {
        try {
            CertificadoDto certificado = obtenerCertificadoPorUuid(certificadoUuid);

            if (certificado == null || !certificado.isEsValido() || certificado.getFechaVencimiento().before(new Date())) {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El certificado no es válido o está vencido");
            }
            HashMap<String, Object> parametros = prepararParametros(certificado, usuario, baseUrl);

            generadorReporte.generarSqlReportePdf(
                    "reporte_certificado_gnv",
                    "classpath:report/reporte_certificado_gnv.jrxml",
                    parametros,
                    response
            );
        } catch (SQLException e) {
            throw new BlogAPIException("409_CONFLICT", HttpStatus.CONFLICT, e.getMessage() + " " + e.getLocalizedMessage());
        }
    }

    @Override
    public CertificadoDto actualizarCertificadoValido(String uuid, boolean nuevoValido) {
        Certificado certificado = certificadoRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Certificado no encontrado"));
        certificado.setEsValido(nuevoValido);
        certificadoRepository.save(certificado);
        return CertificadoMapper.toCertificadoDto(certificado);
    }

    private HashMap<String, Object> prepararParametros(CertificadoDto certificado, String usuario, String baseUrl) {
        HashMap<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("titulo", "DIRECCION DE MEDIO AMBIENTE");
        parametros.put("subtitulo", "INSPECCION TECNICA VEHICULAR MUNICIPAL");
        parametros.put("usuario", usuario);
        parametros.put("uuidCertificado", certificado.getUuid());
        parametros.put("fechaActual", new Date());
        parametros.put("url", baseUrl + "/api/reporte/verificar?codigo=" + certificado.getCodigo());
        return parametros;
    }
}


