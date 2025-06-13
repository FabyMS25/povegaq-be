package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.EquipoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/equipo")
public class EquipoController {
    @Autowired
    EquipoService equipoService;

    @GetMapping("/uuid/{uuid}")
    public Response getEquipoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(equipoService.obtenerEquipoPorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getEquipoByNombre(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(equipoService.obtenerEquipoPorNombre(nombre));
    }

    @GetMapping()
    public Response getAllEquipos(){
        return Response.ok().setPayload(equipoService.obtenerEquipos());
    }

    @PostMapping()
    public Response createEquipo(@Valid @RequestBody EquipoDto equipoDto){
        return Response.ok().setPayload(equipoService.crearEquipo(equipoDto));
    }

    @PutMapping()
    public Response updateEquipo(@Valid @RequestBody EquipoDto EquipoDto){
        return Response.ok().setPayload(equipoService.actualizarEquipo(EquipoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteEquipo(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(equipoService.eliminarEquipo(uuid));
    }
}
