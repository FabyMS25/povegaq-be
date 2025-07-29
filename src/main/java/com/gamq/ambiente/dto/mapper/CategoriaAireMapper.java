package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.CategoriaAireDto;
import com.gamq.ambiente.model.CategoriaAire;

public class CategoriaAireMapper {
    public static CategoriaAireDto toCategoriaAireDto(CategoriaAire categoriaAire){
        return new CategoriaAireDto()
                .setUuid(categoriaAire.getUuid())
                .setCategoria(categoriaAire.getCategoria())
                .setDescripcion(categoriaAire.getDescripcion())
                .setColor(categoriaAire.getColor())
                .setRecomendacion(categoriaAire.getRecomendacion())
                .setNorma(categoriaAire.getNorma())
                .setValorMaximo(categoriaAire.getValorMaximo())
                .setValorMinimo(categoriaAire.getValorMinimo())
                .setActivo(categoriaAire.isActivo())
                .setEstado(categoriaAire.isEstado());
    }

    public static CategoriaAire toCategoriaAire(CategoriaAireDto categoriaAireDto){
        return new CategoriaAire()
                .setUuid(categoriaAireDto.getUuid())
                .setCategoria(categoriaAireDto.getCategoria())
                .setDescripcion(categoriaAireDto.getDescripcion())
                .setColor(categoriaAireDto.getColor())
                .setRecomendacion(categoriaAireDto.getRecomendacion())
                .setNorma(categoriaAireDto.getNorma())
                .setValorMaximo(categoriaAireDto.getValorMaximo())
                .setValorMinimo(categoriaAireDto.getValorMinimo())
                .setActivo(categoriaAireDto.isActivo())
                .setEstado(categoriaAireDto.isEstado());
    }
}
