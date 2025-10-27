package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ConfiguracionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/configuracion")
public class ConfiguracionController {
    @Autowired
    ConfiguracionService configuracionService;

    @GetMapping("/uuid/{uuid}")
    public Response getConfiguracionByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(configuracionService.obtenerConfiguracionPorUuid(uuid));
    }

    @GetMapping("/clave/{clave}")
    public Response getConfiguracionByClave(@PathVariable("clave") String clave){
        return Response.ok().setPayload(configuracionService.obtenerConfiguracionPorClave(clave));
    }

    @GetMapping()
    public Response getAllConfiguraciones(){
        return Response.ok().setPayload(configuracionService.obtenerConfiguraciones());
    }

    @GetMapping("/activas")
    public Response obtenerConfiguracionesActivas() {
        return Response.ok().setPayload(configuracionService.obtenerConfiguracionesActivas());
    }

    @PostMapping()
    public ResponseEntity<Response<ConfiguracionDto>> createConfiguracion(@Valid @RequestBody ConfiguracionDto configuracionDto){
        Response<ConfiguracionDto> response = Response.<ConfiguracionDto>created().setStatus(Status.OK).setPayload(configuracionService.crearConfiguracion(configuracionDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateConfiguracion(@Valid @RequestBody ConfiguracionDto configuracionDto){
        return Response.ok().setPayload(configuracionService.actualizarConfiguracion(configuracionDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteConfiguracion(@PathVariable("uuid") String uuid){
        configuracionService.eliminarConfiguracion(uuid);
        return Response.ok().setPayload("La Configuracion fue eliminado exitosamenete");
    }

    @GetMapping("/gestion")
    public Response obtenerConfiguracionesPorAnio(@RequestParam("year") Integer year) {
        return Response.ok().setPayload(configuracionService.obtenerConfiguracionesPorAnio(year));
    }

    @PutMapping("/{uuid}/cambiar-activo")
    public Response updateConfiguracionActivo(@PathVariable String uuid,
                                          @RequestParam boolean activo){
        return Response.ok().setPayload(configuracionService.actualizarConfiguracionActivo(uuid, activo));
    }

    @GetMapping("/entre-fechas")
    public Response obtenerConfiguracionesEntreFechas(
            @RequestParam("inicio") Date rangoInicio,
            @RequestParam("fin") Date rangoFin) {
        return Response.ok().setPayload (configuracionService.obtenerConfiguracionesEntreFechas(rangoInicio, rangoFin));
    }
}
