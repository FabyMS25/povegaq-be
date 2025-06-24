package com.gamq.ambiente.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InspeccionRequestDto {
    private String uuidInspeccion;
    private List<DetalleInspeccionRequestDto> detalleInspeccionRequestDtoList;
}
