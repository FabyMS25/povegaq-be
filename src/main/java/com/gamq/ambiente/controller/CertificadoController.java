package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.CertificadoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.scheduler.CertificadoScheduledTask;
import com.gamq.ambiente.service.CertificadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/certificado")
@Tag(name = "Certificado", description = "API REST para gestión de los certificados de inspeccion")
public class CertificadoController {
    @Autowired
    CertificadoService certificadoService;
    @Autowired
    CertificadoScheduledTask certificadoScheduledTask;

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Buscar certificado por UUID", description = "Retorna los datos del certificado de Inspeccion")
    public Response getCertificadoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(certificadoService.obtenerCertificadoPorUuid(uuid));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar certificado por codigo", description = "Retorna los datos del certificado de Inspeccion")
    public Response getCertificadoByCodigo(@PathVariable("codigo") String codigo){
        return Response.ok().setPayload(certificadoService.obtenerCertificadoPorCodigo(codigo));
    }

    @GetMapping()
    @Operation(summary = "Lista todos los certificado", description = "Retorna una lista de todos los certificado de Inspeccion")
    public Response getAllCertificados(){
        return Response.ok().setPayload(certificadoService.obtenerCertificados());
    }

    @PostMapping()
    @Operation(summary = "Crear nuevo certificado", description = "Registra los datos de un nuevo certificado")
    public ResponseEntity<Response<CertificadoDto>> createCertificado(@Valid @RequestBody CertificadoDto certificadoDto){
        Response<CertificadoDto> response = Response.<CertificadoDto>created().setStatus(Status.OK).setPayload(certificadoService.crearCertificado(certificadoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    @Operation(summary = "Modificar certificado", description = "Modifica los datos de un certificado existente")
    public Response updateCertificado(@Valid @RequestBody CertificadoDto certificadoDto){
        return Response.ok().setPayload(certificadoService.actualizarCertificado(certificadoDto));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Eliminar certificado por UUID", description = "Elimina logicamente un certificado existente por su uuid")
    public Response deleteCertificado(@PathVariable("uuid") String uuid){
        certificadoService.eliminarCertificado(uuid);
        return Response.noContent().setPayload("EL certificado fue eliminado exitosamente");
    }

    @PutMapping("/{uuid}/cambiar-valido")
    @Operation(summary = "Cambia a valido= true o no valido= false del certificado por UUID", description = "Cambia la validez de un certificado a valido o no valido")
    public Response updateCertificadoValido(@PathVariable String uuid,
                                            @RequestParam boolean valido){
        return Response.ok().setPayload(certificadoService.actualizarCertificadoValido(uuid, valido));
    }

    @PostMapping("/ejecutar-validez")
    public Response ejecutarVerificacionManual() {
        certificadoScheduledTask.ejecutarValidacionDiaria();
        return Response.ok().setPayload("Verificación de certificados ejecutada correctamente.");
    }
}
