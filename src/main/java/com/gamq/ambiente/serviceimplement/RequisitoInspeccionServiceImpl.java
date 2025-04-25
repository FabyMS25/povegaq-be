package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InspeccionRequisitoInspeccionDto;
import com.gamq.ambiente.dto.RequisitoDto;
import com.gamq.ambiente.dto.RequisitoInspeccionDto;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.dto.mapper.RequisitoInspeccionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.DetalleInspeccion;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Requisito;
import com.gamq.ambiente.model.RequisitoInspeccion;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.RequisitoInspeccionRepository;
import com.gamq.ambiente.repository.RequisitoRepository;
import com.gamq.ambiente.service.RequisitoInspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RequisitoInspeccionServiceImpl implements RequisitoInspeccionService {
    @Autowired
    RequisitoInspeccionRepository requisitoInspeccionRepository;
    @Autowired
    InspeccionRepository inspeccionRepository;
    @Autowired
    RequisitoRepository requisitoRepository;
    @Autowired
    private EntidadHelperServiceImpl entidadHelper;

    @Override
    public RequisitoInspeccionDto obtenerRequisitoInspeccionPorUuid(String uuid) {
        Optional<RequisitoInspeccion> requisitoInspeccionOptional = requisitoInspeccionRepository.findByUuid(uuid);
        if(requisitoInspeccionOptional.isPresent()){
            return RequisitoInspeccionMapper.toRequisitoInspeccionDto(requisitoInspeccionOptional.get());
        }
        throw new ResourceNotFoundException("Requisito Inspeccion", "uuid", uuid);
    }

    @Override
    public List<RequisitoInspeccionDto> obtenerRequisitoInspeccionPorUuidInspeccion(String uuidInspeccion) {
        List<RequisitoInspeccion> requisitoInspeccionList = requisitoInspeccionRepository.findByUuidInspeccion(uuidInspeccion);
        return requisitoInspeccionList.stream().map( requisitoInspeccion -> {
            return RequisitoInspeccionMapper.toRequisitoInspeccionDto(requisitoInspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<RequisitoInspeccionDto> obtenerRequisitoInspecciones() {
        List<RequisitoInspeccion> requisitoInspeccionList = requisitoInspeccionRepository.findAll();
        return  requisitoInspeccionList.stream().map( requisitoInspeccion -> {
            return  RequisitoInspeccionMapper.toRequisitoInspeccionDto(requisitoInspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public RequisitoInspeccionDto crearRequisitoInspeccion(RequisitoInspeccionDto requisitoInspeccionDto) {
        if(!requisitoInspeccionRepository.exitsByUuidInspeccionAndUuidRequisito(requisitoInspeccionDto.getInspeccionDto().getUuid(), requisitoInspeccionDto.getRequisitoDto().getUuid())){
            Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(requisitoInspeccionDto.getInspeccionDto().getUuid());
            if(inspeccionOptional.isEmpty()){
                throw new ResourceNotFoundException("inspeccion", "uuid", requisitoInspeccionDto.getInspeccionDto().getUuid());
            }
            Optional<Requisito> requisitoOptional = requisitoRepository.findByUuid(requisitoInspeccionDto.getRequisitoDto().getUuid());
            if(requisitoOptional.isEmpty()){
                throw new ResourceNotFoundException("requisito", "uuid", requisitoInspeccionDto.getRequisitoDto().getUuid());
            }

            RequisitoInspeccion nuevoRequisitoInspeccion = RequisitoInspeccionMapper.toRequisitoInspeccion(requisitoInspeccionDto);
            nuevoRequisitoInspeccion.setInspeccion(inspeccionOptional.get());
            nuevoRequisitoInspeccion.setRequisito(requisitoOptional.get());
            return RequisitoInspeccionMapper.toRequisitoInspeccionDto(requisitoInspeccionRepository.save(nuevoRequisitoInspeccion));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Requisito ya existe en la inspeccion");
    }

    @Override
    public RequisitoInspeccionDto actualizarRequisitoInspeccion(RequisitoInspeccionDto requisitoInspeccionDto) {
        Optional<RequisitoInspeccion> requisitoInspeccionOptional = requisitoInspeccionRepository.findByUuid(requisitoInspeccionDto.getUuid());
        if(requisitoInspeccionOptional.isPresent()) {
            if (!requisitoInspeccionRepository.exitsLikeUuidRequisitoAndUuidInspeccion(requisitoInspeccionDto.getInspeccionDto().getUuid(),
                                                                                       requisitoInspeccionDto.getRequisitoDto().getUuid(),
                                                                                       requisitoInspeccionDto.getUuid())) {
                Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(requisitoInspeccionDto.getInspeccionDto().getUuid());
                if(inspeccionOptional.isEmpty()){
                    throw new ResourceNotFoundException("inspeccion", "uuid", requisitoInspeccionDto.getInspeccionDto().getUuid());
                }
                Optional<Requisito> requisitoOptional = requisitoRepository.findByUuid(requisitoInspeccionDto.getRequisitoDto().getUuid());
                if(requisitoOptional.isEmpty()){
                    throw new ResourceNotFoundException("requisito", "uuid", requisitoInspeccionDto.getRequisitoDto().getUuid());
                }

                RequisitoInspeccion updateRequisitoInspeccion = RequisitoInspeccionMapper.toRequisitoInspeccion(requisitoInspeccionDto);
                updateRequisitoInspeccion.setIdRequisitoInspeccion(requisitoInspeccionOptional.get().getIdRequisitoInspeccion());
                updateRequisitoInspeccion.setInspeccion(inspeccionOptional.get());
                updateRequisitoInspeccion.setRequisito(requisitoOptional.get());
                return RequisitoInspeccionMapper.toRequisitoInspeccionDto(requisitoInspeccionRepository.save(updateRequisitoInspeccion));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Requisito Inspeccion ya existe");
            }
        }
        throw new ResourceNotFoundException("Requisito Inspeccion", "uuid",requisitoInspeccionDto.getUuid());
    }

    @Override
    public RequisitoInspeccionDto eliminarRequisitoInspeccion(String uuid) {
        RequisitoInspeccion requisitoInspeccionQBE = new RequisitoInspeccion(uuid);
        Optional<RequisitoInspeccion> optionalRequisitoInspeccion = requisitoInspeccionRepository.findOne(Example.of(requisitoInspeccionQBE));
        if(optionalRequisitoInspeccion.isPresent()){
            RequisitoInspeccion requisitoInspeccion = optionalRequisitoInspeccion.get();
            requisitoInspeccionRepository.delete(requisitoInspeccion);
            return RequisitoInspeccionMapper.toRequisitoInspeccionDto(requisitoInspeccion);
        }
        throw new ResourceNotFoundException("Requisito Inspeccion","uuid", uuid);
    }

    @Override
    public List<RequisitoInspeccionDto> addRequisitoInspeccionToInspeccion(InspeccionRequisitoInspeccionDto inspeccionRequisitoInspeccionDto) {
        Inspeccion inspeccion = entidadHelper.obtenerInspeccion(inspeccionRequisitoInspeccionDto.getUuidInspeccion());

        List<RequisitoInspeccion> requisitoInspeccionList = inspeccionRequisitoInspeccionDto.getRequisitoInspeccionDtoList().stream().map( requisitoInspeccionDto -> {
            if(!requisitoInspeccionRepository.exitsByUuidInspeccionAndUuidRequisito(inspeccion.getUuid(),requisitoInspeccionDto.getRequisitoDto().getUuid())) {
                Requisito requisito = entidadHelper.obtenerRequisito(requisitoInspeccionDto.getRequisitoDto().getUuid());
                RequisitoInspeccion nuevoRequisitoInspecion = crearNuevoRequisitoInspeccion(requisitoInspeccionDto, inspeccion,requisito);
                return  nuevoRequisitoInspecion;
            }
            else { return null;}

        }).filter(Objects::nonNull)
        .collect(Collectors.toList());
        inspeccion.setRequisitoInspeccionList(requisitoInspeccionList);
        return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(inspeccion)).getRequisitoInspeccionDtoList();
    }
    private RequisitoInspeccion crearNuevoRequisitoInspeccion(RequisitoInspeccionDto requisitoInspeccionDto, Inspeccion inspeccion, Requisito requisito){
        RequisitoInspeccion requisitoInspeccion = RequisitoInspeccionMapper.toRequisitoInspeccion(requisitoInspeccionDto);
        requisitoInspeccion.setRequisito(requisito);
        requisitoInspeccion.setInspeccion(inspeccion);
        return requisitoInspeccion;
    }
}
