package com.gamq.ambiente.controller;

import com.gamq.ambiente.dto.CategoriaAireDto;
import com.gamq.ambiente.dto.response.Response;
import com.gamq.ambiente.dto.response.Status;
import com.gamq.ambiente.service.CategoriaAireService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categoriaAire")
@Tag(name = "Categoria de Aire", description = "API REST para gestionar categorias de aire")
public class CategoriaAireController {
    @Autowired
    CategoriaAireService categoriaAireService;

    @GetMapping("/uuid/{uuid}")
    public Response getCategoriaAireByUuid(@PathVariable("uuid") String uuid) {
        return Response.ok().setPayload(categoriaAireService.obtenerCategoriaAirePorUuid(uuid));
    }

    @GetMapping("/categoria/{categoria}")
    public Response getCategoriaAireByDescripcion(@PathVariable("categoria") String categoria){
        return Response.ok().setPayload(categoriaAireService.obtenerCategoriaAirePorCategoria(categoria));
    }

    @GetMapping()
    public Response getAllCategoriaAires(){
        return Response.ok().setPayload(categoriaAireService.obtenerCategoriasAire());
    }

    @GetMapping("/activas")
    public Response obtenerCategoriasAireActivas(){
        return  Response.ok().setPayload(categoriaAireService.obtenerCategoriasAireActivas());
    }

    @PostMapping()
    public ResponseEntity<Response<CategoriaAireDto>> createCategoriaAire(@Valid @RequestBody CategoriaAireDto categoriaAireDto){
        Response<CategoriaAireDto> response = Response.<CategoriaAireDto>created().setStatus(Status.OK).setPayload(categoriaAireService.crearCategoriaAire(categoriaAireDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping()
    public Response updateCategoriaAire(@Valid @RequestBody CategoriaAireDto CategoriaAireDto){
        return Response.ok().setPayload(categoriaAireService.actualizarCategoriaAire(CategoriaAireDto));
    }

    @PutMapping("/{uuidCategoriaAire}/cambiar-activo")
    public Response updateActividadActivo(@PathVariable String uuidCategoriaAire,
                                          @RequestParam boolean activo){
        return Response.ok().setPayload(categoriaAireService.actualizarCategoriaAireActivo(uuidCategoriaAire, activo));
    }

    @DeleteMapping("/{uuid}")
    public Response deleteCategoriaAire(@PathVariable("uuid") String uuid){
        categoriaAireService.eliminarCategoriaAire(uuid);
        return Response.noContent().setPayload("La categoria fue eliminado exitosamente");
    }
}
