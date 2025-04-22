package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDetalleInspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.DetalleInspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/detalleInspeccion")
public class DetalleInspeccionController {
    @Autowired
    DetalleInspeccionService detalleInspeccionService;

    @GetMapping("/uuid/{uuid}")
    public Response getDetalleInspeccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(detalleInspeccionService.obtenerDetalleInspeccionPorUuid(uuid));
    }

    @GetMapping()
    public Response getAllDetalleInspecciones(){
        return Response.ok().setPayload(detalleInspeccionService.obtenerDetalleInspecciones());
    }

    @PostMapping()
    public Response createDetalleInspeccion(@Valid @RequestBody DetalleInspeccionDto detalleInspeccionDto){
        return Response.ok().setPayload(detalleInspeccionService.crearDetalleInspeccion(detalleInspeccionDto));
    }

    @PutMapping()
    public Response updateDetalleInspeccion(@Valid @RequestBody DetalleInspeccionDto detalleInspeccionDto){
        return Response.ok().setPayload(detalleInspeccionService.actualizarDetalleInspeccion(detalleInspeccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteDetalleInspeccion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(detalleInspeccionService.eliminarDetalleInspeccion(uuid));
    }

    @PutMapping("/add-detalle-inspecciones")
    public Response addDetalleInspeccionesToInpeccion(@RequestBody InspeccionDetalleInspeccionDto inspeccionDetalleInspeccionDto){
        return Response.ok().setPayload(detalleInspeccionService.addDetalleInspecionToInspeccion(inspeccionDetalleInspeccionDto));
    }

    @GetMapping("/uuidInspeccion/{uuidInspeccion}")
    public Response getDetalleInspeccionByUuidInspeccion(@PathVariable("uuidInspeccion") String uuidInspeccion){
        return Response.ok().setPayload(detalleInspeccionService.obtenerDetalleInspeccionPorUuidInspeccion(uuidInspeccion));
    }
}
