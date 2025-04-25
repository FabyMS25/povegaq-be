package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.CertificadoDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.service.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/certificado")
public class CertificadoController {
    @Autowired
    CertificadoService certificadoService;

    @GetMapping("/uuid/{uuid}")
    public Response getCertificadoByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(certificadoService.obtenerCertificadoPorUuid(uuid));
    }

    @GetMapping("/codigo/{codigo}")
    public Response getCertificadoByCodigo(@PathVariable("codigo") String codigo){
        return Response.ok().setPayload(certificadoService.obtenerCertificadoPorCodigo(codigo));
    }

    @GetMapping()
    public Response getAllCertificados(){
        return Response.ok().setPayload(certificadoService.obtenerCertificados());
    }

    @PostMapping()
    public Response createCertificado(@Valid @RequestBody CertificadoDto certificadoDto){
        return Response.ok().setPayload(certificadoService.crearCertificado(certificadoDto));
    }

    @PutMapping()
    public Response updateCertificado(@Valid @RequestBody CertificadoDto certificadoDto){
        return Response.ok().setPayload(certificadoService.actualizarCertificado(certificadoDto));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteCertificado(@PathVariable("uuid") String uuid){
        return Response.ok().setPayload(certificadoService.eliminarCertificado(uuid));
    }

    @PutMapping("/{uuid}/cambiar-valido")
    public Response updateCertificadoValido(@PathVariable String uuid,
                                            @RequestParam boolean valido){
        return Response.ok().setPayload(certificadoService.actualizarCertificadoValido(uuid, valido));
    }
}
