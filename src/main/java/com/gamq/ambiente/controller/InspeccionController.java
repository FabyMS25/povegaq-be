package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.InspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/inspeccion")
public class InspeccionController {
    @Autowired
    InspeccionService inspeccionService;

    @GetMapping("/uuid/{uuid}")
    public Response getInspeccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorUuid(uuid));
    }

    @GetMapping("/placa/{placa}")
    public Response getInspeccionByPlaca(@PathVariable("placa") String placa){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorPlaca(placa));
    }

    @GetMapping("/uuidUsuario/{uuidUsuario}")
    public Response getInspeccionByUuidUsuario(@PathVariable("uuidUsuario") String uuidUsuario){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorUuidUsuario(uuidUsuario));
    }

    @GetMapping("/uuidActividad/{uuidActividad}")
    public Response getInspeccionByUuidActividad(@PathVariable("uuidActividad") String uuidActividad){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorUuidActividad(uuidActividad));
    }

    @GetMapping("/fechaInspeccion")
    public Response getInspeccionByFechaInspeccion(@RequestParam Date fechaInspeccion){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorFechaInspeccion(fechaInspeccion));
    }

    @GetMapping()
    public Response getAllInspeccions(){
        return Response.ok().setPayload(inspeccionService.obtenerInspecciones());
    }

    @PostMapping()
    public Response createInspeccion(@Valid @RequestBody InspeccionDto inspeccionDto){
        return Response.ok().setPayload(inspeccionService.crearInspeccion(inspeccionDto));
    }

    @PutMapping()
    public Response updateInspeccion(@Valid @RequestBody InspeccionDto inspeccionDto){
        return Response.ok().setPayload(inspeccionService.actualizarInspeccion(inspeccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteInspeccion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(inspeccionService.eliminarInspeccion(uuid));
    }

}
