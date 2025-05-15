package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.InfraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/infraccion")
public class InfraccionController {
    @Autowired
    InfraccionService infraccionService;

    @GetMapping("/uuid/{uuid}")
    public Response getInfraccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(infraccionService.obtenerInfraccionPorUuid(uuid));
    }

    @GetMapping("/vehiculo/{uuidVehiculo}")
    public Response getInfraccionByVehiculo(@PathVariable("uuidVehiculo") String uuidVehiculo) {
        return Response.ok().setPayload(infraccionService.obtenerInfraccionPorVehiculo(uuidVehiculo));
    }
    @GetMapping("/fecha/{fecha}")
    public Response getInfraccionByFecha(@PathVariable("fecha") Date fecha){
        return Response.ok().setPayload(infraccionService.obtenerInfraccionPorFecha(fecha));
    }

    @GetMapping()
    public Response getAllInfracciones(){
        return Response.ok().setPayload(infraccionService.obtenerInfracciones());
    }

    @PostMapping()
    public Response createInfraccion(@Valid @RequestBody InfraccionDto infraccionDto){
        return Response.ok().setPayload(infraccionService.crearInfraccion(infraccionDto));
    }

    @PostMapping("/generar/{uuidInspeccion}")
    public Response generarInfraccion(@PathVariable String uuidInspeccion) {
        return Response.ok().setPayload(infraccionService.generarInfraccion(uuidInspeccion));
    }

    @PutMapping()
    public Response updateInfraccion(@Valid @RequestBody InfraccionDto infraccionDto){
        return Response.ok().setPayload(infraccionService.actualizarInfraccion(infraccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteInfraccion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(infraccionService.eliminarInfraccion(uuid));
    }

  }


