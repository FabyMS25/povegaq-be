package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/notificacion")
@Tag(name = "Notificacion", description = "API REST para gestiónar notificaciones")
public class NotificacionController {
    @Autowired
    NotificacionService notificacionService;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar una Notificacionpor el UUID", description = "Retorna los datos de una Notificacion")
    public Response getNotificacionByUuid(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(notificacionService.obtenerNotificacionPorUuid(uuid));
    }

    @GetMapping()
    @Operation(summary = "Listar todas las Notificaciones", description = "Obtiene la lista de Notificaciones")
    public Response getAllNotificaciones(){ return Response.ok().setPayload(notificacionService.obtenerNotificaciones());}

    @GetMapping("por-tipoNotificacion/{tipoNotificacion}")
    @Operation(summary = "Listar notificaciones por su tipo", description = "Obtiene la lista de Notificaciones por tipo de notificacion")
    public Response getNotificacionesByTipoNotificacion(@PathVariable("tipoNotificacion")TipoNotificacion tipoNotificacion){
        try {
            return Response.ok().setPayload(notificacionService.obtenerPorTipoNotificacion(tipoNotificacion));
        }
        catch (IllegalArgumentException e) {
            return Response.badRequest().setPayload("Tipo de notificación inválido: " + tipoNotificacion);//("Tipo de notificación inválido: " + tipo);
        }
    }

    @PostMapping()
    @Operation(
            summary = "Crear nueva Notificacion",
            description = "Registra los datos de una notificacion"
    )
    public Response createNotificacion(@Valid @RequestBody NotificacionDto notificacionDto){
        return Response.ok().setPayload(notificacionService.crearNotificacion(notificacionDto));
    }

    @GetMapping("/previa-vista/{uuidInspeccion}")
    @Operation(
            summary = "Muestra una vista de una Posible Notificacion",
            description = "Despliega los datos de una notificacion"
    )
    public Response getNotificacionPreviaVistaByUuidInspeccion(@PathVariable("uuidInspeccion") String uuidInspeccion) {
        return Response.ok().setPayload(notificacionService.generarNotificacionVistaPrevia(uuidInspeccion));
    }

    @PutMapping()
    @Operation(
            summary = "Modificar una Notificacion",
            description = "Modificar los datos de una Notificacion  existente"
    )
    public Response updateNotificacion(@Valid @RequestBody NotificacionDto notificacionDto){
        return Response.ok().setPayload(notificacionService.actualizarNotificacion(notificacionDto));
    }

    @PutMapping("/{uuid}/tipoNotificacion")
    @Operation(
            summary = "Modifica el Tipo de una Notificacion por UUID",
            description = "Modificar el tipo de la notificacion existente"
    )
    public Response updateTipoNotificacionByUuidNotificacion(@PathVariable String uuid,
                                                             @RequestBody TipoNotificacion nuevoTipoNotificacion){
        return  Response.ok().setPayload(notificacionService.actualizarTipoNotificacion(uuid,nuevoTipoNotificacion));
    }

    @PutMapping("/{uuid}/estadoNotificacion")
    @Operation(
            summary = "Modifica el Estado de una Notificacion por UUID",
            description = "Modificar el Estado de una Notificacion existente"
    )
    public Response updateEstadoNotificacionByUuidNotificacion(@PathVariable String uuid,
                                                               @RequestBody EstadoNotificacion nuevoEstadoNotificacion){
        return  Response.ok().setPayload(notificacionService.actualizarEstadoNotificacion(uuid, nuevoEstadoNotificacion));
    }

    @DeleteMapping("/{uuid}")
    @Operation(
            summary = "Eliminar una Notificacion",
            description = "Eliminar una Notificacion por su uuid"
    )
    public Response deleteNotificacion(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(notificacionService.eliminarNotificacion(uuid));
    }

    @GetMapping("/vencidas")
    @Operation(
            summary = "Listar las Notificaciones Vencidas",
            description = "Obtiene una lista de notificaciones vencidas"
    )
    public Response getNotificacionesVencidas() {
        return Response.ok().setPayload(notificacionService.obtenerNotificacionesPorFechaAsistenciaVencida());
    }

    @PostMapping("/generar-notificaciones")
    public Response generarNotificacionesMasivas(@RequestHeader Map<String, String> headers){
        if (!headers.containsKey("usuariouuid") || headers.get("usuariouuid") == null || headers.get("usuariouuid").trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'usuariouuid' es obligatorio.");
        }

        if (!headers.containsKey("usuarionombrecompleto") || headers.get("usuarionombrecompleto") == null || headers.get("usuarionombrecompleto").trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'usuarionombrecompleto' es obligatorio.");
        }
        String usuarioUuid = headers.get("usuariouuid").isEmpty()?"":headers.get("usuariouuid");
        String usuarioNombreCompleto = headers.get("usuarionombrecompleto").isEmpty()?"":headers.get("usuarionombrecompleto");
        notificacionService.generarNotificacionesFallidas(usuarioUuid, usuarioNombreCompleto);
        return Response.ok().setPayload("Notificaciones generadas para inspecciones fallidas");
    }
    @GetMapping("/ultima-notificacion/{uuidVehiculo}")
    public Response lastNotificacionByUuidVehiculo(@PathVariable("uuidVehiculo") String uuidVehiculo){
        return Response.ok().setPayload(notificacionService.obtenerUltimaNotificacionPorUuidVehiculo(uuidVehiculo));
    }
}
