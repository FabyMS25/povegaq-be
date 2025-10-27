package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.TipoContribuyenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tipoContribuyente")
public class TipoContribuyenteController {
    @Autowired
    TipoContribuyenteService tipoContribuyenteService;

    @GetMapping("/uuid/{uuid}")
    public Response getTipoContribuyenteByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(tipoContribuyenteService.obtenerTipoContribuyentePorUuid(uuid));
    }

    @GetMapping("/descripcion/{descripcion}")
    public Response getTipoContribuyenteByDescripcion(@PathVariable("descripcion") String descripcion){
        return Response.ok().setPayload(tipoContribuyenteService.obtenerTipoContribuyentePorDescripcion(descripcion));
    }

    @GetMapping()
    public Response getAllTipoContribuyentes(){
        return Response.ok().setPayload(tipoContribuyenteService.obtenerTipoContribuyentes());
    }

    @PostMapping()
    public ResponseEntity<Response<TipoContribuyenteDto>> createTipoContribuyente(@Valid @RequestBody TipoContribuyenteDto tipoContribuyenteDto){
        Response<TipoContribuyenteDto> response = Response.<TipoContribuyenteDto>created().setStatus(Status.OK).setPayload(tipoContribuyenteService.crearTipoContribuyente(tipoContribuyenteDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateTipoContribuyente(@Valid @RequestBody TipoContribuyenteDto tipoContribuyenteDto){
        return Response.ok().setPayload(tipoContribuyenteService.actualizarTipoContribuyente(tipoContribuyenteDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteTipoContribuyente(@PathVariable("uuid") String uuid){
        tipoContribuyenteService.eliminarTipoContribuyente(uuid);
        return Response.ok().setPayload("El Tipo Contribuyente fue eliminado exitosamente");
    }
}
