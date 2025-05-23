package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
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

    @GetMapping("por-tipoNotificacion/{tipoNotificacion}")
    public Response getNotificacionesByTipoNotificacion(@PathVariable("tipoNotificacion")TipoNotificacion tipoNotificacion){
        return Response.ok().setPayload(notificacionService.obtenerPorTipoNotificacion(tipoNotificacion));
    }

    @PostMapping()
    public Response createNotificacion(@Valid @RequestBody NotificacionDto notificacionDto){
        return Response.ok().setPayload(notificacionService.crearNotificacion(notificacionDto));
    }

    @GetMapping("/previa-vista/{uuidInspeccion}")
    public Response getNotificacionPreviaVistaByUuidInspeccion(@PathVariable("uuidInspeccion") String uuidInspeccion) {
        return Response.ok().setPayload(notificacionService.generarNotificacionVistaPrevia(uuidInspeccion));
    }

    @PutMapping()
    public Response updateNotificacion(@Valid @RequestBody NotificacionDto notificacionDto){
        return Response.ok().setPayload(notificacionService.actualizarNotificacion(notificacionDto));
    }

    @PutMapping("/{uuid}/tipoNotificacion")
    public Response updateTipoNotificacionByUuidNotificacion(@PathVariable String uuid,
                                                             @RequestBody TipoNotificacion nuevoTipoNotificacion){
        return  Response.ok().setPayload(notificacionService.actualizarTipoNotificacion(uuid,nuevoTipoNotificacion));
    }

    @PutMapping("/{uuid}/estadoNotificacion")
    public Response updateEstadoNotificacionByUuidNotificacion(@PathVariable String uuid,
                                                               @RequestBody EstadoNotificacion nuevoEstadoNotificacion){
        return  Response.ok().setPayload(notificacionService.actualizarEstadoNotificacion(uuid, nuevoEstadoNotificacion));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteNotificacion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(notificacionService.eliminarNotificacion(uuid));
    }
}
