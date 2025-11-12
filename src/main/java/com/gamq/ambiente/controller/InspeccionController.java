package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.ComparacionEmisionService;
import com.gamq.ambiente.service.InspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/inspeccion")
public class InspeccionController {
    @Autowired
    InspeccionService inspeccionService;
    @Autowired
    ComparacionEmisionService comparacionEmisionService;

    @GetMapping("/uuid/{uuid}")
    public Response getInspeccionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorUuid(uuid));
    }

    @GetMapping("/codigo/{codigo}")
    public Response getInspeccionByCodigo(@PathVariable("codigo") String codigo){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorCodigo(codigo));
    }

    @GetMapping("/placa/{placa}")
    public Response getInspeccionByPlaca(@PathVariable("placa") String placa){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorPlaca(placa));
    }

    @GetMapping("/uuidUsuario/{uuidUsuario}")
    public Response getInspeccionByUuidUsuario(@PathVariable("uuidUsuario") String uuidUsuario){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorUuidUsuario(uuidUsuario));
    }

    @GetMapping("/uuidActividad/{uuidActividad}")
    public Response getInspeccionByUuidActividad(@PathVariable("uuidActividad") String uuidActividad){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorUuidActividad(uuidActividad));
    }

    @GetMapping("/fechaInspeccion")
    public Response getInspeccionByFechaInspeccion(@RequestParam Date fechaInspeccion){
        return Response.ok().setPayload(inspeccionService.obtenerInspeccionPorFechaInspeccion(fechaInspeccion));
    }

    @GetMapping()
    public Response getAllInspeccions(){
        return Response.ok().setPayload(inspeccionService.obtenerInspecciones());
    }

    @PostMapping()
    public ResponseEntity<Response<InspeccionDto>> createInspeccion(@Valid @RequestBody InspeccionDto inspeccionDto){
        Response<InspeccionDto> response = Response.<InspeccionDto>created().setStatus(Status.OK).setPayload(inspeccionService.crearInspeccion(inspeccionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateInspeccion(@Valid @RequestBody InspeccionDto inspeccionDto){
        return Response.ok().setPayload(inspeccionService.actualizarInspeccion(inspeccionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteInspeccion(@PathVariable("uuid") String uuid){
        inspeccionService.eliminarInspeccion(uuid);
        return Response.ok().setPayload("La Inspeccion fue eliminado exitosamente");
    }

    @PostMapping("/validar-inspeccion/{uuidInspeccion}")
    public Response validarInspeccion(@PathVariable("uuidInspeccion") String uuidInspeccion){
        comparacionEmisionService.validarInspeccion(uuidInspeccion);
        return Response.ok().setPayload("Operacion completada");
    }

    @GetMapping("/ultima-inspeccion/{uuidVehiculo}")
    public Response lastInspeccionByUuidVehiculo(@PathVariable("uuidVehiculo") String uuidVehiculo){
        return Response.ok().setPayload(inspeccionService.obtenerUltimaInspeccionPorUuidVehiculo(uuidVehiculo));
    }
}
