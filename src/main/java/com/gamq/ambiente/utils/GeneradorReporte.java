package com.gamq.ambiente.utils;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@Component("generadorReporteComponent")
public class GeneradorReporte {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     *
     * @param nombreArchivo para el archivo
     * @param pathReporte del archivo jrxml
     * @param parametros del reporte
     * @param response para devolver el archivo
     * @throws SQLException
     */
    public void generarSqlReportePdf(
            String nombreArchivo,
            String pathReporte,
            HashMap<String, Object> parametros,
            HttpServletResponse response
    ) throws SQLException
    {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            //load report master
            Resource resource = resourceLoader.getResource(pathReporte);
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            //load logo
            String logo64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";
            //Resource logoResource = resourceLoader.getResource("classpath:imagenes/gamq1.jpg");
            Resource logoResource = resourceLoader.getResource("classpath:image/gamq1.jpg");
            if(logoResource.exists()) {
                logo64 = new String(Base64.encodeBase64(logoResource.getInputStream().readAllBytes()), "UTF-8");
            }
            parametros.put("logo64", logo64);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,conn);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename="+nombreArchivo+".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException jrException) {
            System.out.println(jrException.getMessage());
            System.out.println(jrException.getLocalizedMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getLocalizedMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.out.println(sqlException.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        } finally {
            conn.close();
        }
    }

    /**
     *
     * @param nombreArchivo para el archivo
     * @param pathReporte del archivo jrxml
     * @param parametros del reporte
     * @param response para devolver el archivo
     * @throws SQLException
     */
    public void generarSqlReporteWord(
            String nombreArchivo,
            String pathReporte,
            HashMap<String, Object> parametros,
            HttpServletResponse response
    ) throws SQLException
    {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Resource resource = resourceLoader.getResource(pathReporte);
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            String logo64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";
            Resource logoResource = resourceLoader.getResource("classpath:image/gamq1.jpg");
            if(logoResource.exists()) {
                logo64 = new String(Base64.encodeBase64(logoResource.getInputStream().readAllBytes()), "UTF-8");
            }
            parametros.put("logo64", logo64);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,conn);

            response.setContentType("application/msword");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".docx");
            JRDocxExporter exporter = new JRDocxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
            configuration.setFlexibleRowHeight(true);
            SimpleDocxExporterConfiguration configurationExport = new SimpleDocxExporterConfiguration();
            configurationExport.setMetadataAuthor("GAMQ");
            configurationExport.setMetadataTitle(nombreArchivo);
            exporter.setConfiguration(configuration);
            exporter.setConfiguration(configurationExport);
            exporter.exportReport();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException jrException) {
            System.out.println(jrException.getMessage());
            System.out.println(jrException.getLocalizedMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getLocalizedMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.out.println(sqlException.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        } finally {
            conn.close();
        }
    }

    /**
     *
     * @param nombreArchivo para el archivo
     * @param pathReporte del archivo jrxml
     * @param parametros del reporte
     * @param response para devolver el archivo
     * @throws SQLException
     */
    public void generarSqlReporteExcel(
            String nombreArchivo,
            String pathReporte,
            HashMap<String, Object> parametros,
            HttpServletResponse response
    ) throws SQLException
    {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            Resource resource = resourceLoader.getResource(pathReporte);
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            String logo64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";
            Resource logoResource = resourceLoader.getResource("classpath:image/gamq1.jpg");
            if(logoResource.exists()) {
                logo64 = new String(Base64.encodeBase64(logoResource.getInputStream().readAllBytes()), "UTF-8");
            }
            parametros.put("logo64", logo64);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,conn);

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".xlsx");
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            configuration.setWhitePageBackground(false);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException jrException) {
            System.out.println(jrException.getMessage());
            System.out.println(jrException.getLocalizedMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getLocalizedMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.out.println(sqlException.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        } finally {
            conn.close();
        }
    }

    public void generarJsonReportePdf(
            String nombreArchivo,
            String pathJson,
            String pathReporteMain,
            String nameSubReport,
            HashMap<String, Object> parametros,
            HttpServletResponse response
    ) throws SQLException
    {
        try {
            //load json data
            Resource resource = resourceLoader.getResource(pathJson);
            InputStream inputStream = resource.getInputStream();

            JRDataSource jsonDataSource = new JsonDataSource(inputStream);

            if(!nameSubReport.equals("")) {
                String rutaCompleta = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/report/";
                String rutaJasper = rutaCompleta + nameSubReport.replace("jrxml", "jasper");// "material.jasper";
                rutaCompleta = rutaCompleta + nameSubReport;
                JasperCompileManager.compileReportToFile(rutaCompleta, rutaJasper);
                parametros.put("subReportParameterMat", rutaJasper);
            }

            //load report master
            resource = resourceLoader.getResource(pathReporteMain);
            inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            //load logo
            String logo64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";
            Resource logoResource = resourceLoader.getResource("classpath:image/gamq1.jpg");
            if(logoResource.exists()) {
                logo64 = new String(Base64.encodeBase64(logoResource.getInputStream().readAllBytes()), "UTF-8");
            }
            parametros.put("logo64", logo64);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, jsonDataSource);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename="+nombreArchivo+".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException jrException) {
            System.out.println(jrException.getMessage());
            System.out.println(jrException.getLocalizedMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        } finally {
        }
    }

    /**
     *
     * @param nombreArchivo para el archivo
     * @param pathReporte del archivo jrxml
     * @param pathSubReporteMaterial del archivo jrxml
     * @param parametros del reporte
     * @param response para devolver el archivo
     * @throws SQLException
     */
    public void generarSqlReporteAndSubReportePdf(
            String nombreArchivo,
            String pathReporte,
            String pathSubReporteMaterial,
            String pathSubReporteMano,
            String pathSubReporteHerramienta,
            HashMap<String, Object> parametros,
            HttpServletResponse response
    ) throws SQLException
    {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            //load report master
            Resource resource = resourceLoader.getResource(pathReporte);
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            //load sub report 1
            resource = resourceLoader.getResource(pathSubReporteMaterial);
            inputStream =resource.getInputStream();
            JasperReport jasperSubReport = JasperCompileManager.compileReport(inputStream);
            parametros.put("subreportParameterMaterial", jasperSubReport);
            parametros.put("codigoMA","MA");

            //load sub report 2
            resource = resourceLoader.getResource(pathSubReporteMano);
            inputStream =resource.getInputStream();
            jasperSubReport = JasperCompileManager.compileReport(inputStream);
            parametros.put("subreportParameterMano", jasperSubReport);
            parametros.put("codigoMO","MO");

            //load sub report 3
            resource = resourceLoader.getResource(pathSubReporteHerramienta);
            inputStream =resource.getInputStream();
            jasperSubReport = JasperCompileManager.compileReport(inputStream);
            parametros.put("subreportParameterHerramienta", jasperSubReport);
            parametros.put("codigoEQ","EQ");

            //load logo
            String logo64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";
            Resource logoResource = resourceLoader.getResource("classpath:image/gamq1.jpg");
            if(logoResource.exists()) {
                logo64 = new String(Base64.encodeBase64(logoResource.getInputStream().readAllBytes()), "UTF-8");
            }
            parametros.put("logo64", logo64);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,conn);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename="+nombreArchivo+".pdf");
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException jrException) {
            System.out.println(jrException.getMessage());
            System.out.println(jrException.getLocalizedMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getLocalizedMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.out.println(sqlException.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        } finally {
            conn.close();
        }
    }
    /**
     *
     * @param nombreArchivo para el archivo
     * @param pathReporte del archivo jrxml
     * @param parametros del reporte
     * @param response para devolver el archivo
     * @throws SQLException
     */
    public void generarSqlReporteAndSubReportExcel(
            String nombreArchivo,
            String pathReporte,
            String pathSubReporteMaterial,
            String pathSubReporteMano,
            String pathSubReporteHerramienta,
            HashMap<String, Object> parametros,
            HttpServletResponse response
    ) throws SQLException
    {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            //load report master
            Resource resource = resourceLoader.getResource(pathReporte);
            InputStream inputStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

            //load sub report 1
            resource =resourceLoader.getResource(pathSubReporteMaterial);
            inputStream = resource.getInputStream();
            JasperReport jasperSubReport = JasperCompileManager.compileReport(inputStream);
            parametros.put("subreportParameterMaterial", jasperSubReport);
            parametros.put("codigoMA", "MA");

            //load sub report 2
            resource = resourceLoader.getResource(pathSubReporteMano);
            inputStream =resource.getInputStream();
            jasperSubReport = JasperCompileManager.compileReport(inputStream);
            parametros.put("subreportParameterMano", jasperSubReport);
            parametros.put("codigoMO","MO");

            //load sub report 3
            resource = resourceLoader.getResource(pathSubReporteHerramienta);
            inputStream =resource.getInputStream();
            jasperSubReport = JasperCompileManager.compileReport(inputStream);
            parametros.put("subreportParameterHerramienta", jasperSubReport);
            parametros.put("codigoEQ","EQ");

            //load logo
            String logo64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=";
            Resource logoResource = resourceLoader.getResource("classpath:image/gamq1.jpg");
            if(logoResource.exists()) {
                logo64 = new String(Base64.encodeBase64(logoResource.getInputStream().readAllBytes()), "UTF-8");
            }
            parametros.put("logo64", logo64);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros,conn);

            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo + ".xlsx");
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
            SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
            configuration.setOnePagePerSheet(false);
            configuration.setDetectCellType(true);
            configuration.setCollapseRowSpan(false);
            configuration.setWhitePageBackground(false);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (JRException jrException) {
            System.out.println(jrException.getMessage());
            System.out.println(jrException.getLocalizedMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.out.println(ioException.getLocalizedMessage());
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            System.out.println(sqlException.getLocalizedMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        } finally {
            conn.close();
        }
    }

}


