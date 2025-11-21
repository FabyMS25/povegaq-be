package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

  //  @GetMapping("/chasis/{chasis}")
  //  public Response getVehiculoByChasis(@PathVariable("chasis") String chasis){
  //      return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorChasis(chasis));
  //  }

    @GetMapping("/placaAnterior/{placaAnterior}")
    public Response getVehiculoByPlacaAnterior(@PathVariable("placaAnterior") String placaAnterior){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculoPorPlacaAnterior(placaAnterior));
    }

    @GetMapping()
    public Response getAllVehiculos(){
        return Response.ok().setPayload(vehiculoService.obtenerVehiculos());
    }

    @PostMapping()
    public ResponseEntity<Response<VehiculoDto>> createVehiculo(@Valid @RequestBody VehiculoDto vehiculoDto, BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            Response<VehiculoDto> response = new Response<>();
            response.setStatus(Status.BAD_REQUEST);
            response.setErrors(errors);
            return ResponseEntity.badRequest().body(response);
        }

        Response<VehiculoDto> response = Response.<VehiculoDto>created().setStatus(Status.OK).setPayload(vehiculoService.crearVehiculo(vehiculoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateVehiculo(@Valid @RequestBody VehiculoDto vehiculoDto){
        return Response.ok().setPayload(vehiculoService.actualizarVehiculo(vehiculoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteVehiculo(@PathVariable("uuid") String uuid){
        vehiculoService.eliminarVehiculo(uuid);
        return Response.ok().setPayload("El Vehiculo fue eliminado exitosamente");
    }
}
