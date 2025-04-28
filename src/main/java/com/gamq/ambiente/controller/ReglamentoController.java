package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ReglamentoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.ReglamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reglamento")
public class ReglamentoController {
    @Autowired
    ReglamentoService reglamentoService;

    @GetMapping("/uuid/{uuid}")
    public Response getReglamentoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(reglamentoService.obtenerReglamentoPorUuid(uuid));
    }

    @GetMapping("/codigo/{codigo}")
    public Response getReglamentoByCodigo(@PathVariable("codigo") String codigo){
        return Response.ok().setPayload(reglamentoService.obtenerReglamentoPorCodigo(codigo));
    }

    @GetMapping()
    public Response getAllReglamentos(){
        return Response.ok().setPayload(reglamentoService.obtenerReglamentos());
    }

    @PostMapping()
    public Response createReglamento(@Valid @RequestBody ReglamentoDto reglamentoDto){
        return Response.ok().setPayload(reglamentoService.crearReglamento(reglamentoDto));
    }

    @PutMapping()
    public Response updateReglamento(@Valid @RequestBody ReglamentoDto reglamentoDto){
        return Response.ok().setPayload(reglamentoService.actualizarReglamento(reglamentoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteReglamento(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(reglamentoService.eliminarReglamento(uuid));
    }

    @PutMapping("/cambiar-inactivo")
    public Response updateReglamentoActivo(){
        boolean resultado = reglamentoService.actualizarReglamentoActivoToInactivo();
        if (resultado)
        {
            return Response.<Boolean>ok().setPayload(true)
                    .setErrors("El reglamento actualizado exitosamente");
        }
        else {
            return Response.<Boolean>badRequest().setPayload(false)
                    .setMetadata("No se pudo actalizar el reglamaneto o no existe reglamentos activos!");
        }
    }
}
