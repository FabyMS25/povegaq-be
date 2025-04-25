package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.InspeccionRequisitoInspeccionDto;
import com.gamq.ambiente.dto.RequisitoInspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.RequisitoInspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/requisitoInspeccion")
public class RequisitoInspeccionController {
    @Autowired
    RequisitoInspeccionService requisitoInspeccionService;

    @GetMapping("/uuid/{uuid}")
    public Response getRequisitoInspeccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(requisitoInspeccionService.obtenerRequisitoInspeccionPorUuid(uuid));
    }

    @GetMapping("/uuidInspeccion/{uuidInspeccion}")
    public Response getRequisitoInspeccionByUuidInspeccion(@PathVariable("uuidInspeccion") String uuidInspeccion){
        return Response.ok().setPayload(requisitoInspeccionService.obtenerRequisitoInspeccionPorUuidInspeccion(uuidInspeccion));
    }

    @GetMapping()
    public Response getAllRequisitoInspeccions(){
        return Response.ok().setPayload(requisitoInspeccionService.obtenerRequisitoInspecciones());
    }

    @PostMapping()
    public Response createRequisitoInspeccion(@Valid @RequestBody RequisitoInspeccionDto requisitoInspeccionDto){
        return Response.ok().setPayload(requisitoInspeccionService.crearRequisitoInspeccion(requisitoInspeccionDto));
    }

    @PutMapping()
    public Response updateRequisitoInspeccion(@Valid @RequestBody RequisitoInspeccionDto requisitoInspeccionDto){
        return Response.ok().setPayload(requisitoInspeccionService.actualizarRequisitoInspeccion(requisitoInspeccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteRequisitoInspeccion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(requisitoInspeccionService.eliminarRequisitoInspeccion(uuid));
    }

    @PutMapping("/add-requisito-inspecciones")
    public Response addRequisitoToInspeccion(@RequestBody InspeccionRequisitoInspeccionDto inspeccionRequisitoInspeccionDto){
        return Response.ok().setPayload(requisitoInspeccionService.addRequisitoInspeccionToInspeccion(inspeccionRequisitoInspeccionDto));
    }
}
