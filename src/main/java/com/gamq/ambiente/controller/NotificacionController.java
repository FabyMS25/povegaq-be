package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notificacion")
public class NotificacionController {
    @Autowired
    NotificacionService notificacionService;

    @GetMapping("/uuid/{uuid}")
    public Response getNotificacionByUuid(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(notificacionService.obtenerNotificacionPorUuid(uuid));
    }

    @GetMapping("/intentos/{uuidInspeccion}")
    public Response getNotificacionIntentosByUuidInspeccion(@PathVariable("uuidInspeccion") String uuidInspeccion){
        return Response.ok().setPayload(notificacionService.ObtenerNotificacionIntentoPorInspeccion(uuidInspeccion));
    }

    @GetMapping()
    public Response getAllNotificaciones(){ return Response.ok().setPayload(notificacionService.obtenerNotificaciones());}

    @PostMapping()
    public Response createNotificacion(@Valid @RequestBody NotificacionDto notificacionDto){
        return Response.ok().setPayload(notificacionService.crearNotificacion(notificacionDto));
    }

    @PutMapping()
    public Response updateNotificacion(@Valid @RequestBody NotificacionDto notificacionDto){
        return Response.ok().setPayload(notificacionService.actualizarNotificacion(notificacionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteNotificacion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(notificacionService.eliminarNotificacion(uuid));
    }
}
