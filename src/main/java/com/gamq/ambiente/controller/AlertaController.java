package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.dto.AlertaDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/alerta")
public class AlertaController {
    @Autowired
    AlertaService alertaService;

    @GetMapping("/uuid/{uuid}")
    public Response getAlertaByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(alertaService.obtenerAlertaPorUuid(uuid));
    }

    @GetMapping
    public Response getAlertas() {
        return Response.ok().setPayload(alertaService.obtenerAlertas());
    }

    @PostMapping()
    public ResponseEntity<Response<AlertaDto>> createAlerta(@Valid @RequestBody AlertaDto alertaDto) {
        Response<AlertaDto> response = Response.<AlertaDto>created().setStatus(Status.OK).setPayload(alertaService.crearAlerta(alertaDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateAlerta(@RequestBody AlertaDto alertaDto) {
        return Response.ok().setPayload(alertaService.actualizarAlerta(alertaDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteAlerta(@PathVariable("uuid") String uuid) {
        alertaService.eliminarAlerta(uuid);
        return Response.noContent().setPayload("La alerta fue eliminada");
    }
    @GetMapping("/fecha-actual")
    public Response getAlertaByFechaActual(@RequestParam( value = "fechaActual") Date fechaActual){
        return Response.ok().setPayload(alertaService.obtenerAlertasPorFechaActual(fechaActual));
    }

    @GetMapping("/fecha-destinatario")
    public Response getAlertaByFechaActualAndUuidDestinatario(@RequestParam( value = "fechaActual") Date fechaActual,
                                                              @RequestParam( value = "uuidDestinatario") String uuidDestinatario){
        return Response.ok().setPayload(alertaService.obtenerAlertasPorFechaActualAndUuidDestinatario(fechaActual, uuidDestinatario));
    }

}
