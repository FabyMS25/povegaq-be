package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.VehiculoConductorInspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.VehiculoConductorInspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vehiculo-conductor-inspeccion")
public class VehiculoConductorInspeccionController {
    @Autowired
    VehiculoConductorInspeccionService vehiculoConductorInspeccionService;
    @GetMapping("/uuid/{uuid}")
    public Response getVehiculoConductorInspeccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(vehiculoConductorInspeccionService.obtenerVehiculoConductorInspeccionPorUuid(uuid));
    }

    @GetMapping("/uuidInspeccion/{uuidInspeccion}")
    public Response getVehiculoConductorInspeccionByDescripcion(@PathVariable("uuidInspeccion") String uuidInspeccion){
        return Response.ok().setPayload(vehiculoConductorInspeccionService.obtenerVehiculoConductorInspeccionPorUuidInspeccion(uuidInspeccion));
    }

    @GetMapping()
    public Response getAllVehiculoConductorInspecciones(){
        return Response.ok().setPayload(vehiculoConductorInspeccionService.obtenerVehiculoConductorInspecciones());
    }

    @PostMapping()
    public Response createVehiculoConductorInspeccion(@Valid @RequestBody VehiculoConductorInspeccionDto vehiculoConductorInspeccionDto){
        return Response.ok().setPayload(vehiculoConductorInspeccionService.crearVehiculoConductorInspeccion(vehiculoConductorInspeccionDto));
    }

    @PutMapping()
    public Response updateVehiculoConductorInspeccion(@Valid @RequestBody VehiculoConductorInspeccionDto vehiculoConductorInspeccionDto){
        return Response.ok().setPayload(vehiculoConductorInspeccionService.actualizarVehiculoConductorInspeccion(vehiculoConductorInspeccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteVehiculoConductorInspeccion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(vehiculoConductorInspeccionService.eliminarVehiculoConductorInspeccion(uuid));
    }
}
