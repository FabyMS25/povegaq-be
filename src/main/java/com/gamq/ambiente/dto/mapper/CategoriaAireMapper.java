package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.CategoriaAireDto;
import com.gamq.ambiente.model.CategoriaAire;

public class CategoriaAireMapper {
    public static CategoriaAireDto toCategoriaAireDto(CategoriaAireDto categoriaAireDto){
        return new CategoriaAireDto()
                .setUuid(categoriaAireDto.getUuid())
                .setCategoria(categoriaAireDto.getCategoria())
                .setDescripcion(categoriaAireDto.getDescripcion())
                .setColor(categoriaAireDto.getColor())
                .setNorma(categoriaAireDto.getNorma())
                .setValorMaximo(categoriaAireDto.getValorMaximo())
                .setValorMinimo(categoriaAireDto.getValorMinimo())
                .setEstado(categoriaAireDto.isEstado());
    }

    public static CategoriaAire toCategoriaAire(CategoriaAire categoriaAire){
        return new CategoriaAire()
                .setUuid(categoriaAire.getUuid())
                .setCategoria(categoriaAire.getCategoria())
                .setDescripcion(categoriaAire.getDescripcion())
                .setColor(categoriaAire.getColor())
                .setNorma(categoriaAire.getNorma())
                .setValorMaximo(categoriaAire.getValorMaximo())
                .setValorMinimo(categoriaAire.getValorMinimo())
                .setEstado(categoriaAire.isEstado());
    }
}
