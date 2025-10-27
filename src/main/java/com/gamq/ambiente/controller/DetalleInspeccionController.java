package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionRequestDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.DetalleInspeccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/detalleInspeccion")
@Tag(name = "Detalle de Inspecciones", description = "API REST para gestión de los detalles de inspeccion")
public class DetalleInspeccionController {
    @Autowired
    DetalleInspeccionService detalleInspeccionService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar Detalles de Inspeccion por UUID", description = "Retorna los datos de los Detalles de Inspeccion")
    public Response getDetalleInspeccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(detalleInspeccionService.obtenerDetalleInspeccionPorUuid(uuid));
    }

    @GetMapping()
    @Operation(summary = "Listar todos los Detalles de Inspeccion", description = "Obtiene todos los Detalles de Inspeciones")
    public Response getAllDetalleInspecciones(){
        return Response.ok().setPayload(detalleInspeccionService.obtenerDetalleInspecciones());
    }

    @PostMapping()
    @Operation(
            summary = "Crear nuevo detalle de inspección",
            description = "Registra los datos de los detalles de inspeccionn"
    )
    public ResponseEntity<Response<DetalleInspeccionDto>> createDetalleInspeccion(@Valid @RequestBody DetalleInspeccionDto detalleInspeccionDto){
        Response<DetalleInspeccionDto> response = Response.<DetalleInspeccionDto>created().setStatus(Status.OK).setPayload(detalleInspeccionService.crearDetalleInspeccion(detalleInspeccionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    @Operation(
            summary = "Modificar detalle de inspección",
            description = "Registra los datos modificados del detalle de inspeccionn"
    )
    public Response updateDetalleInspeccion(@Valid @RequestBody DetalleInspeccionDto detalleInspeccionDto){
        return Response.ok().setPayload(detalleInspeccionService.actualizarDetalleInspeccion(detalleInspeccionDto));
    }

    @DeleteMapping("/{uuid}")
    @Operation(
            summary = "Eliminar detalle de inspección",
            description = "Eliminar el detalle de inspeccion por su uuid"
    )
    public Response deleteDetalleInspeccion(@PathVariable("uuid") String uuid){
        detalleInspeccionService.eliminarDetalleInspeccion(uuid);
        return Response.ok().setPayload("El Detalle Inspeccion fue eliminado exitosamente");
    }

    @PutMapping("/add-detalle-inspecciones")
    @Operation(
            summary = "Agregar masivamente detalle de inspección",
            description = "Agregar una lista de detalle de inspeccion"
    )
    public Response addDetalleInspeccionesToInpeccion(@RequestBody InspeccionDetalleInspeccionDto inspeccionDetalleInspeccionDto){
        return Response.ok().setPayload(detalleInspeccionService.addDetalleInspecionToInspeccion(inspeccionDetalleInspeccionDto));
    }

    @GetMapping("/uuidInspeccion/{uuidInspeccion}")
    @Operation(
            summary = "Obtener los detalles de inspección",
            description = "Obtiene los detalle de inspeccion por su uuid de la inspección"
    )
    public Response getDetalleInspeccionByUuidInspeccion(@PathVariable("uuidInspeccion") String uuidInspeccion){
        return Response.ok().setPayload(detalleInspeccionService.obtenerDetalleInspeccionPorUuidInspeccion(uuidInspeccion));
    }

    @PostMapping("/gases")
    @Operation(
            summary = "Registrar medición de gases con tipo de combustible",
            description = "Registra múltiples detalles de gases con el tipo de combustible utilizado para una inspección"
    )
    public Response registrarDetalleGases(@RequestBody InspeccionRequestDto inspeccionRequestDto) {
        detalleInspeccionService.registrarDetalleInspeccionGases( inspeccionRequestDto);
        return Response.ok().setPayload("Gases registrados correctamente");
    }

    @PostMapping("/masivo")
    public Response registrarDetalleInspeccionMasivo(@RequestBody InspeccionRequestDto inspeccionRequestDto){
        return Response.ok().setPayload(detalleInspeccionService.registrarDetalleInspeccionMasivo(inspeccionRequestDto));
    }
}
