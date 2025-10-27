package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ConductorDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.ConductorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/conductor")
@Tag(name = "Conductor", description = "API REST para gestión de Conductores")
public class ConductorController {
    @Autowired
    ConductorService conductorService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar Conductor por UUID", description = "Retorna los datos del Conductor")
    public Response getConductorByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(conductorService.obtenerConductorPorUuid(uuid));
    }

    @GetMapping("/numeroDocumento/{numeroDocumento}")
    @Operation(summary = "Buscar Conductor por Numero Documento", description = "Retorna los datos del Conductor")
    public Response getConductorByNumeroDocumento(@PathVariable("numeroDocumento") String numeroDocumento){
        return Response.ok().setPayload(conductorService.obtenerConductorPorNumeroDocumento(numeroDocumento));
    }

    @GetMapping()
    @Operation(summary = "Listar todos los Conductores", description = "Obtiene la lista de los Conductores")
    public Response getAllConductores(){
        return Response.ok().setPayload(conductorService.obtenerConductores());
    }

    @PostMapping()
    @Operation(
            summary = "Crear nuevo Conductor",
            description = "Registra los datos de un conductor"
    )
    public ResponseEntity<Response<ConductorDto>> createConductor(@Valid @RequestBody ConductorDto conductorDto){
        Response<ConductorDto> response = Response.<ConductorDto>created().setStatus(Status.OK).setPayload(conductorService.crearConductor(conductorDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    @Operation(
            summary = "Modifica Conductor",
            description = "Modifica los datos de un conductor existente"
    )
    public Response updateConductor(@Valid @RequestBody ConductorDto conductorDto){
        return Response.ok().setPayload(conductorService.actualizarConductor(conductorDto));
    }

    @DeleteMapping("/{uuid}")
    @Operation(
            summary = "Eliminar Conductor por uuid",
            description = "Eliminar el Conductor por su uuid"
    )
    public Response deleteConductor(@PathVariable("uuid") String uuid){
        conductorService.eliminarConductor(uuid);
        return Response.ok().setPayload("El conductor fue eliminado exitosamente");
    }
}
