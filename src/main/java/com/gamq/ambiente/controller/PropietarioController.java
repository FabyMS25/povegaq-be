package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.PropietarioDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.PropietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/propietario")
@Tag(name = "Propietarios", description = "API REST para gestión de Propietarios")
public class PropietarioController {
    @Autowired
    PropietarioService propietarioService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar Propietario por UUID", description = "Retorna los datos del Propietario")
    public Response getPropietarioByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(propietarioService.obtenerPropietarioPorUuid(uuid));
    }

    @GetMapping("/numeroDocumento/{numeroDocumento}")
    @Operation(summary = "Buscar Propietario por Numero Documento", description = "Retorna los datos del Propietario")
    public Response getPropietarioByNumeroDocumento(@PathVariable("numeroDocumento") String numeroDocumento) {
        return Response.ok().setPayload(propietarioService.obtenerPropietarioPorNumeroDocumento(numeroDocumento));
    }

    @GetMapping
    @Operation(summary = "Listar todos los Propietarios", description = "Obtiene la lista de los Propietarios")
    public Response getPropietarios() {
        return Response.ok().setPayload(propietarioService.obtenerPropietarios());
    }

    @PostMapping()
    @Operation(
            summary = "Crear nuevo Propietario",
            description = "Registra los datos de un propietario"
    )
    public ResponseEntity<Response<PropietarioDto>> createPropietario(@Valid @RequestBody PropietarioDto propietarioDto) {
        Response<PropietarioDto> response = Response.<PropietarioDto>created().setStatus(Status.OK).setPayload(propietarioService.crearPropietario(propietarioDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    @Operation(
            summary = "Modifica Propietario",
            description = "Modifica los datos de un propietario existente"
    )
    public Response updatePropietario(@Valid @RequestBody PropietarioDto propietarioDto) {
        return Response.ok().setPayload(propietarioService.actualizarPropietario(propietarioDto));
    }

    @DeleteMapping("/{uuid}")
    @Operation(
            summary = "Eliminar Propietario por uuid",
            description = "Eliminar el Propietario por su uuid"
    )
    public Response deletePropietario(@PathVariable("uuid") String uuid) {
        propietarioService.eliminarPropietario(uuid);
        return Response.ok().setPayload("El Propietario fue elimnado exitosamente");
    }
}
