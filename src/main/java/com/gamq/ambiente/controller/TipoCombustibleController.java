package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.TipoCombustibleDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.TipoCombustibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tipoCombustible")
public class TipoCombustibleController {
    @Autowired
    TipoCombustibleService tipoCombustibleService;

    @GetMapping("/uuid/{uuid}")
    public Response getTipoCombustibleByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(tipoCombustibleService.obtenerTipoCombustiblePorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getTipoCombustibleByNombre(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(tipoCombustibleService.obtenerTipoCombustiblePorNombre(nombre));
    }

    @GetMapping()
    public Response getAllTipoCombustibles(){
        return Response.ok().setPayload(tipoCombustibleService.obtenerTipoCombustibles());
    }

    @PostMapping()
    public Response createTipoCombustible(@Valid @RequestBody TipoCombustibleDto tipoCombustibleDto){
        return Response.ok().setPayload(tipoCombustibleService.crearTipoCombustible(tipoCombustibleDto));
    }

    @PutMapping()
    public Response updateTipoCombustible(@Valid @RequestBody TipoCombustibleDto tipoCombustibleDto){
        return Response.ok().setPayload(tipoCombustibleService.actualizarTipoCombustible(tipoCombustibleDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteTipoCombustible(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(tipoCombustibleService.eliminarTipoCombustible(uuid));
    }
}
