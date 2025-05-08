package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.PropietarioDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/propietario")
public class PropietarioController {
    @Autowired
    PropietarioService propietarioService;

    @GetMapping("/uuid/{uuid}")
    public Response getPropietarioByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(propietarioService.obtenerPropietarioPorUuid(uuid));
    }

    @GetMapping
    public Response getPropietarios() {
        return Response.ok().setPayload(propietarioService.obtenerPropietarios());
    }

    @PostMapping()
    public Response createPropietario(@Valid @RequestBody PropietarioDto propietarioDto) {
        return Response.ok().setPayload(propietarioService.crearPropietario(propietarioDto));
    }

    @PutMapping()
    public Response updatePropietario(@Valid @RequestBody PropietarioDto propietarioDto) {
        return Response.ok().setPayload(propietarioService.actualizarPropietario(propietarioDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deletePropietario(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(propietarioService.eliminarPropietario(uuid));
    }
}
