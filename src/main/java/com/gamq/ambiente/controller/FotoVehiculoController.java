package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.FotoVehiculoDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.FotoVehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("api/fotoVehiculo")
@Tag(name = "Foto Vehiculo", description = "API REST para gestión de las fotografias de los Vehiculos")
public class FotoVehiculoController {
    @Autowired
    FotoVehiculoService fotoVehiculoService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar Foto del Vehiculo por UUID", description = "Retorna los datos de la foto del vehiculo")
    public Response getFotoVehiculoByUuid(@PathVariable("uuid") String uuid)
    {
        return Response.ok().setPayload(fotoVehiculoService.obtenerFotoVehiculoPorUuid(uuid));
    }
    @GetMapping("/uuidVehiculo")
    @Operation(summary = "Buscar Foto del Vehiculo por uuid del vehiculo", description = "Retorna los datos de la foto del vehiculo")
    public Response getArchivoAdjuntos(@RequestParam("uuidVehiculo") String uuidVehiculo)
    {
        return Response.ok().setPayload(fotoVehiculoService.obtenerFotoVehiculoPorUuidVehiculo(uuidVehiculo));
    }

    @GetMapping("/fotos")
    @Operation(summary = "Listar las fotos de los vehiculos", description = "Obtiene la lista de fotos de los vehiculos")
    public Response getFotoVehiculos()
    {
        return Response.ok().setPayload(fotoVehiculoService.obtenerFotoVehiculos());
    }

    @PostMapping("/create")
    @Operation(
            summary = "Crear nueva Fotos del Vehiculo",
            description = "Registra los fotos de un Vehiculo"
    )
    public Response createFotoVehiculo(@Valid
                                       @RequestParam String uuidVehiculo,
                                       @RequestParam String uuidUsuario,
                                       MultipartFile archivoFile,
                                       @ModelAttribute FotoVehiculoDto fotoVehiculoDto)
    {

        fotoVehiculoDto.setVehiculoDto(new VehiculoDto().setUuid(uuidVehiculo));
        return Response.ok().setPayload(fotoVehiculoService.crearFotoVehiculo(fotoVehiculoDto));
    }

    @PutMapping("/update")
    @Operation(
            summary = "Modificar la Fotos del Vehiculo",
            description = "Modificar las fotos de un Vehiculo"
    )
    public Response updateFotoVehiculo(@Valid @RequestParam String uuid,
                                       MultipartFile archivoFile,
                                       @ModelAttribute FotoVehiculoDto fotoVehiculoDto){
        return Response.ok().setPayload(fotoVehiculoService.actualizarFotoVehiculo(fotoVehiculoDto));
    }

    @GetMapping("/descargar/{fileName:.+}")
    @Operation(
            summary = "Descarga la Foto del vehiculo",
            description = "Descarga la Foto del Vehiculo"
    )
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException
    {
        Resource file = fotoVehiculoService.descargarArchivo(fileName);
        String contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    @DeleteMapping("/delete/{uuid}")
    @Operation(
            summary = "Eliminar la Foto del Vehiculo por uuid",
            description = "Eliminar la foto del vehioculo por su uuid"
    )
    public Response deleteFotoVehiculo(@PathVariable("uuid") String uuid)
    {
        return Response.ok().setPayload(fotoVehiculoService.eliminarFotoVehiculo(uuid));
    }
}
