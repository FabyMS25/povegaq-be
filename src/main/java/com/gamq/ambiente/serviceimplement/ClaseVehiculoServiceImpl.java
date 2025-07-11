package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.ClaseVehiculoDto;
import com.gamq.ambiente.dto.mapper.ClaseVehiculoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.ClaseVehiculo;
import com.gamq.ambiente.repository.ClaseVehiculoRepository;
import com.gamq.ambiente.service.ClaseVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClaseVehiculoServiceImpl implements ClaseVehiculoService {
    @Autowired
    ClaseVehiculoRepository claseVehiculoRepository;

    @Override
    public ClaseVehiculoDto obtenerClaseVehiculoPorUuid(String uuid) {
        ClaseVehiculo claseVehiculo = obtenerClaseVehiculoPorUuidOThrow(uuid);
        return ClaseVehiculoMapper.toClaseVehiculoDto(claseVehiculo);
    }

    @Override
    public ClaseVehiculoDto obtenerClaseVehiculoPorNombre(String nombre) {
        ClaseVehiculo claseVehiculo = claseVehiculoRepository.findByNombre(nombre)
                .orElseThrow(()-> new ResourceNotFoundException("ClaseVehiculo","descripcion", nombre));
        return ClaseVehiculoMapper.toClaseVehiculoDto(claseVehiculo);
    }

    @Override
    public List<ClaseVehiculoDto> obtenerClaseVehiculos() {
        List<ClaseVehiculo> claseVehiculoList = claseVehiculoRepository.findAll();
        return  claseVehiculoList.stream().map( claseVehiculo -> {
            return  ClaseVehiculoMapper.toClaseVehiculoDto(claseVehiculo);
        }).collect(Collectors.toList());
    }

    @Override
    public ClaseVehiculoDto crearClaseVehiculo(ClaseVehiculoDto claseVehiculoDto) {
        String nombre = claseVehiculoDto.getNombre();
        if(nombre==null || nombre.isBlank()){ throw new ResourceNotFoundException("ClaseVehiculo","nombre", nombre);}
        if (claseVehiculoRepository.findByNombre(nombre).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El claseVehiculo ya existe");
        }
        ClaseVehiculo nuevoClaseVehiculo = ClaseVehiculoMapper.toClaseVehiculo(claseVehiculoDto);
        return ClaseVehiculoMapper.toClaseVehiculoDto(claseVehiculoRepository.save(nuevoClaseVehiculo));
    }

    @Override
    public ClaseVehiculoDto actualizarClaseVehiculo(ClaseVehiculoDto claseVehiculoDto) {
        ClaseVehiculo claseVehiculo = obtenerClaseVehiculoPorUuidOThrow(claseVehiculoDto.getUuid());
        if (claseVehiculoRepository.exitsClaseVehiculoLikeNombre(claseVehiculoDto.getNombre().toLowerCase(), claseVehiculoDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el ClaseVehiculo ya existe");
        }
        ClaseVehiculo updateClaseVehiculo = ClaseVehiculoMapper.toClaseVehiculo(claseVehiculoDto);
        updateClaseVehiculo.setIdClaseVehiculo(claseVehiculo.getIdClaseVehiculo());
        return ClaseVehiculoMapper.toClaseVehiculoDto(claseVehiculoRepository.save(updateClaseVehiculo));
    }

    @Override
    public ClaseVehiculoDto eliminarClaseVehiculo(String uuid) {
        ClaseVehiculo claseVehiculo = obtenerClaseVehiculoPorUuidOThrow(uuid);
       // if(!claseVehiculo.getClaseVehiculoList().isEmpty()){
       //     throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el claseVehiculo ya esta siendo usado por las inspecciones");
       // }
        claseVehiculoRepository.delete(claseVehiculo);
        return ClaseVehiculoMapper.toClaseVehiculoDto(claseVehiculo);
    }

    private ClaseVehiculo obtenerClaseVehiculoPorUuidOThrow(String uuid){
        return claseVehiculoRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("ClaseVehiculo", "uuid",uuid));
    }
}
