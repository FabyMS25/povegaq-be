package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.TipoInfraccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.service.TipoInfraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tipoInfraccion")
public class TipoInfraccionController {
    @Autowired
    TipoInfraccionService tipoInfraccionService;

    @GetMapping("/uuid/{uuid}")
    public Response getTipoInfraccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(tipoInfraccionService.obtenerTipoInfraccionPorUuid(uuid));
    }

    @GetMapping("/grado/{grado}")
    public Response getTipoInfraccionByGrado(@PathVariable("grado") GradoInfraccion grado){
        return Response.ok().setPayload(tipoInfraccionService.obtenerTipoInfraccionPorGrado(grado));
    }

    @GetMapping()
    public Response getAllTipoInfraccions(){
        return Response.ok().setPayload(tipoInfraccionService.obtenerTipoInfracciones());
    }

    @PostMapping()
    public ResponseEntity<Response<TipoInfraccionDto>> createTipoInfraccion(@Valid @RequestBody TipoInfraccionDto TipoInfraccionDto){
        Response<TipoInfraccionDto> response = Response.<TipoInfraccionDto>created().setStatus(Status.OK).setPayload(tipoInfraccionService.crearTipoInfraccion(TipoInfraccionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateTipoInfraccion(@Valid @RequestBody TipoInfraccionDto TipoInfraccionDto){
        return Response.ok().setPayload(tipoInfraccionService.actualizarTipoInfraccion(TipoInfraccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteTipoInfraccion(@PathVariable("uuid") String uuid){
        tipoInfraccionService.eliminarTipoInfraccion(uuid);
        return Response.ok().setPayload("El Tipo Infraccion fue eliminado exitosamente");
    }

    @GetMapping("/no-automatico/{uuidTipoContribuyente}")
    public Response getTipoInfraccionNoAutomatico(@PathVariable("uuidTipoContribuyente") String uuidTipoContribuyente){
        return Response.ok().setPayload(tipoInfraccionService.obtenerTipoInfraccionNoAutomativoPorUuidTipoContribuyente(uuidTipoContribuyente));
    }
}
