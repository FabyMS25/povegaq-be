package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ConductorDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/conductor")
public class ConductorController {
    @Autowired
    ConductorService conductorService;

    @GetMapping("/uuid/{uuid}")
    public Response getConductorByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(conductorService.obtenerConductorPorUuid(uuid));
    }

    @GetMapping("/numeroDocumento/{numeroDocumento}")
    public Response getConductorByNumeroDocumento(@PathVariable("numeroDocumento") String numeroDocumento){
        return Response.ok().setPayload(conductorService.obtenerConductorPorNumeroDocumento(numeroDocumento));
    }

    @GetMapping()
    public Response getAllConductores(){
        return Response.ok().setPayload(conductorService.obtenerConductores());
    }

    @PostMapping()
    public Response createConductor(@Valid @RequestBody ConductorDto conductorDto){
        return Response.ok().setPayload(conductorService.crearConductor(conductorDto));
    }

    @PutMapping()
    public Response updateConductor(@Valid @RequestBody ConductorDto conductorDto){
        return Response.ok().setPayload(conductorService.actualizarConductor(conductorDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteConductor(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(conductorService.eliminarConductor(uuid));
    }
}
