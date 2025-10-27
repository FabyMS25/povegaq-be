package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.EstacionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.EstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/estacion")
public class EstacionController {
    @Autowired
    EstacionService estacionService;

    @GetMapping("/uuid/{uuid}")
    public Response getEstacionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(estacionService.obtenerEstacionPorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getEstacionByNombre(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(estacionService.obtenerEstacionPorNombre(nombre));
    }

    @GetMapping()
    public Response getAllEstaciones(){
        return Response.ok().setPayload(estacionService.obtenerEstaciones());
    }

    @PostMapping()
    public ResponseEntity<Response<EstacionDto>> createEstacion(@Valid @RequestBody EstacionDto estacionDto){
        Response<EstacionDto> response = Response.<EstacionDto>created().setStatus(Status.OK).setPayload(estacionService.crearEstacion(estacionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateEstacion(@Valid @RequestBody EstacionDto EstacionDto){
        return Response.ok().setPayload(estacionService.actualizarEstacion(EstacionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteEstacion(@PathVariable("uuid") String uuid){
        estacionService.eliminarEstacion(uuid);
        return Response.noContent().setPayload("La Estacion fue eliminado exitosamente");
    }
}
