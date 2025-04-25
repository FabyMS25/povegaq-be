package com.gamq.ambiente.controller;

import com.gamq.ambiente.model.Certificado;
import com.gamq.ambiente.repository.CertificadoRepository;
import com.gamq.ambiente.service.CertificadoService;
import com.gamq.ambiente.utils.GeneradorReporte;
import com.gamq.ambiente.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {
    @Autowired
    private GeneradorReporte generadorReporte;
    @Autowired
    private CertificadoService certificadoService;
    @Autowired
    private UrlUtil urlUtil;
    @Autowired
    private CertificadoRepository certificadoRepository;


    @RequestMapping( value = "/certificado", method= RequestMethod.GET)
    @ResponseBody
    public void generarReporteCertificado(
            @RequestParam( name = "uuid") String certificadoUuid,
            @RequestHeader Map<String, String> headers,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        String usuario = headers.getOrDefault("usuario", "Admin");
        String baseUrl = urlUtil.construirBaseUrl(request);
        certificadoService.generarReporteCertificado(certificadoUuid, usuario, baseUrl, response);
    }

    @RequestMapping( value = "/verificar", method= RequestMethod.GET)
    @ResponseBody
    public void verificarCertificado(
            @RequestParam( name = "codigo") String codigo,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {

        try {
            Optional<Certificado> certificadoOptional = certificadoRepository.findByCodigo(codigo);

            response.setContentType("text/plain; charset=UTF-8");

            if (certificadoOptional.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("❌ Certificado no encontrado o inválido.");
                return;
            }

            Certificado certificado = certificadoOptional.get();
            boolean vigente = certificado.getFechaVencimiento().before( new Date());

            String mensaje;
            if (certificado.isEsValido() && !vigente) {
                mensaje = "✅ Certificado VÁLIDO\n\n";
            } else {
                mensaje = "❌ Certificado NO VÁLIDO o VENCIDO\n\n";
            }

            mensaje += "Placa del Vehículo: " + certificado.getInspeccion().getVehiculo().getPlaca() + "\n";
            mensaje += "Fecha de Vencimiento: " + certificado.getFechaVencimiento() + "\n";
            mensaje += "Estado de Certificado: " + (!vigente ? "VIGENTE" : "EXPIRADO");

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(mensaje);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("⚠️ Error al procesar la solicitud.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
