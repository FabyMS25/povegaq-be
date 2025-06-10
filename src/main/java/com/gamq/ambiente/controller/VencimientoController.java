package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.scheduler.NotificacionScheduledTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vencimientos")
public class VencimientoController {
    @Autowired
    NotificacionScheduledTask notificacionScheduledTask;

    @PostMapping("/procesar-vencimiento")
    public Response ejecutarProcesoDeVencimientos() {
        notificacionScheduledTask.procesarVencimientos();
        return Response.ok().setPayload("Procesamiento de vencimientos ejecutado correctamente.");
    }
}
