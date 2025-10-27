package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.UfvDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.UfvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/ufv")
public class UfvController {
    @Autowired
    UfvService ufvService;

    @GetMapping("/uuid/{uuid}")
    public Response getUfvByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(ufvService.obtenerUfvPorUuid(uuid));
    }

    @GetMapping("/fecha")
    public Response getUfvByFecha(@RequestParam( value = "fecha") Date fecha){
        return Response.ok().setPayload(ufvService.obtenerUfvPorFecha(fecha));
    }

    @GetMapping()
    public Response getAllUfves(){
        return Response.ok().setPayload(ufvService.obtenerUfves());
    }

    @PostMapping()
    public ResponseEntity<Response<UfvDto>> createUfv(@Valid @RequestBody UfvDto ufvDto){
        Response<UfvDto> response = Response.<UfvDto>created().setStatus(Status.OK).setPayload(ufvService.crearUfv(ufvDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateUfv(@Valid @RequestBody UfvDto ufvDto){
        return Response.ok().setPayload(ufvService.actualizarUfv(ufvDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteUfv(@PathVariable("uuid") String uuid){
        ufvService.eliminarUfv(uuid);
        return Response.noContent().setPayload("El UFVs fue eliminado exitosamente");
    }
}
