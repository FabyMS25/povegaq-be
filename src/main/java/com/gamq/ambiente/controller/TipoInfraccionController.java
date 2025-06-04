package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.TipoInfraccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.service.TipoInfraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tipoInfraccion")
public class TipoInfraccionController {
    @Autowired
    TipoInfraccionService tipoInfraccionService;

    @GetMapping("/uuid/{uuid}")
    public Response getTipoInfraccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(tipoInfraccionService.obtenerTipoInfraccionPorUuid(uuid));
    }

    @GetMapping("/grado/{grado}")
    public Response getTipoInfraccionByGrado(@PathVariable("grado") GradoInfraccion grado){
        return Response.ok().setPayload(tipoInfraccionService.obtenerTipoInfraccionPorGrado(grado));
    }

    @GetMapping()
    public Response getAllTipoInfraccions(){
        return Response.ok().setPayload(tipoInfraccionService.obtenerTipoInfracciones());
    }

    @PostMapping()
    public Response createTipoInfraccion(@Valid @RequestBody TipoInfraccionDto TipoInfraccionDto){
        return Response.ok().setPayload(tipoInfraccionService.crearTipoInfraccion(TipoInfraccionDto));
    }

    @PutMapping()
    public Response updateTipoInfraccion(@Valid @RequestBody TipoInfraccionDto TipoInfraccionDto){
        return Response.ok().setPayload(tipoInfraccionService.actualizarTipoInfraccion(TipoInfraccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteTipoInfraccion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(tipoInfraccionService.eliminarTipoInfraccion(uuid));
    }
}
