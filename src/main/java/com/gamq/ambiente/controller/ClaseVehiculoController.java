package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ClaseVehiculoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.ClaseVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/claseVehiculo")
public class ClaseVehiculoController {
    @Autowired
    ClaseVehiculoService claseVehiculoService;

    @GetMapping("/uuid/{uuid}")
    public Response getClaseVehiculoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(claseVehiculoService.obtenerClaseVehiculoPorUuid(uuid));
    }

    @GetMapping("/nombre/{nombre}")
    public Response getClaseVehiculoByDescripcion(@PathVariable("nombre") String nombre){
        return Response.ok().setPayload(claseVehiculoService.obtenerClaseVehiculoPorNombre(nombre));
    }

    @GetMapping()
    public Response getAllClaseVehiculos(){
        return Response.ok().setPayload(claseVehiculoService.obtenerClaseVehiculos());
    }

    @PostMapping()
    public ResponseEntity<Response<ClaseVehiculoDto>> createClaseVehiculo(@Valid @RequestBody ClaseVehiculoDto claseVehiculoDto){
        Response<ClaseVehiculoDto> response = Response.<ClaseVehiculoDto>created().setStatus(Status.OK).setPayload(claseVehiculoService.crearClaseVehiculo(claseVehiculoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateClaseVehiculo(@Valid @RequestBody ClaseVehiculoDto ClaseVehiculoDto){
        return Response.ok().setPayload(claseVehiculoService.actualizarClaseVehiculo(ClaseVehiculoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteClaseVehiculo(@PathVariable("uuid") String uuid){
        claseVehiculoService.eliminarClaseVehiculo(uuid);
        return Response.noContent().setPayload("La Clase Vehiculo fue eliminado exitosamente");
    }
}
