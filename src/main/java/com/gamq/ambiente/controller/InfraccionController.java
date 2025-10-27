package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.PagoInfraccionRequest;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.InfraccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/infraccion")
@Tag(name = "Infracciones", description = "API REST para gestión de las Infracciones")
public class InfraccionController {
    @Autowired
    InfraccionService infraccionService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar Infraccion por UUID", description = "Retorna los datos de la Infraccion de una Inspeccion")
    public Response getInfraccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(infraccionService.obtenerInfraccionPorUuid(uuid));
    }

    @GetMapping("/vehiculo/{uuidVehiculo}")
    @Operation(summary = "Buscar Infraccion por uuid vehiculo", description = "Retorna los datos de la Infraccion de una Inspeccion")
    public Response getInfraccionByVehiculo(@PathVariable("uuidVehiculo") String uuidVehiculo) {
        return Response.ok().setPayload(infraccionService.obtenerInfraccionPorVehiculo(uuidVehiculo));
    }
    @GetMapping("/fecha")
    @Operation(summary = "Buscar Infraccion por fecha", description = "Retorna los datos de la Infraccion de una Inspeccion")
    public Response getInfraccionByFecha(@RequestParam Date fecha){
        return Response.ok().setPayload(infraccionService.obtenerInfraccionPorFecha(fecha));
    }

    @GetMapping()
    @Operation(summary = "Listar todos los Infraciones", description = "Retorna todas las infracciones")
    public Response getAllInfracciones(){
        return Response.ok().setPayload(infraccionService.obtenerInfracciones());
    }

    @PostMapping()
    @Operation(summary = "Crear nueva Infraccion", description = "Registra los datos de la Infraccion")
    public ResponseEntity<Response<InfraccionDto>> createInfraccion(@Valid @RequestBody InfraccionDto infraccionDto){
        Response<InfraccionDto> response = Response.<InfraccionDto>created().setStatus(Status.OK).setPayload(infraccionService.crearInfraccion(infraccionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/generar/{uuidInspeccion}")
    @Operation(summary = "Genera una Infraccion por uuid de la inspeccion", description = "Muestra los datos de la Infraccion")
    public Response generarInfraccion(@PathVariable String uuidInspeccion) {
        return Response.ok().setPayload(infraccionService.generarInfraccion(uuidInspeccion));
    }

    @PutMapping()
    @Operation(summary = "Modificar la Infraccion existente", description = "Modifica los datos de la Infraccion")
    public Response updateInfraccion(@Valid @RequestBody InfraccionDto infraccionDto){
        return Response.ok().setPayload(infraccionService.actualizarInfraccion(infraccionDto));
    }

    @DeleteMapping("/{uuid}")
    @Operation(
            summary = "Eliminar la Infraccion por uuid",
            description = "Eliminar la Infraccion logicamente"
    )
    public Response deleteInfraccion(@PathVariable("uuid") String uuid){
        infraccionService.eliminarInfraccion(uuid);
        return Response.ok().setPayload("La Infraccion fue eliminado exitosamente");
    }

    @PostMapping("/pagar-infraccion")
    @Operation(
            summary = "Actualiza el pago de la Infraccion",
            description = "Actualiza el pago de la infraccion de uuidInfraccion, numera tasa y fecha pago"
    )
    public Response markPayInfraccion(@RequestBody PagoInfraccionRequest pagoInfraccionRequest){
        return Response.ok().setPayload(infraccionService.marcarInfraccionComoPagada(pagoInfraccionRequest.getUuidInfraccion(),
                                                                                     pagoInfraccionRequest.getNumeroTasa(),
                                                                                     pagoInfraccionRequest.getFechaPago()));
    }
  }


