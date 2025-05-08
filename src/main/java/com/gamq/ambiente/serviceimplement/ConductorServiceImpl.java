package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.ConductorDto;
import com.gamq.ambiente.dto.mapper.ConductorMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Conductor;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.repository.ConductorRepository;
import com.gamq.ambiente.repository.TipoContribuyenteRepository;
import com.gamq.ambiente.service.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ConductorServiceImpl implements ConductorService {
    @Autowired
    ConductorRepository conductorRepository;
    @Autowired
    TipoContribuyenteRepository tipoContribuyenteRepository;
    @Override
    public ConductorDto obtenerConductorPorUuid(String uuid) {
        Optional<Conductor> conductorOptional = conductorRepository.findByUuid(uuid);
        if(conductorOptional.isPresent()){
            return ConductorMapper.toConductorDto(conductorOptional.get());
        }
        throw new ResourceNotFoundException("Conductor", "uuid", uuid);
    }

    @Override
    public ConductorDto obtenerConductorPorNumeroDocumento(String numeroDocumento) {
        Optional<Conductor> conductorOptional = conductorRepository.findByNumeroDocumento(numeroDocumento);
        if(conductorOptional.isPresent()){
            return ConductorMapper.toConductorDto(conductorOptional.get());
        }
        throw new ResourceNotFoundException("Conductor", "numero documento", numeroDocumento);
    }

    @Override
    public List<ConductorDto> obtenerConductores() {
        List<Conductor> conductorList = conductorRepository.findAll();
        return  conductorList.stream().map( conductor -> {
            return  ConductorMapper.toConductorDto(conductor);
        }).collect(Collectors.toList());
    }

    @Override
    public ConductorDto crearConductor(ConductorDto conductorDto) {
        if (conductorDto.getTipoContribuyenteDto() == null || conductorDto.getTipoContribuyenteDto().getUuid() == null) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "El uuid de tipoContribuyenteDto no puede ser vacío");
        }

        String numeroDocumento = conductorDto.getNumeroDocumento();
        if(numeroDocumento==null){ throw new ResourceNotFoundException("Conductor","numero documento", numeroDocumento);}
        Optional<Conductor> conductorOptional = conductorRepository.findByNumeroDocumento(numeroDocumento);
        if(conductorOptional.isEmpty()){
            Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByUuid(conductorDto.getTipoContribuyenteDto().getUuid());
            if(tipoContribuyenteOptional.isPresent()) {
                Conductor nuevoConductor = ConductorMapper.toConductor(conductorDto);
                nuevoConductor.setTipoContribuyente( tipoContribuyenteOptional.get());
                return ConductorMapper.toConductorDto(conductorRepository.save(nuevoConductor));
            }
            else {
                throw new ResourceNotFoundException("Tipo Contribuyente", "uuid", conductorDto.getTipoContribuyenteDto().getUuid());
            }
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Conductor ya existe");
    }

    @Override
    public ConductorDto actualizarConductor(ConductorDto conductorDto) {
        if (conductorDto.getTipoContribuyenteDto() == null || conductorDto.getTipoContribuyenteDto().getUuid() == null) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "El uuid de tipoContribuyenteDto no puede ser vacío");
        }
        Optional<Conductor> conductorOptional = conductorRepository.findByUuid(conductorDto.getUuid());
        if(conductorOptional.isPresent()) {
            if (!conductorRepository.exitsConductorLikeNumeroDocumento(conductorDto.getNumeroDocumento().toLowerCase(), conductorDto.getUuid())) {
                Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByUuid(conductorDto.getTipoContribuyenteDto().getUuid());
                if(tipoContribuyenteOptional.isPresent()) {
                    Conductor updateConductor = ConductorMapper.toConductor(conductorDto);
                    updateConductor.setIdConductor(conductorOptional.get().getIdConductor());
                    updateConductor.setTipoContribuyente( tipoContribuyenteOptional.get());
                    return ConductorMapper.toConductorDto(conductorRepository.save(updateConductor));
                }
                else {
                    throw new ResourceNotFoundException("Tipo Contribuyente", "uuid", conductorDto.getTipoContribuyenteDto().getUuid());
                }
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Conductor ya existe");
            }
        }
        throw new ResourceNotFoundException("Conductor", "uuid", conductorDto.getUuid());
    }

    @Override
    public ConductorDto eliminarConductor(String uuid) {
        Conductor conductorQBE = new Conductor(uuid);
        Optional<Conductor> optionalConductor = conductorRepository.findOne(Example.of(conductorQBE));
        if(optionalConductor.isPresent()){
            Conductor conductor = optionalConductor.get();
            if(!conductor.getInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el Conductor ya esta siendo usado por las inspecciones");
            }
            conductorRepository.delete(conductor);
            return ConductorMapper.toConductorDto(conductor);
        }
        throw new ResourceNotFoundException("Conductor","uuid", uuid);
    }
}
