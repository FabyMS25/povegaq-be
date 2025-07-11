package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.TipoClaseVehiculoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.TipoClaseVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tipoClaseVehiculo")
public class TipoClaseVehiculoController {
    @Autowired
    TipoClaseVehiculoService tipoClaseVehiculoService;

    @GetMapping("/uuid/{uuid}")
    public Response getTipoClaseVehiculoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(tipoClaseVehiculoService.obtenerTipoClaseVehiculoPorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getTipoClaseVehiculoByDescripcion(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(tipoClaseVehiculoService.obtenerTipoClaseVehiculoPorNombre(nombre));
    }

    @GetMapping()
    public Response getAllTipoClaseVehiculos(){
        return Response.ok().setPayload(tipoClaseVehiculoService.obtenerTipoClaseVehiculos());
    }

    @PostMapping()
    public Response createTipoClaseVehiculo(@Valid @RequestBody TipoClaseVehiculoDto tipoClaseVehiculoDto){
        return Response.ok().setPayload(tipoClaseVehiculoService.crearTipoClaseVehiculo(tipoClaseVehiculoDto));
    }

    @PutMapping()
    public Response updateTipoClaseVehiculo(@Valid @RequestBody TipoClaseVehiculoDto TipoClaseVehiculoDto){
        return Response.ok().setPayload(tipoClaseVehiculoService.actualizarTipoClaseVehiculo(TipoClaseVehiculoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteTipoClaseVehiculo(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(tipoClaseVehiculoService.eliminarTipoClaseVehiculo(uuid));
    }
}
