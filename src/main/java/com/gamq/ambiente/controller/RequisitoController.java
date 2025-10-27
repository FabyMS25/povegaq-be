package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.RequisitoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.RequisitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/requisito")
public class RequisitoController {
    @Autowired
    RequisitoService requisitoService;

    @GetMapping("/uuid/{uuid}")
    public Response getRequisitoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(requisitoService.obtenerRequisitoPorUuid(uuid));
    }

    @GetMapping("/descripcion/{descripcion}")
    public Response getRequisitoByDescripcion(@PathVariable("descripcion") String descripcion){
        return Response.ok().setPayload(requisitoService.obtenerRequisitoPorDescripcion(descripcion));
    }

    @GetMapping()
    public Response getAllRequisitos(){
        return Response.ok().setPayload(requisitoService.obtenerRequisitos());
    }

    @PostMapping()
    public ResponseEntity<Response<RequisitoDto>> createRequisito(@Valid @RequestBody RequisitoDto requisitoDto){
        Response<RequisitoDto> response = Response.<RequisitoDto>created().setStatus(Status.OK).setPayload(requisitoService.crearRequisito(requisitoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateRequisito(@Valid @RequestBody RequisitoDto RequisitoDto){
        return Response.ok().setPayload(requisitoService.actualizarRequisito(RequisitoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteRequisito(@PathVariable("uuid") String uuid){
        requisitoService.eliminarRequisito(uuid);
        return Response.ok().setPayload("El Requisito fue eliminado exitosamente");
    }
}
