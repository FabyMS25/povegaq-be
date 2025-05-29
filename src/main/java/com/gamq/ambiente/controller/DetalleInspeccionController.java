package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDetalleInspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.DetalleInspeccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Response createDetalleInspeccion(@Valid @RequestBody DetalleInspeccionDto detalleInspeccionDto){
        return Response.ok().setPayload(detalleInspeccionService.crearDetalleInspeccion(detalleInspeccionDto));
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
        return Response.ok().setPayload(detalleInspeccionService.eliminarDetalleInspeccion(uuid));
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
}
