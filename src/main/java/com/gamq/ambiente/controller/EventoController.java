package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.EventoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/evento")
@Tag(name = "Eventos", description = "API REST para gestión de los eventos de una actividad")
public class EventoController {
    @Autowired
    EventoService eventoService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar Evento por UUID", description = "Retorna los datos de un evento")
    public Response getEventoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(eventoService.obtenerEventoPorUuid(uuid));
    }

    @GetMapping("/institucion/{institucion}")
    @Operation(summary = "Buscar Evento por institucion", description = "Retorna los datos del evento")
    public Response getEventoByInstitucion(@PathVariable("institucion") String institucion){
        return Response.ok().setPayload(eventoService.obtenerEventoPorInstitucion(institucion));
    }

    @GetMapping("/fecha")
    @Operation(summary = "Buscar Evento por fecha", description = "Retorna unas lista de eventos")
    public Response getEventoAntesDeFecha(@RequestParam Date fecha){
        return Response.ok().setPayload(eventoService.obtenerEventosAntesDeFecha(fecha));
    }

    @GetMapping()
    @Operation(summary = "Lista todos los eventos", description = "Retorna la lista de eventos")
    public Response getAllEventos(){
        return Response.ok().setPayload(eventoService.obtenerEventos());
    }

    @PostMapping()
    @Operation(summary = "Crear Nuevo Evento de una actividad", description = "Registra los datos de un nuevo evento")
    public ResponseEntity<Response<EventoDto>> createEvento(@Valid @RequestBody EventoDto eventoDto){
        Response<EventoDto> response = Response.<EventoDto>created().setStatus(Status.OK).setPayload(eventoService.crearEvento(eventoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    @Operation(summary = "Modifica el Evento", description = "Registra los datos modificados de un evento existente")
    public Response updateEvento(@Valid @RequestBody EventoDto eventoDto){
        return Response.ok().setPayload(eventoService.actualizarEvento(eventoDto));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Eliminar Evento", description = "Elimina el evento por el uuid")
    public Response deleteEvento(@PathVariable("uuid") String uuid){
        eventoService.eliminarEvento(uuid);
        return Response.noContent().setPayload("El Evento fue eliminado exitosamente");
    }

    @GetMapping("/fechaFin")
    @Operation(summary = "Buscar Evento activos por fecha final", description = "Retorna una lista de eventos activos por fecha final")
    public Response getEventosActivas(@RequestParam Date fechaFin){
        return Response.ok().setPayload(eventoService.obtenerEventosActivas(fechaFin));
    }

    @GetMapping("/gestion")
    @Operation(summary = "Buscar Eventos por gestion", description = "Retorna una lista de eventos por gestion")
    public Response obtenerEventosPorAnio(@RequestParam("year") Integer year) {
        return Response.ok().setPayload(eventoService.obtenerEventosPorAnio(year));
    }

}
