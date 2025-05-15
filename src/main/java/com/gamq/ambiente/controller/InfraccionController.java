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

    @GetMapping("/fecha/{fecha}")
    public Response getInfraccionByDescripcion(@PathVariable("fecha") Date fecha){
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

    @PutMapping()
    public Response updateInfraccion(@Valid @RequestBody InfraccionDto infraccionDto){
        return Response.ok().setPayload(infraccionService.actualizarInfraccion(infraccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteInfraccion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(infraccionService.eliminarInfraccion(uuid));
    }
}


