package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.LimiteEmisionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.LimiteEmisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/limiteEmision")
public class LimiteEmisionController {
    @Autowired
    LimiteEmisionService limiteEmisionService;

    @GetMapping("/uuid/{uuid}")
    public Response getLimiteEmisionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(limiteEmisionService.obtenerLimiteEmisionPorUuid(uuid));
    }

    @GetMapping("/tipoParametro/{uuidTipoParametro}")
    public Response getLimiteEmisionByNombreTipoParametro(@PathVariable("uuidTipoParametro") String uuidTipoParametro){
        return Response.ok().setPayload(limiteEmisionService.obtenerLimiteEmisionPorUuidTipoParametro(uuidTipoParametro));
    }

    @GetMapping()
    public Response getAllLimiteEmisiones(){
        return Response.ok().setPayload(limiteEmisionService.obtenerLimiteEmisiones());
    }

    @PostMapping()
    public ResponseEntity<Response<LimiteEmisionDto>> createLimiteEmision(@Valid @RequestBody LimiteEmisionDto limiteEmisionDto){
        Response<LimiteEmisionDto> response = Response.<LimiteEmisionDto>created().setStatus(Status.OK).setPayload(limiteEmisionService.crearLimiteEmision(limiteEmisionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateLimiteEmision(@Valid @RequestBody LimiteEmisionDto limiteEmisionDto){
        return Response.ok().setPayload(limiteEmisionService.actualizarLimiteEmision(limiteEmisionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteLimiteEmision(@PathVariable("uuid") String uuid){
        limiteEmisionService.eliminarLimiteEmision(uuid);
        return Response.ok().setPayload("El Limite emision fue eliminado eitosamente");
    }

    @PutMapping("/{uuid}/cambiar-activo")
    public Response updateLimiteEmisionActivo(@PathVariable String uuid,
                                              @RequestParam boolean activo){
        return Response.ok().setPayload(limiteEmisionService.actualizarLimiteEmisionActivo(uuid, activo));
    }

}
