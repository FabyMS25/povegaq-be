package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.GrupoRiesgoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.GrupoRiesgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/grupoRiesgo")
public class GrupoRiesgoController {
    @Autowired
    GrupoRiesgoService grupoRiesgoService;

    @GetMapping("/uuid/{uuid}")
    public Response getGrupoRiesgoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(grupoRiesgoService.obtenerGrupoRiesgoPorUuid(uuid));
    }

    @GetMapping("/grupo/{grupo}")
    public Response getGrupoRiesgoByGrupo(@PathVariable("grupo") String grupo){
        return Response.ok().setPayload(grupoRiesgoService.obtenerGrupoRiesgoPorGrupo(grupo));
    }

    @GetMapping()
    public Response getAllGrupoRiesgos(){
        return Response.ok().setPayload(grupoRiesgoService.obtenerGrupoRiesgos());
    }

    @GetMapping("/uuidCategoriaAire/{uuidCategoriaAire}")
    public Response getGrupoRiesgoByUuidCategoriaAire(@PathVariable("uuidCategoriaAire") String uuidCategoriaAire){
        return Response.ok().setPayload(grupoRiesgoService.obtenerGrupoRiesgoPorUuidCategoriaAire(uuidCategoriaAire));
    }

    @PostMapping()
    public Response createGrupoRiesgo(@Valid @RequestBody GrupoRiesgoDto grupoRiesgoDto){
        return Response.ok().setPayload(grupoRiesgoService.crearGrupoRiesgo(grupoRiesgoDto));
    }

    @PutMapping()
    public Response updateGrupoRiesgo(@Valid @RequestBody GrupoRiesgoDto GrupoRiesgoDto){
        return Response.ok().setPayload(grupoRiesgoService.actualizarGrupoRiesgo(GrupoRiesgoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteGrupoRiesgo(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(grupoRiesgoService.eliminarGrupoRiesgo(uuid));
    }
}
