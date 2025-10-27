package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.DatoTecnicoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.DatoTecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/datoTecnico")
public class DatoTecnicoController {
    @Autowired
    DatoTecnicoService datoTecnicoService;

    @GetMapping("/uuid/{uuid}")
    public Response getDatoTecnicoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(datoTecnicoService.obtenerDatoTecnicoPorUuid(uuid));
    }

    @PostMapping()
    public ResponseEntity<Response<DatoTecnicoDto>> createDatoTecnico(@Valid @RequestBody DatoTecnicoDto datoTecnicoDto){
        Response<DatoTecnicoDto> response = Response.<DatoTecnicoDto>created().setStatus(Status.OK).setPayload(datoTecnicoService.crearDatoTecnico(datoTecnicoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateDatoTecnico(@Valid @RequestBody DatoTecnicoDto datoTecnicoDto){
        return Response.ok().setPayload(datoTecnicoService.actualizarDatoTecnico(datoTecnicoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteDatoTecnico(@PathVariable("uuid") String uuid){
        datoTecnicoService.eliminarDatoTecnico(uuid);
        return Response.ok().setPayload("El Dato Tecnico fue eliminado exitosamente");
    }
}
