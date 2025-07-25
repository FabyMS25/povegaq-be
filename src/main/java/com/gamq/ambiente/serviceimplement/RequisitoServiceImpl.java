package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.RequisitoDto;
import com.gamq.ambiente.dto.mapper.RequisitoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Requisito;
import com.gamq.ambiente.repository.RequisitoRepository;
import com.gamq.ambiente.service.RequisitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequisitoServiceImpl implements RequisitoService {
    @Autowired
    RequisitoRepository requisitoRepository;

    @Override
    public RequisitoDto obtenerRequisitoPorUuid(String uuid) {
        Requisito requisito = obtenerRequisitoPorUuidOThrow(uuid);
        return RequisitoMapper.toRequisitoDto(requisito);
    }

    @Override
    public RequisitoDto obtenerRequisitoPorDescripcion(String descripcion) {
        Requisito requisito = requisitoRepository.findByDescripcion(descripcion)
                .orElseThrow(()-> new ResourceNotFoundException("Requisito","descripcion", descripcion));
            return RequisitoMapper.toRequisitoDto(requisito);
    }

    @Override
    public List<RequisitoDto> obtenerRequisitos() {
        List<Requisito> requisitoList = requisitoRepository.findAll();
        return  requisitoList.stream().map( requisito -> {
            return  RequisitoMapper.toRequisitoDto(requisito);
        }).collect(Collectors.toList());
    }

    @Override
    public RequisitoDto crearRequisito(RequisitoDto requisitoDto) {
        String descripcion = requisitoDto.getDescripcion();
        if(descripcion==null || descripcion.isBlank()){ throw new ResourceNotFoundException("Requisito","descripcion", descripcion);}
        if (requisitoRepository.findByDescripcion(descripcion).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El requisito ya existe");
        }
        Requisito nuevoRequisito = RequisitoMapper.toRequisito(requisitoDto);
        return RequisitoMapper.toRequisitoDto(requisitoRepository.save(nuevoRequisito));
    }

    @Override
    public RequisitoDto actualizarRequisito(RequisitoDto requisitoDto) {
        Requisito requisito = obtenerRequisitoPorUuidOThrow(requisitoDto.getUuid());
        if (requisitoRepository.exitsRequisitoLikeDescripcion(requisitoDto.getDescripcion().toLowerCase(), requisitoDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Requisito ya existe");
        }
        Requisito updateRequisito = RequisitoMapper.toRequisito(requisitoDto);
        updateRequisito.setIdRequisito(requisito.getIdRequisito());
        return RequisitoMapper.toRequisitoDto(requisitoRepository.save(updateRequisito));
    }

    @Override
    public RequisitoDto eliminarRequisito(String uuid) {
        Requisito requisito = obtenerRequisitoPorUuidOThrow(uuid);
        if(!requisito.getRequisitoInspeccionList().isEmpty()){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el requisito ya esta siendo usado por las inspecciones");
        }
        requisitoRepository.delete(requisito);
        return RequisitoMapper.toRequisitoDto(requisito);
    }

    private Requisito obtenerRequisitoPorUuidOThrow(String uuid){
        return requisitoRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Requisito", "uuid",uuid));
    }
}
