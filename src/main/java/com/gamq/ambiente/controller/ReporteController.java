package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.mapper.InfraccionMapper;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.StatusInfraccion;
import com.gamq.ambiente.model.Certificado;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.repository.CertificadoRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.NotificacionRepository;
import com.gamq.ambiente.service.CertificadoService;
import com.gamq.ambiente.service.InfraccionService;
import com.gamq.ambiente.service.NotificacionService;
import com.gamq.ambiente.utils.FechaUtil;
import com.gamq.ambiente.utils.GeneradorReporte;
import com.gamq.ambiente.utils.NombreContribuyenteUtil;
import com.gamq.ambiente.utils.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/reporte")
public class ReporteController {
    @Value("${spring.jackson.time-zone}")
    private String zonaHorario;
    @Autowired
    private GeneradorReporte generadorReporte;
    @Autowired
    private CertificadoService certificadoService;
    @Autowired
    private UrlUtil urlUtil;
    @Autowired
    private CertificadoRepository certificadoRepository;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private InfraccionService infraccionService;
    @Autowired
    private InspeccionRepository inspeccionRepository;

    @RequestMapping( value = "/infraccion-portipo", method= RequestMethod.GET)
    @ResponseBody
    public void generarInfraccionesPorTipo(
            @RequestParam( name = "fechaInicio") Date fechaInicio,
            @RequestParam( name = "fechaFin") Date fechaFin,
            @RequestParam( name = "estadoPago") boolean estadoPago,
            @RequestParam(name = "descripcion", required = false) String descripcion,
            @RequestParam(name = "articulo", required = false) String articulo,
            @RequestParam(name = "grado", required = false) String grado,
            @RequestParam(name = "valorUFV", required = false) BigDecimal valorUFV,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            Date fechaActual =  new Date();

            ZoneId zona = ZoneId.of(zonaHorario);
            Date fechaInicioAjustada = FechaUtil.ajustarFechaInicioDia(fechaInicio,zona);
            Date fechaFinAjustada = FechaUtil.ajustarFechaFinDia(fechaFin,zona);
            parametros.put("fechaInicial",new Timestamp(fechaInicioAjustada.getTime()));
            parametros.put("fechaFinal", new Timestamp(fechaFinAjustada.getTime()));
            parametros.put("estadoPago", estadoPago);

            if (descripcion != null && !descripcion.isBlank()) parametros.put("descripcion", descripcion);
            if (articulo != null && !articulo.isBlank()) parametros.put("articulo", articulo);
            if (grado != null && !grado.isBlank()) parametros.put("grado", grado);
            if (valorUFV != null) parametros.put("valorUFV", valorUFV);

            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("criterio", estadoPago?"Pagadas":"Adeudadas");
            parametros.put("usuario", nombreUsuario);
            parametros.put("fechaActual", fechaActual);

            generadorReporte.generarSqlReportePdf(
                    "reporte_infracciones_portipo",
                    "classpath:report/reporte_infracciones_portipo.jrxml",
                    parametros,
                    response
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }


    @RequestMapping( value = "/infraccion-detallada", method= RequestMethod.GET)
    @ResponseBody
    public void generarInfraccionDetallada(
            @RequestParam( name = "fechaInicio") Date fechaInicio,
            @RequestParam( name = "fechaFin") Date fechaFin,
            @RequestParam( name = "estadoPago") boolean estadoPago,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            Date fechaActual =  new Date();

            ZoneId zona = ZoneId.of(zonaHorario);
            Date fechaInicioAjustada = FechaUtil.ajustarFechaInicioDia(fechaInicio,zona);
            Date fechaFinAjustada = FechaUtil.ajustarFechaFinDia(fechaFin,zona);
            parametros.put("fechaInicial",new Timestamp(fechaInicioAjustada.getTime()));
            parametros.put("fechaFinal", new Timestamp(fechaFinAjustada.getTime()));
            parametros.put("estadoPago", estadoPago);
            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("criterio", estadoPago?"Pagadas":"Adeudadas");
            parametros.put("usuario", nombreUsuario);
            parametros.put("fechaActual", fechaActual);

            generadorReporte.generarSqlReportePdf(
                    "reporte_infraccion_detallada",
                    "classpath:report/reporte_infraccion_detallada.jrxml",
                    parametros,
                    response
            );
       } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }


    @RequestMapping( value = "/notificacion-completa", method= RequestMethod.GET)
    @ResponseBody
    public void generarNotificacionCompleta(
            @RequestParam( name = "uuidNotificacion") String uuidNotificacion,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            Date fechaActual =  new Date();

            if (uuidNotificacion != null ) {
                NotificacionDto notificacionDto = notificacionService.obtenerNotificacionPorUuid(uuidNotificacion);
            }

            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("subtitulo", "");
            parametros.put("usuario", nombreUsuario);
            parametros.put("fechaActual", fechaActual);
            parametros.put("uuidNotificacion", uuidNotificacion);
            generadorReporte.generarSqlReportePdf(
                    "reporte_notificacion_completa",
                    "classpath:report/reporte_notificacion_completa.jrxml",
                    parametros,
                    response
            );
            NotificacionDto notificacionDto = notificacionService.actualizarEstadoNotificacion(uuidNotificacion, EstadoNotificacion.ENVIADA);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    @RequestMapping( value = "/notificacion", method= RequestMethod.GET)
    @ResponseBody
    public void generarNotificacion(
            @RequestParam( name = "uuidNotificacion") String uuidNotificacion,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            Date fechaActual =  new Date();

            if (uuidNotificacion != null ) {
                NotificacionDto notificacionDto = notificacionService.obtenerNotificacionPorUuid(uuidNotificacion);
           }

            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("subtitulo", "");
            parametros.put("usuario", nombreUsuario);
            parametros.put("fechaActual", fechaActual);
            parametros.put("uuidNotificacion", uuidNotificacion);
            generadorReporte.generarSqlReportePdf(
                    "reporte_notificacion",
                    "classpath:report/reporte_notificacion.jrxml",
                    parametros,
                    response
            );
            NotificacionDto notificacionDto = notificacionService.actualizarEstadoNotificacion(uuidNotificacion, EstadoNotificacion.ENVIADA);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    @RequestMapping( value = "/por-rangofechas", method= RequestMethod.GET)
    @ResponseBody
    public void generarDetalleNotificacionesPorRangoDeFechas(
            @RequestParam( name = "fechaInicio") Date fechaInicio,
            @RequestParam( name = "fechaFin") Date fechaFin,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("usuario", nombreUsuario);
            parametros.put("subtitulo", "");
            ZoneId zona = ZoneId.of(zonaHorario);
            Date fechaInicioAjustada = FechaUtil.ajustarFechaInicioDia(fechaInicio,zona);
            Date fechaFinAjustada = FechaUtil.ajustarFechaFinDia(fechaFin,zona);
            parametros.put("fechaInicial",new Timestamp(fechaInicioAjustada.getTime()));
            parametros.put("fechaFinal", new Timestamp(fechaFinAjustada.getTime()));
            generadorReporte.generarSqlReportePdf(
                    "reporte_mensual_notificacion",
                    "classpath:report/reporte_mensual_notificacion.jrxml",
                    parametros,
                    response
            );

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }



    @RequestMapping( value = "/certificado-opacidad", method= RequestMethod.GET)
    @ResponseBody
    public void generarReporteCertificadoOpacidad(
            @RequestParam( name = "uuid") String certificadoUuid,
            @RequestHeader Map<String, String> headers,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        String usuario = headers.getOrDefault("usuario", "Admin");
        String baseUrl = urlUtil.construirBaseUrl(request);
        certificadoService.generarReporteCertificadoOpacidad(certificadoUuid, usuario, baseUrl, response);
    }

    @RequestMapping( value = "/certificado-gnv", method= RequestMethod.GET)
    @ResponseBody
    public void generarReporteCertificadoGnv(
            @RequestParam( name = "uuid") String certificadoUuid,
            @RequestHeader Map<String, String> headers,
            HttpServletRequest request,
            HttpServletResponse response
    )
    {
        String usuario = headers.getOrDefault("usuario", "Admin");
        String baseUrl = urlUtil.construirBaseUrl(request);
        certificadoService.generarReporteCertificadoGnv(certificadoUuid, usuario, baseUrl, response);
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

    @RequestMapping( value = "/infraccion", method= RequestMethod.GET)
    @ResponseBody
    public void generarReporteMulta(
            @RequestParam( name = "uuid") String infraccionUuid,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            String lugarInspeccion="";
            Date fechaActual =  new Date();
            String nombreCompleto = "";

            if (!infraccionUuid.equalsIgnoreCase("0")) {
                InfraccionDto infraccionDto =  infraccionService.obtenerInfraccionPorUuid(infraccionUuid);
                lugarInspeccion = infraccionDto.getInspeccionDto().getLugarInspeccion();
                Optional<Inspeccion> inspeccion = inspeccionRepository.findByUuid(infraccionDto.getInspeccionDto().getUuid());
                nombreCompleto= NombreContribuyenteUtil.resolverNombreContribuyente(inspeccion.get());
            }

            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("usuario", nombreUsuario);
            parametros.put("subtitulo", "");
            parametros.put("lugarInspeccion", lugarInspeccion);
            parametros.put("fechaActual", fechaActual);
            parametros.put("uuidInfraccion", infraccionUuid);
            parametros.put("nombreCompleto", nombreCompleto);

            generadorReporte.generarSqlReportePdf(
                    "reporte_infraccion_municipal",
                    "classpath:report/reporte_infraccion_municipal.jrxml",
                    parametros,
                    response
            );
   //         InfraccionDto infraccionDto = infraccionService.actualizarStatusInfraccion(infraccionUuid, StatusInfraccion.NOTIFICADA);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    @RequestMapping( value = "/verificacion-tecnica", method= RequestMethod.GET)
    @ResponseBody
    public void generarReporteVerificacionTecnica(
            @RequestParam( name = "uuidVehiculo") String uuidVehiculo,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            String lugarInspeccion="";
            Date fechaActual =  new Date();
            String nombreCompleto = "";

            if (!uuidVehiculo.equalsIgnoreCase("0")) {
              //  InfraccionDto infraccionDto =  infraccionService.obtenerInfraccionPorUuid(infraccionUuid);
              //  lugarInspeccion = infraccionDto.getInspeccionDto().getLugarInspeccion();
               // Optional<Inspeccion> inspeccion = inspeccionRepository.findByUuid(infraccionDto.getInspeccionDto().getUuid());
             //   nombreCompleto= NombreContribuyenteUtil.resolverNombreContribuyente(inspeccion.get());
            }

            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("usuario", nombreUsuario);
            parametros.put("subtitulo", "");
            //parametros.put("lugarInspeccion", lugarInspeccion);
            parametros.put("fechaActual", fechaActual);
            parametros.put("uuidVehiculo", uuidVehiculo);
            //parametros.put("nombreCompleto", nombreCompleto);

            generadorReporte.generarSqlReportePdf(
                    "reporte_verificacion_tecnica",
                    "classpath:report/reporte_verificacion_tecnica.jrxml",
                    parametros,
                    response
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }

    @RequestMapping( value = "/infraccion-resumen", method= RequestMethod.GET)
    @ResponseBody
    public void generarResumenInfraccion(
         //   @RequestParam( name = "uuidVehiculo") String uuidVehiculo,
            @RequestHeader Map<String, String> headers,
            HttpServletResponse response
    )
    {
        try {
            String nombreUsuario = headers.getOrDefault("usuario", "Admin");
            HashMap<String, Object> parametros = new HashMap<String,Object>();
            String lugarInspeccion="";
            Date fechaActual =  new Date();
            String nombreCompleto = "";

           // if (!uuidVehiculo.equalsIgnoreCase("0")) {
                //  InfraccionDto infraccionDto =  infraccionService.obtenerInfraccionPorUuid(infraccionUuid);
                //  lugarInspeccion = infraccionDto.getInspeccionDto().getLugarInspeccion();
                // Optional<Inspeccion> inspeccion = inspeccionRepository.findByUuid(infraccionDto.getInspeccionDto().getUuid());
                //   nombreCompleto= NombreContribuyenteUtil.resolverNombreContribuyente(inspeccion.get());
           // }

            parametros.put("titulo", "UNIDAD DE MEDIO AMBIENTE");
            parametros.put("usuario", nombreUsuario);
            parametros.put("subtitulo", "");
            //parametros.put("lugarInspeccion", lugarInspeccion);
            parametros.put("fechaActual", fechaActual);
            //parametros.put("uuidVehiculo", uuidVehiculo);
            //parametros.put("nombreCompleto", nombreCompleto);

            generadorReporte.generarSqlReportePdf(
                    "reporte_resumen_recaudacion",
                    "classpath:report/reporte_resumen_recaudacion.jrxml",
                    parametros,
                    response
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }


}
