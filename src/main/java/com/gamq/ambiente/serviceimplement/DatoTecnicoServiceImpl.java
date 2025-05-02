package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.DatoTecnicoDto;
import com.gamq.ambiente.dto.mapper.DatoTecnicoMapper;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.DatoTecnico;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.DatoTecnicoRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.DatoTecnicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DatoTecnicoServiceImpl implements DatoTecnicoService {
    @Autowired
    DatoTecnicoRepository datoTecnicoRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Override
    public DatoTecnicoDto obtenerDatoTecnicoPorUuid(String uuid) {
        Optional<DatoTecnico> datoTecnicoOptional = datoTecnicoRepository.findByUuid(uuid);
        if(datoTecnicoOptional.isPresent()){
            return DatoTecnicoMapper.toDatoTecnicoDto(datoTecnicoOptional.get());
        }
        throw new ResourceNotFoundException("Dato Tecnico", "uuid", uuid);
    }
/*
    @Override
    public DatoTecnicoDto obtenerDatoTecnicoPorUuidVehiculo(String vehiculoUuid) {
        Optional<DatoTecnico> datoTecnicoOptional = datoTecnicoRepository.findByUuidVehiculo(vehiculoUuid);
        if(datoTecnicoOptional.isPresent()){
            return DatoTecnicoMapper.toDatoTecnicoDto(datoTecnicoOptional.get());
        }
        throw new ResourceNotFoundException("Dato Tecnico", "uuid vehiculo", vehiculoUuid);
    }

    @Override
    public List<DatoTecnicoDto> obtenerDatoTecnicos() {
        List<DatoTecnico> datoTecnicoList = datoTecnicoRepository.findAll();
        return  datoTecnicoList.stream().map( datoTecnico -> {
            return  DatoTecnicoMapper.toDatoTecnicoDto(datoTecnico);
        }).collect(Collectors.toList());
    }*/

    @Override
    public DatoTecnicoDto crearDatoTecnico(DatoTecnicoDto datoTecnicoDto) {
        String uuidVehiculo = datoTecnicoDto.getVehiculoDto().getUuid();
        if(uuidVehiculo==null){ throw new ResourceNotFoundException("Dato Tecnico","uuid Vehiculo", uuidVehiculo);}
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(uuidVehiculo);
        if(!vehiculoOptional.isEmpty()){
            DatoTecnico nuevoDatoTecnico = DatoTecnicoMapper.toDatoTecnico(datoTecnicoDto);
            nuevoDatoTecnico.setVehiculo(vehiculoOptional.get());
            return DatoTecnicoMapper.toDatoTecnicoDto(datoTecnicoRepository.save(nuevoDatoTecnico));
        }
        throw new ResourceNotFoundException("Vehiculo", "uuid", uuidVehiculo);
    }

    @Override
    public DatoTecnicoDto actualizarDatoTecnico(DatoTecnicoDto datoTecnicoDto) {
        Optional<DatoTecnico> datoTecnicoOptional = datoTecnicoRepository.findByUuid(datoTecnicoDto.getUuid());
        if(datoTecnicoOptional.isPresent()) {
                DatoTecnico updateDatoTecnico = DatoTecnicoMapper.toDatoTecnico(datoTecnicoDto);
                updateDatoTecnico.setIdDatoTecnico(datoTecnicoOptional.get().getIdDatoTecnico());
                return DatoTecnicoMapper.toDatoTecnicoDto(datoTecnicoRepository.save(updateDatoTecnico));
        }
        throw new ResourceNotFoundException("DatoTecnico", "uuid",datoTecnicoDto.getUuid());
    }

    @Override
    public DatoTecnicoDto eliminarDatoTecnico(String uuid) {
        DatoTecnico datoTecnicoQBE = new DatoTecnico(uuid);
        Optional<DatoTecnico> optionalDatoTecnico = datoTecnicoRepository.findOne(Example.of(datoTecnicoQBE));
        if(optionalDatoTecnico.isPresent()){
            DatoTecnico datoTecnico = optionalDatoTecnico.get();
            datoTecnicoRepository.delete(datoTecnico);
            return DatoTecnicoMapper.toDatoTecnicoDto(datoTecnico);
        }
        throw new ResourceNotFoundException("Dato Tecnico","uuid", uuid);
    }
}
