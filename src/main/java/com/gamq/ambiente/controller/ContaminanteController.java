package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ContaminanteDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.ContaminanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/contaminante")
public class ContaminanteController {
    @Autowired
    ContaminanteService contaminanteService;

    @GetMapping("/uuid/{uuid}")
    public Response getContaminanteByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(contaminanteService.obtenerContaminantePorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getContaminanteByNombre(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(contaminanteService.obtenerContaminantePorNombre(nombre));
    }

    @GetMapping()
    public Response getAllContaminantes(){
        return Response.ok().setPayload(contaminanteService.obtenerContaminantes());
    }

    @PostMapping()
    public Response createContaminante(@Valid @RequestBody ContaminanteDto contaminanteDto){
        return Response.ok().setPayload(contaminanteService.crearContaminante(contaminanteDto));
    }

    @PutMapping()
    public Response updateContaminante(@Valid @RequestBody ContaminanteDto ContaminanteDto){
        return Response.ok().setPayload(contaminanteService.actualizarContaminante(ContaminanteDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteContaminante(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(contaminanteService.eliminarContaminante(uuid));
    }
}
