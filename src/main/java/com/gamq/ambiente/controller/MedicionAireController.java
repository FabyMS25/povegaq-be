package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.MedicionAireDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.MedicionAireService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response createMedicionAire(@Valid @RequestBody MedicionAireDto medicionAireDto){
        return Response.ok().setPayload(medicionAireService.crearMedicionAire(medicionAireDto));
    }

    @PostMapping("/masivo")
    public Response crearMedicionesMasivo(@RequestBody List<MedicionAireDto> medicionesDto) {
        return Response.ok().setPayload(medicionAireService.crearMedicionesAireMasivo(medicionesDto));
    }

    @PutMapping()
    public Response updateMedicionAire(@Valid @RequestBody MedicionAireDto MedicionAireDto){
        return Response.ok().setPayload(medicionAireService.actualizarMedicionAire(MedicionAireDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteMedicionAire(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(medicionAireService.eliminarMedicionAire(uuid));
    }
}
