package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.TipoParametroDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.TipoParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tipoParametro")
public class TipoParametroController {
    @Autowired
    TipoParametroService tipoParametroService;

    @GetMapping("/uuid/{uuid}")
    public Response getTipoParametroByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(tipoParametroService.obtenerTipoParametroPorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getTipoParametroByNombre(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(tipoParametroService.obtenerTipoParametroPorNombre(nombre));
    }

    @GetMapping()
    public Response getAllTipoParametros(){
        return Response.ok().setPayload(tipoParametroService.obtenerTipoParametros());
    }

    @PostMapping()
    public Response createTipoParametro(@Valid @RequestBody TipoParametroDto tipoParametroDto){
        return Response.ok().setPayload(tipoParametroService.crearTipoParametro(tipoParametroDto));
    }

    @PutMapping()
    public Response updateTipoParametro(@Valid @RequestBody TipoParametroDto tipoParametroDto){
        return Response.ok().setPayload(tipoParametroService.actualizarTipoParametro(tipoParametroDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteTipoParametro(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(tipoParametroService.eliminarTipoParametro(uuid));
    }

    @PutMapping("/{uuid}/cambiar-activo")
    public Response updateTipoParametroActivo(@PathVariable String uuid,
                                              @RequestParam boolean activo) {
        return Response.ok().setPayload(tipoParametroService.actualizarTipoParametroActivo(uuid, activo));
    }
}
