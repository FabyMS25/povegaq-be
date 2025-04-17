package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.EventoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/evento")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @GetMapping("/uuid/{uuid}")
    public Response getEventoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(eventoService.obtenerEventoPorUuid(uuid));
    }

    @GetMapping("/institucion/{institucion}")
    public Response getEventoByInstitucion(@PathVariable("institucion") String institucion){
        return Response.ok().setPayload(eventoService.obtenerEventoPorInstitucion(institucion));
    }

    @GetMapping("/fecha")
    public Response getEventoAntesDeFecha(@RequestParam Date fecha){
        return Response.ok().setPayload(eventoService.obtenerEventosAntesDeFecha(fecha));
    }

    @GetMapping()
    public Response getAllEventos(){
        return Response.ok().setPayload(eventoService.obtenerEventos());
    }

    @PostMapping()
    public Response createEvento(@Valid @RequestBody EventoDto eventoDto){
        return Response.ok().setPayload(eventoService.crearEvento(eventoDto));
    }

    @PutMapping()
    public Response updateEvento(@Valid @RequestBody EventoDto eventoDto){
        return Response.ok().setPayload(eventoService.actualizarEvento(eventoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteEvento(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(eventoService.eliminarEvento(uuid));
    }

    @GetMapping("/fechaFin")
    public Response getEventosActivas(@RequestParam Date fechaFin){
        return Response.ok().setPayload(eventoService.obtenerEventosActivas(fechaFin));
    }

    @GetMapping("/gestion")
    public Response obtenerEventosPorAnio(@RequestParam("year") Integer year) {
        return Response.ok().setPayload(eventoService.obtenerEventosPorAnio(year));
    }

}
