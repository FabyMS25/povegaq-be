package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.InspeccionRequisitoInspeccionDto;
import com.gamq.ambiente.dto.RequisitoInspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.RequisitoInspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Response<RequisitoInspeccionDto>> createRequisitoInspeccion(@Valid @RequestBody RequisitoInspeccionDto requisitoInspeccionDto){
        Response<RequisitoInspeccionDto> response = Response.<RequisitoInspeccionDto>created().setStatus(Status.OK).setPayload(requisitoInspeccionService.crearRequisitoInspeccion(requisitoInspeccionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateRequisitoInspeccion(@Valid @RequestBody RequisitoInspeccionDto requisitoInspeccionDto){
        return Response.ok().setPayload(requisitoInspeccionService.actualizarRequisitoInspeccion(requisitoInspeccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteRequisitoInspeccion(@PathVariable("uuid") String uuid){
        requisitoInspeccionService.eliminarRequisitoInspeccion(uuid);
        return Response.noContent().setPayload("El Requisito de Inspeccion fue eliminado exitosamente");
    }

    @PutMapping("/add-requisito-inspecciones")
    public Response addRequisitoToInspeccion(@RequestBody InspeccionRequisitoInspeccionDto inspeccionRequisitoInspeccionDto){
        return Response.ok().setPayload(requisitoInspeccionService.addRequisitoInspeccionToInspeccion(inspeccionRequisitoInspeccionDto));
    }
}
