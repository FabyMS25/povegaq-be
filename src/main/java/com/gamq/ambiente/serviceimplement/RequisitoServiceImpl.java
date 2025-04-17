package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.RequisitoDto;
import com.gamq.ambiente.dto.mapper.RequisitoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Requisito;
import com.gamq.ambiente.repository.RequisitoRepository;
import com.gamq.ambiente.service.RequisitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RequisitoServiceImpl implements RequisitoService {
    @Autowired
    RequisitoRepository requisitoRepository;
    @Override
    public RequisitoDto obtenerRequisitoPorUuid(String uuid) {
        Optional<Requisito> requisitoOptional = requisitoRepository.findByUuid(uuid);
        if(requisitoOptional.isPresent()){
            return RequisitoMapper.toRequisitoDto(requisitoOptional.get());
        }
        throw new ResourceNotFoundException("Requisito", "uuid", uuid);
    }

    @Override
    public RequisitoDto obtenerRequisitoPorDescripcion(String descripcion) {
        Optional<Requisito> requisitoOptional = requisitoRepository.findByDescripcion(descripcion);
        if(requisitoOptional.isPresent()){
            return RequisitoMapper.toRequisitoDto(requisitoOptional.get());
        }
        throw new ResourceNotFoundException("Requisito", "descripcion", descripcion.toString());
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
        if(descripcion==null){ throw new ResourceNotFoundException("Requisito","descripcion", descripcion);}
        Optional<Requisito> requisitoOptional = requisitoRepository.findByDescripcion(descripcion);
        if(requisitoOptional.isEmpty()){
            Requisito nuevoRequisito = RequisitoMapper.toRequisito(requisitoDto);
            return RequisitoMapper.toRequisitoDto(requisitoRepository.save(nuevoRequisito));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Requisito ya existe");
    }

    @Override
    public RequisitoDto actualizarRequisito(RequisitoDto requisitoDto) {
        Optional<Requisito> requisitoOptional = requisitoRepository.findByUuid(requisitoDto.getUuid());
        if(requisitoOptional.isPresent()) {
            if (!requisitoRepository.exitsRequisitoLikeDescripcion(requisitoDto.getDescripcion().toLowerCase(), requisitoDto.getUuid())) {
                Requisito updateRequisito = RequisitoMapper.toRequisito(requisitoDto);
                updateRequisito.setIdRequisito(requisitoOptional.get().getIdRequisito());
                return RequisitoMapper.toRequisitoDto(requisitoRepository.save(updateRequisito));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Requisito ya existe");
            }
        }
        throw new ResourceNotFoundException("Requisito", "uuid",requisitoDto.getUuid());
    }

    @Override
    public RequisitoDto eliminarRequisito(String uuid) {
        Requisito requisitoQBE = new Requisito(uuid);
        Optional<Requisito> optionalRequisito = requisitoRepository.findOne(Example.of(requisitoQBE));
        if(optionalRequisito.isPresent()){
            Requisito requisito = optionalRequisito.get();
            if(!requisito.getRequisitoInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el requisito ya esta siendo usado por las inspecciones");
            }
            requisitoRepository.delete(requisito);
            return RequisitoMapper.toRequisitoDto(requisito);
        }
        throw new ResourceNotFoundException("Requisito","uuid", uuid);
    }
}
