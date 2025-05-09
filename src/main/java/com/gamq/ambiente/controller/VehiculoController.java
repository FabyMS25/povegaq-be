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

    @GetMapping("/poliza/{poliza}")
    public Response getVehiculoByPoliza(@PathVariable("poliza") String poliza){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorPoliza(poliza));
    }

    @GetMapping("/vin/{vin}")
    public Response getVehiculoByVin(@PathVariable("vin") String vin){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorVinNumeroIdentificacion(vin));
    }

    @GetMapping("/pin/{pin}")
    public Response getVehiculoByPin(@PathVariable("pin") String pin){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorPinNumeroIdentificacion(pin));
    }

    @GetMapping("/copo/{copo}")
    public Response getVehiculoByCopo(@PathVariable("copo") String copo){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorCopo(copo));
    }

    @GetMapping("/placaAnterior/{placaAnterior}")
    public Response getVehiculoByPlacaAnterior(@PathVariable("placaAnterior") String placaAnterior){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorPlacaAnterior(placaAnterior));
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
