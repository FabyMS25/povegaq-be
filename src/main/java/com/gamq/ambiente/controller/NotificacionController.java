package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
