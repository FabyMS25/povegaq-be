package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.ContaminanteDto;
import com.gamq.ambiente.dto.mapper.ContaminanteMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Contaminante;
import com.gamq.ambiente.repository.ContaminanteRepository;
import com.gamq.ambiente.service.ContaminanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContaminanteServiceImpl implements ContaminanteService {
    @Autowired
    ContaminanteRepository contaminanteRepository;

    @Override
    public ContaminanteDto obtenerContaminantePorUuid(String uuid) {
        Contaminante contaminante = obtenerContaminantePorUuidOThrow(uuid);
        return ContaminanteMapper.toContaminanteDto(contaminante);
    }

    @Override
    public ContaminanteDto obtenerContaminantePorNombre(String nombre) {
        Contaminante contaminante = contaminanteRepository.findByNombre(nombre)
                .orElseThrow(()-> new ResourceNotFoundException("Contaminante","nombre", nombre));
        return ContaminanteMapper.toContaminanteDto(contaminante);
    }

    @Override
    public List<ContaminanteDto> obtenerContaminantes() {
        List<Contaminante> contaminanteList = contaminanteRepository.findAll();
        return  contaminanteList.stream().map( contaminante -> {
            return  ContaminanteMapper.toContaminanteDto(contaminante);
        }).collect(Collectors.toList());
    }

    @Override
    public ContaminanteDto crearContaminante(ContaminanteDto contaminanteDto) {
        String nombre = contaminanteDto.getNombre();
        if(nombre==null || nombre.isBlank()){ throw new ResourceNotFoundException("Contaminante","nombre", nombre);}
        if (contaminanteRepository.findByNombre(nombre).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El contaminante ya existe");
        }
        Contaminante nuevoContaminante = ContaminanteMapper.toContaminante(contaminanteDto);
        return ContaminanteMapper.toContaminanteDto(contaminanteRepository.save(nuevoContaminante));
    }

    @Override
    public ContaminanteDto actualizarContaminante(ContaminanteDto contaminanteDto) {
        Contaminante contaminante = obtenerContaminantePorUuidOThrow(contaminanteDto.getUuid());
        if (contaminanteRepository.exitsContaminanteLikeNombre(contaminanteDto.getNombre().toLowerCase(), contaminanteDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el nombre del contaminante ya existe");
        }
        Contaminante updateContaminante = ContaminanteMapper.toContaminante(contaminanteDto);
        updateContaminante.setIdContaminante(contaminante.getIdContaminante());
        return ContaminanteMapper.toContaminanteDto(contaminanteRepository.save(updateContaminante));
    }

    @Override
    public ContaminanteDto eliminarContaminante(String uuid) {
        Contaminante contaminante = obtenerContaminantePorUuidOThrow(uuid);
        if(!contaminante.getMedicionAireList().isEmpty()){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el contaminante ya esta siendo usado por las medicion del aire");
        }
        contaminanteRepository.delete(contaminante);
        return ContaminanteMapper.toContaminanteDto(contaminante);
    }

    private Contaminante obtenerContaminantePorUuidOThrow(String uuid){
        return contaminanteRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Contaminante", "uuid",uuid));
    }
}
