package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ArchivoAdjuntoDto;
import com.gamq.ambiente.dto.RequisitoInspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.ArchivoAdjuntoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@RestController
@RequestMapping("api/archivo-adjunto")
public class ArchivoAdjuntoController {
    @Autowired
    ArchivoAdjuntoService archivoAdjuntoService;

    @GetMapping("/uuid/{uuid}")
    public Response getArchivoAdjuntoByUuid(@PathVariable("uuid") String uuid)
    {
        return Response.ok().setPayload(archivoAdjuntoService.obtenerArchivoAdjuntoPorUuid(uuid));
    }

    @GetMapping("/uuidRequisitoInspeccion")
    public Response getArchivoAdjuntos(@RequestParam("uuidRequisitoInspeccion") String uuidRequisitoInspeccion)
    {
        return Response.ok().setPayload(archivoAdjuntoService.obtenerArchivosAdjuntos(uuidRequisitoInspeccion));
    }

    @PostMapping("/create")
    public Response createArchivoAdjunto(@Valid
                                         @RequestParam String uuidUsuario,
                                         @RequestParam String uuidRequisitoInspeccion,
                                         @RequestParam Date fechaAdjunto,
                                         @RequestParam String descripcion,
                                         MultipartFile archivoFile,
                                         @ModelAttribute ArchivoAdjuntoDto archivoAdjuntoDto)
    {
        archivoAdjuntoDto.setRequisitoInspeccionDto(new RequisitoInspeccionDto().setUuid(uuidRequisitoInspeccion));
        return Response.ok().setPayload(archivoAdjuntoService.crearArchivoAdjunto(archivoAdjuntoDto));
    }

    @PutMapping("/update")
    public Response updateArchivoAdjunto(@RequestParam String uuid,
                                         @RequestParam String uuidUsuario,
                                         @RequestParam Date fechaAdjunto,
                                         @RequestParam String descripcion,
                                         MultipartFile archivoFile,
                                         @ModelAttribute ArchivoAdjuntoDto archivoAdjuntoDto)
    {
        return  Response.ok().setPayload(archivoAdjuntoService.actualizarArchivoAdjunto(archivoAdjuntoDto));
    }

    @DeleteMapping("/delete/{uuid}")
    public Response deleteArchivoAdjunto(@PathVariable("uuid") String uuid)
    {
        return Response.ok().setPayload(archivoAdjuntoService.eliminarArchivoAdjunto(uuid));
    }

    @GetMapping("/descargar/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName)  throws IOException
    {
        Resource file = archivoAdjuntoService.descargarArchivo(fileName);

        /* funciona word , excel, pdf, png, jpg
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.getFile().toPath()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                        .body(file);
        */
        // funciona png, jpg, pdf,
        String  contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @GetMapping("/descargar-office/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFileOffice(@PathVariable String fileName)  throws IOException
    {
        Resource file = archivoAdjuntoService.descargarArchivo(fileName);
        String  contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
