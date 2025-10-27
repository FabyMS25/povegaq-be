package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.MedicionAireDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.MedicionAireService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/medicionAire")
@Tag(name = "Medicion de Aire", description = "API REST para gestionar mediciones de aire")
public class MedicionAireController {
    @Autowired
    MedicionAireService medicionAireService;

    @GetMapping("/uuid/{uuid}")
    public Response getMedicionAireByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(medicionAireService.obtenerMedicionAirePorUuid(uuid));
    }

    @GetMapping("/fecha")
    public Response getMedicionAireByFecha(@RequestParam("fecha") Date fecha){
        return Response.ok().setPayload(medicionAireService.obtenerMedicionAirePorFecha(fecha));
    }

    @GetMapping()
    public Response getAllMedicionAires(){
        return Response.ok().setPayload(medicionAireService.obtenerMedicionesAire());
    }

    @PostMapping()
    public ResponseEntity<Response<MedicionAireDto>> createMedicionAire(@Valid @RequestBody MedicionAireDto medicionAireDto){
        Response<MedicionAireDto> response = Response.<MedicionAireDto>created().setStatus(Status.OK).setPayload(medicionAireService.crearMedicionAire(medicionAireDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/masivo")
    public ResponseEntity<Response<List<MedicionAireDto>>> crearMedicionesMasivo(@RequestBody List<MedicionAireDto> medicionesDto) {
        Response<List<MedicionAireDto>> response = Response.<List<MedicionAireDto>>created().setStatus(Status.OK).setPayload(medicionAireService.crearMedicionesAireMasivo(medicionesDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateMedicionAire(@Valid @RequestBody MedicionAireDto MedicionAireDto){
        return Response.ok().setPayload(medicionAireService.actualizarMedicionAire(MedicionAireDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteMedicionAire(@PathVariable("uuid") String uuid){
        medicionAireService.eliminarMedicionAire(uuid);
        return Response.noContent().setPayload("LaMedicion del Aire fue eliminado exitosamente");
    }
}
