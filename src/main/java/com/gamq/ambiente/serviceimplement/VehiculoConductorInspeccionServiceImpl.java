package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.VehiculoConductorInspeccionDto;
import com.gamq.ambiente.dto.mapper.VehiculoConductorInspeccionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Conductor;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.model.VehiculoConductorInspeccion;
import com.gamq.ambiente.repository.ConductorRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.VehiculoConductorInspeccionRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.VehiculoConductorInspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VehiculoConductorInspeccionServiceImpl implements VehiculoConductorInspeccionService {
    @Autowired
    VehiculoConductorInspeccionRepository vehiculoConductorInspeccionRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    ConductorRepository conductorRepository;
    @Autowired
    InspeccionRepository inspeccionRepository;

    @Override
    public VehiculoConductorInspeccionDto obtenerVehiculoConductorInspeccionPorUuid(String uuid) {
        Optional<VehiculoConductorInspeccion> vehiculoConductorInspeccionOptional = vehiculoConductorInspeccionRepository.findByUuid(uuid);
        if(vehiculoConductorInspeccionOptional.isPresent()){
            return VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccionDto(vehiculoConductorInspeccionOptional.get());
        }
        throw new ResourceNotFoundException("VehiculoConductorInspeccion", "uuid", uuid);
    }

    @Override
    public VehiculoConductorInspeccionDto obtenerVehiculoConductorInspeccionPorUuidInspeccion(String uuidInspeccion) {
        Optional<VehiculoConductorInspeccion> vehiculoConductorInspeccionOptional = vehiculoConductorInspeccionRepository.findByUuidInspeccion(uuidInspeccion);
        if(vehiculoConductorInspeccionOptional.isPresent()){
            return VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccionDto(vehiculoConductorInspeccionOptional.get());
        }
        throw new ResourceNotFoundException("VehiculoConductorInspeccion", "uuidInspeccion", uuidInspeccion);
    }

    @Override
    public List<VehiculoConductorInspeccionDto> obtenerVehiculoConductorInspecciones() {
        List<VehiculoConductorInspeccion> vehiculoConductorInspeccionList = vehiculoConductorInspeccionRepository.findAll();
        return  vehiculoConductorInspeccionList.stream().map( vehiculoConductorInspeccion -> {
            return  VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccionDto(vehiculoConductorInspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public VehiculoConductorInspeccionDto crearVehiculoConductorInspeccion(VehiculoConductorInspeccionDto vehiculoConductorInspeccionDto) {
        String uuidInspeccion = vehiculoConductorInspeccionDto.getInspeccionDto().getUuid();
        if(uuidInspeccion==null){ throw new ResourceNotFoundException("VehiculoConductorInspeccion","uuidInspeccion", uuidInspeccion);}
        Optional<VehiculoConductorInspeccion> vehiculoConductorInspeccionOptional = vehiculoConductorInspeccionRepository.findByUuidInspeccion(uuidInspeccion);
        if(vehiculoConductorInspeccionOptional.isEmpty()){
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(vehiculoConductorInspeccionDto.getVehiculoDto().getUuid());
            Optional<Conductor> conductorOptional = conductorRepository.findByUuid(vehiculoConductorInspeccionDto.getConductorDto().getUuid());
            Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(vehiculoConductorInspeccionDto.getInspeccionDto().getUuid());

            if (vehiculoConductorInspeccionRepository.existsByVehiculoAndConductorAndInspeccion(vehiculoOptional.get().getUuid(), conductorOptional.get().getUuid(), inspeccionOptional.get().getUuid())) {
                throw new IllegalArgumentException("Ya existe una relación para este vehículo, conductor e inspección.");
            }

            if(vehiculoOptional.isPresent()) {
                if(conductorOptional.isPresent()) {
                    if(inspeccionOptional.isPresent()) {
                        //  Validar integridad lógica: la inspección pertenece al mismo vehículo
                        if (!inspeccionOptional.get().getVehiculo().getIdVehiculo().equals(vehiculoOptional.get().getIdVehiculo())) {
                            throw new IllegalArgumentException("La inspección no pertenece al vehículo especificado.");
                        }
                        VehiculoConductorInspeccion nuevoVehiculoConductorInspeccion = VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccion(vehiculoConductorInspeccionDto);
                        nuevoVehiculoConductorInspeccion.setVehiculo(vehiculoOptional.get());
                        nuevoVehiculoConductorInspeccion.setConductor(conductorOptional.get());
                        nuevoVehiculoConductorInspeccion.setInspeccion(inspeccionOptional.get());
                        return VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccionDto(vehiculoConductorInspeccionRepository.save(nuevoVehiculoConductorInspeccion));
                    }
                    else {
                        throw new ResourceNotFoundException("conductor", "uuid", vehiculoConductorInspeccionDto.getInspeccionDto().getUuid());
                    }
                }
                else {
                    throw new ResourceNotFoundException("conductor", "uuid", vehiculoConductorInspeccionDto.getConductorDto().getUuid());
                }
            }
            else {
                throw new ResourceNotFoundException("vehiculo", "uuid", vehiculoConductorInspeccionDto.getVehiculoDto().getUuid());
            }
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la Inspeccion ya tiene Vehiculo y Conductor registrado");
    }

    @Override
    public VehiculoConductorInspeccionDto actualizarVehiculoConductorInspeccion(VehiculoConductorInspeccionDto vehiculoConductorInspeccionDto) {
        Optional<VehiculoConductorInspeccion> vehiculoConductorInspeccionOptional = vehiculoConductorInspeccionRepository.findByUuid(vehiculoConductorInspeccionDto.getUuid());
        if(vehiculoConductorInspeccionOptional.isPresent()) {
          //  if (!vehiculoConductorInspeccionRepository.exitsVehiculoConductorInspeccionLikeDescripcion(VehiculoConductorInspeccionDto.getDescripcion().toLowerCase(), VehiculoConductorInspeccionDto.getUuid())) {
                VehiculoConductorInspeccion updateVehiculoConductorInspeccion = VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccion(vehiculoConductorInspeccionDto);
                updateVehiculoConductorInspeccion.setIdVehiculoConductorInspeccion(vehiculoConductorInspeccionOptional.get().getIdVehiculoConductorInspeccion());
                Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(vehiculoConductorInspeccionDto.getVehiculoDto().getUuid());
                Optional<Conductor> conductorOptional = conductorRepository.findByUuid(vehiculoConductorInspeccionDto.getConductorDto().getUuid());
                Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(vehiculoConductorInspeccionDto.getInspeccionDto().getUuid());
                if(vehiculoOptional.isPresent()) {
                    if(conductorOptional.isPresent()) {
                        if(inspeccionOptional.isPresent()) {
                            //  Validar integridad lógica: la inspección pertenece al mismo vehículo
                            if (!inspeccionOptional.get().getVehiculo().getIdVehiculo().equals(vehiculoOptional.get().getIdVehiculo())) {
                                throw new IllegalArgumentException("La inspección no pertenece al vehículo especificado.");
                            }
                            updateVehiculoConductorInspeccion.setVehiculo(vehiculoOptional.get());
                            updateVehiculoConductorInspeccion.setConductor(conductorOptional.get());
                            updateVehiculoConductorInspeccion.setInspeccion(inspeccionOptional.get());
                             return VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccionDto(vehiculoConductorInspeccionRepository.save(updateVehiculoConductorInspeccion));
                        }
                        else {
                            throw new ResourceNotFoundException("conductor", "uuid", vehiculoConductorInspeccionDto.getInspeccionDto().getUuid());
                        }
                    }
                    else {
                        throw new ResourceNotFoundException("conductor", "uuid", vehiculoConductorInspeccionDto.getConductorDto().getUuid());
                    }
                }
                else {
                    throw new ResourceNotFoundException("vehiculo", "uuid", vehiculoConductorInspeccionDto.getVehiculoDto().getUuid());
                }
          //  } else {
          //      throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el VehiculoConductorInspeccion ya existe");
         //   }
        }
        throw new ResourceNotFoundException("VehiculoConductorInspeccion", "uuid",vehiculoConductorInspeccionDto.getUuid());
    }

    @Override
    public VehiculoConductorInspeccionDto eliminarVehiculoConductorInspeccion(String uuid) {
        VehiculoConductorInspeccion vehiculoConductorInspeccionQBE = new VehiculoConductorInspeccion(uuid);
        Optional<VehiculoConductorInspeccion> optionalVehiculoConductorInspeccion = vehiculoConductorInspeccionRepository.findOne(Example.of(vehiculoConductorInspeccionQBE));
        if(optionalVehiculoConductorInspeccion.isPresent()){
            VehiculoConductorInspeccion vehiculoConductorInspeccion = optionalVehiculoConductorInspeccion.get();
          //  if(!vehiculoConductorInspeccion.getVehiculoConductorInspeccionInspeccionList().isEmpty()){
          //      throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el VehiculoConductorInspeccion ya esta siendo usado por las inspecciones");
          //  }
            vehiculoConductorInspeccionRepository.delete(vehiculoConductorInspeccion);
            return VehiculoConductorInspeccionMapper.toVehiculoConductorInspeccionDto(vehiculoConductorInspeccion);
        }
        throw new ResourceNotFoundException("VehiculoConductorInspeccion","uuid", uuid);
    }


}
