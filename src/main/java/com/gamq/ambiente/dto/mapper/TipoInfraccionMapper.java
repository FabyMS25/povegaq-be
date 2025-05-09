package com.gamq.ambiente.dto.mapper;

import com.gamq.ambiente.dto.ReglamentoDto;
import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.dto.TipoInfraccionDto;
import com.gamq.ambiente.model.Reglamento;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.TipoInfraccion;

public class TipoInfraccionMapper {
    public static TipoInfraccionDto toTipoInfraccionDto(TipoInfraccion tipoInfraccion){
        return new TipoInfraccionDto()
                .setUuid(tipoInfraccion.getUuid())
                .setValorUFV(tipoInfraccion.getValorUFV())
                .setGrado(tipoInfraccion.getGrado())
                .setEstado(tipoInfraccion.isEstado())
                .setTipoContribuyenteDto(tipoInfraccion.getTipoContribuyente() == null? null: new TipoContribuyenteDto()
                        .setUuid(tipoInfraccion.getTipoContribuyente().getUuid())
                        .setDescripcion(tipoInfraccion.getTipoContribuyente().getDescripcion())
                        .setCodigo(tipoInfraccion.getTipoContribuyente().getCodigo())
                        .setEstado(tipoInfraccion.getTipoContribuyente().isEstado())
                )
                .setReglamentoDto(tipoInfraccion.getReglamento() == null? null: new ReglamentoDto()
                        .setUuid(tipoInfraccion.getReglamento().getUuid())
                        .setCodigo(tipoInfraccion.getReglamento().getCodigo())
                        .setDescripcion(tipoInfraccion.getReglamento().getDescripcion())
                        .setFechaEmision(tipoInfraccion.getReglamento().getFechaEmision())
                        .setActivo(tipoInfraccion.getReglamento().isActivo())
                        .setEstado(tipoInfraccion.getReglamento().isEstado())
                )
                ;

    }

    public static TipoInfraccion toTipoInfraccion(TipoInfraccionDto tipoInfraccionDto){
        return new TipoInfraccion()
                .setUuid(tipoInfraccionDto.getUuid())
                .setValorUFV(tipoInfraccionDto.getValorUFV())
                .setGrado(tipoInfraccionDto.getGrado())
                .setEstado(tipoInfraccionDto.isEstado())
                .setTipoContribuyente(tipoInfraccionDto.getTipoContribuyenteDto() == null? null: new TipoContribuyente()
                        .setUuid(tipoInfraccionDto.getTipoContribuyenteDto().getUuid())
                        .setDescripcion(tipoInfraccionDto.getTipoContribuyenteDto().getDescripcion())
                        .setCodigo(tipoInfraccionDto.getTipoContribuyenteDto().getCodigo())
                        .setEstado(tipoInfraccionDto.getTipoContribuyenteDto().isEstado())
                )
                .setReglamento(tipoInfraccionDto.getReglamentoDto()== null? null: new Reglamento()
                        .setUuid(tipoInfraccionDto.getReglamentoDto().getUuid())
                        .setCodigo(tipoInfraccionDto.getReglamentoDto().getCodigo())
                        .setDescripcion(tipoInfraccionDto.getReglamentoDto().getDescripcion())
                        .setFechaEmision(tipoInfraccionDto.getReglamentoDto().getFechaEmision())
                        .setActivo(tipoInfraccionDto.getReglamentoDto().isActivo())
                        .setEstado(tipoInfraccionDto.getReglamentoDto().isEstado())
                )

                ;
   }
}
