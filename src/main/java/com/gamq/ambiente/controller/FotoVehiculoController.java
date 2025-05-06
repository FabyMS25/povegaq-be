package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.FotoVehiculoDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.FotoVehiculoService;
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
public class FotoVehiculoController {
    @Autowired
    FotoVehiculoService fotoVehiculoService;

    @GetMapping("/uuid/{uuid}")
    public Response getFotoVehiculoByUuid(@PathVariable("uuid") String uuid)
    {
        return Response.ok().setPayload(fotoVehiculoService.obtenerFotoVehiculoPorUuid(uuid));
    }

    @GetMapping("/fotos")
    public Response getFotoVehiculos()
    {
        return Response.ok().setPayload(fotoVehiculoService.obtenerFotoVehiculos());
    }

    @PostMapping("/create")
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
    public Response updateFotoVehiculo(@Valid @RequestParam String uuid,
                                       MultipartFile archivoFile,
                                       @ModelAttribute FotoVehiculoDto fotoVehiculoDto){
        return Response.ok().setPayload(fotoVehiculoService.actualizarFotoVehiculo(fotoVehiculoDto));
    }

    @GetMapping("/descargar/{fileName:.+}")
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
    public Response deleteFotoVehiculo(@PathVariable("uuid") String uuid)
    {
        return Response.ok().setPayload(fotoVehiculoService.eliminarFotoVehiculo(uuid));
    }
}
