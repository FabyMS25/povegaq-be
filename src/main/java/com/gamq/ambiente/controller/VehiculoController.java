package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/vehiculo")
public class VehiculoController {
    @Autowired
    VehiculoService vehiculoService;

    @GetMapping("/uuid/{uuid}")
    public Response getVehiculoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorUuid(uuid));
    }

    @GetMapping("/placa/{placa}")
    public Response getVehiculoByPlaca(@PathVariable("placa") String placa){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorPlaca(placa));
    }

    @GetMapping()
    public Response getAllVehiculos(){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculos());
    }

    @PostMapping()
    public Response createVehiculo(@Valid @RequestBody VehiculoDto vehiculoDto){
        return Response.ok().setPayload(vehiculoService.crearVehiculo(vehiculoDto));
    }

    @PutMapping()
    public Response updateVehiculo(@Valid @RequestBody VehiculoDto vehiculoDto){
        return Response.ok().setPayload(vehiculoService.actualizarVehiculo(vehiculoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteVehiculo(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(vehiculoService.eliminarVehiculo(uuid));
    }

}
