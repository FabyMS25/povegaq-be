package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ArchivoAdjuntoDto;
import com.gamq.ambiente.dto.RequisitoInspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.ArchivoAdjuntoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Adjuntar Archivos", description = "API REST para adjuntar archivos a los requisitos de inspeccion")
public class ArchivoAdjuntoController {
    @Autowired
    ArchivoAdjuntoService archivoAdjuntoService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Obtener los archivos Adjuntos por UUID", description = "Retorna los archivos adjuntos del requisito de inspeccion")
    public Response getArchivoAdjuntoByUuid(@PathVariable("uuid") String uuid)
    {
        return Response.ok().setPayload(archivoAdjuntoService.obtenerArchivoAdjuntoPorUuid(uuid));
    }

    @GetMapping("/uuidRequisitoInspeccion")
    @Operation(summary = "Obtener los archivos Adjuntos por uuid de requisito de inspeccion", description = "Retorna los archivos adjuntos del requisito de inspeccion")
    public Response getArchivoAdjuntos(@RequestParam("uuidRequisitoInspeccion") String uuidRequisitoInspeccion)
    {
        return Response.ok().setPayload(archivoAdjuntoService.obtenerArchivosAdjuntos(uuidRequisitoInspeccion));
    }

    @PostMapping("/create")
    @Operation(
            summary = "Crear nuevo archivo adjunto para un requisito de inspeccion",
            description = "Registra los archivos adjuntos para un requisito de inspeccion"
    )
    public ResponseEntity<Response<ArchivoAdjuntoDto>> createArchivoAdjunto(@Valid
                                         @RequestParam String uuidUsuario,
                                         @RequestParam String uuidRequisitoInspeccion,
                                         @RequestParam Date fechaAdjunto,
                                         @RequestParam String descripcion,
                                         MultipartFile archivoFile,
                                         @ModelAttribute ArchivoAdjuntoDto archivoAdjuntoDto)
    {
        archivoAdjuntoDto.setRequisitoInspeccionDto(new RequisitoInspeccionDto().setUuid(uuidRequisitoInspeccion));
        Response<ArchivoAdjuntoDto> response = Response.<ArchivoAdjuntoDto>created().setStatus(Status.OK).setPayload(archivoAdjuntoService.crearArchivoAdjunto(archivoAdjuntoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Modificar el archivo adjunto",
            description = "Modificar el archivo adjunto"
    )
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
    @Operation(
            summary = "Eliminar el archivo adjunto por uuid",
            description = "Eliminar el archivo adjunto por su uuid"
    )
    public Response deleteArchivoAdjunto(@PathVariable("uuid") String uuid)
    {
        archivoAdjuntoService.eliminarArchivoAdjunto(uuid);
        return Response.noContent().setPayload("El archivo adjunto fue eliminado exitosamente");
    }

    @GetMapping("/descargar/{fileName:.+}")
    @Operation(
            summary = "Descarga el archivo adjunto",
            description = "Descarga el archivo adjunto"
    )
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
