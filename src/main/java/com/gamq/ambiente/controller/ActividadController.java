package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.ActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/actividad")
public class ActividadController {
    @Autowired
    ActividadService actividadService;

    @GetMapping("/uuid/{uuid}")
    public Response getActividadByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(actividadService.obtenerActividadPorUuid(uuid));
    }

    @GetMapping("/tipoActividad/{tipoActividad}")
    public Response getActividadByTipoActividad(@PathVariable("tipoActividad") String tipoActividad){
        return Response.ok().setPayload(actividadService.obtenerActividadPorTipoActividad(tipoActividad));
    }

    @GetMapping()
    public Response getAllActividades(){
        return Response.ok().setPayload(actividadService.obtenerActividades());
    }

    @GetMapping("/activas")
    public Response obtenerActividadesActivas() {
        return Response.ok().setPayload(actividadService.obtenerActividadesActivas());
    }

    @PostMapping()
    public ResponseEntity<Response<ActividadDto>> createActividad(@Valid @RequestBody ActividadDto actividadDto){
        Response<ActividadDto> response = Response.<ActividadDto>created().setStatus(Status.OK).setPayload(actividadService.crearActividad(actividadDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateActividad(@Valid @RequestBody ActividadDto actividadDto){
        return Response.ok().setPayload(actividadService.actualizarActividad(actividadDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteActividad(@PathVariable("uuid") String uuid){
        actividadService.eliminarActividad(uuid);
        return Response.ok().setPayload("La actividad fue eliminado exitosamente");
    }

    @GetMapping("/gestion")
    public Response obtenerActividadesPorAnio(@RequestParam("year") Integer year) {
        return Response.ok().setPayload(actividadService.obtenerActividadesPorAnio(year));
    }

    @PutMapping("/{uuid}/cambiar-activo")
    public Response updateActividadActivo(@PathVariable String uuid,
                                          @RequestParam boolean activo){
        return Response.ok().setPayload(actividadService.actualizarActividadActivo(uuid, activo));
    }

    @GetMapping("/entre-fechas")
    public Response obtenerActividadesEntreFechas(
            @RequestParam("inicio") Date rangoInicio,
            @RequestParam("fin") Date rangoFin) {
        return Response.ok().setPayload (actividadService.obtenerActividadesEntreFechas(rangoInicio, rangoFin));
    }
}


