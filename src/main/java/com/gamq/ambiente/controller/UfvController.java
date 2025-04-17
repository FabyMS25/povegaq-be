package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.UfvDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.UfvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/ufv")
public class UfvController {
    @Autowired
    UfvService ufvService;

    @GetMapping("/uuid/{uuid}")
    public Response getUfvByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(ufvService.obtenerUfvPorUuid(uuid));
    }

    @GetMapping("/fecha/{fecha}")
    public Response getUfvByFecha(@PathVariable("fecha") Date fecha){
        return Response.ok().setPayload(ufvService.obtenerUfvPorFecha(fecha));
    }

    @GetMapping()
    public Response getAllUfves(){
        return Response.ok().setPayload(ufvService.obtenerUfves());
    }

    @PostMapping()
    public Response createUfv(@Valid @RequestBody UfvDto ufvDto){
        return Response.ok().setPayload(ufvService.crearUfv(ufvDto));
    }

    @PutMapping()
    public Response updateUfv(@Valid @RequestBody UfvDto ufvDto){
        return Response.ok().setPayload(ufvService.actualizarUfv(ufvDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteUfv(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(ufvService.eliminarUfv(uuid));
    }
}
