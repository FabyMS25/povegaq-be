package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.LimiteEmisionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.LimiteEmisionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response createLimiteEmision(@Valid @RequestBody LimiteEmisionDto limiteEmisionDto){
        return Response.ok().setPayload(limiteEmisionService.crearLimiteEmision(limiteEmisionDto));
    }

    @PutMapping()
    public Response updateLimiteEmision(@Valid @RequestBody LimiteEmisionDto limiteEmisionDto){
        return Response.ok().setPayload(limiteEmisionService.actualizarLimiteEmision(limiteEmisionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteLimiteEmision(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(limiteEmisionService.eliminarLimiteEmision(uuid));
    }

    @PutMapping("/{uuid}/cambiar-activo")
    public Response updateLimiteEmisionActivo(@PathVariable String uuid,
                                              @RequestParam boolean activo){
        return Response.ok().setPayload(limiteEmisionService.actualizarLimiteEmisionActivo(uuid, activo));
    }

}
