package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.EstacionDto;
import com.gamq.ambiente.dto.mapper.EstacionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Estacion;
import com.gamq.ambiente.repository.EstacionRepository;
import com.gamq.ambiente.service.EstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstacionServiceImpl implements EstacionService {
    @Autowired
    EstacionRepository estacionRepository;

    @Override
    public EstacionDto obtenerEstacionPorUuid(String uuid) {
        Estacion estacion = obtenerEstacionPorUuidOThrow(uuid);
        return EstacionMapper.toEstacionDto(estacion);
    }

    @Override
    public EstacionDto obtenerEstacionPorNombre(String nombre) {
        Estacion estacion = estacionRepository.findByNombre(nombre)
                .orElseThrow(()-> new ResourceNotFoundException("Estacion","nombre", nombre));
        return EstacionMapper.toEstacionDto(estacion);
    }

    @Override
    public List<EstacionDto> obtenerEstaciones() {
        List<Estacion> estacionList = estacionRepository.findAll();
        return  estacionList.stream().map( estacion -> {
            return  EstacionMapper.toEstacionDto(estacion);
        }).collect(Collectors.toList());
    }

    @Override
    public EstacionDto crearEstacion(EstacionDto estacionDto) {
        String nombre = estacionDto.getNombre();
        if(nombre==null || nombre.isBlank()){ throw new ResourceNotFoundException("Estacion","nombre", nombre);}
        if (estacionRepository.findByNombre(nombre).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El estacion ya existe");
        }
        Estacion nuevoEstacion = EstacionMapper.toEstacion(estacionDto);
        return EstacionMapper.toEstacionDto(estacionRepository.save(nuevoEstacion));
    }

    @Override
    public EstacionDto actualizarEstacion(EstacionDto estacionDto) {
        Estacion estacion = obtenerEstacionPorUuidOThrow(estacionDto.getUuid());
        if (estacionRepository.exitsEstacionLikeNombre(estacionDto.getNombre().toLowerCase(), estacionDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Estacion ya existe");
        }
        Estacion updateEstacion = EstacionMapper.toEstacion(estacionDto);
        updateEstacion.setIdEstacion(estacion.getIdEstacion());
        return EstacionMapper.toEstacionDto(estacionRepository.save(updateEstacion));
    }

    @Override
    public EstacionDto eliminarEstacion(String uuid) {
        Estacion estacion = obtenerEstacionPorUuidOThrow(uuid);
      //  if(!estacion.getEstacionInspeccionList().isEmpty()){
      //      throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el estacion ya esta siendo usado por las inspecciones");
      //  }
        estacionRepository.delete(estacion);
        return EstacionMapper.toEstacionDto(estacion);
    }

    private Estacion obtenerEstacionPorUuidOThrow(String uuid){
        return estacionRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Estacion", "uuid",uuid));
    }
}
