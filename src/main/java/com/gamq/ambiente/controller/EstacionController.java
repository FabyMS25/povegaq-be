package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.EstacionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.EstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/estacion")
public class EstacionController {
    @Autowired
    EstacionService estacionService;

    @GetMapping("/uuid/{uuid}")
    public Response getEstacionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(estacionService.obtenerEstacionPorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getEstacionByNombre(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(estacionService.obtenerEstacionPorNombre(nombre));
    }

    @GetMapping()
    public Response getAllEstaciones(){
        return Response.ok().setPayload(estacionService.obtenerEstaciones());
    }

    @PostMapping()
    public Response createEstacion(@Valid @RequestBody EstacionDto estacionDto){
        return Response.ok().setPayload(estacionService.crearEstacion(estacionDto));
    }

    @PutMapping()
    public Response updateEstacion(@Valid @RequestBody EstacionDto EstacionDto){
        return Response.ok().setPayload(estacionService.actualizarEstacion(EstacionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteEstacion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(estacionService.eliminarEstacion(uuid));
    }
}
