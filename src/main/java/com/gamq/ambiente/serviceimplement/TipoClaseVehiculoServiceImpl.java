package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.TipoClaseVehiculoDto;
import com.gamq.ambiente.dto.mapper.TipoClaseVehiculoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.ClaseVehiculo;
import com.gamq.ambiente.model.TipoClaseVehiculo;
import com.gamq.ambiente.repository.ClaseVehiculoRepository;
import com.gamq.ambiente.repository.TipoClaseVehiculoRepository;
import com.gamq.ambiente.service.TipoClaseVehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TipoClaseVehiculoServiceImpl implements TipoClaseVehiculoService {
    @Autowired
    TipoClaseVehiculoRepository tipoClaseVehiculoRepository;
    @Autowired
    ClaseVehiculoRepository claseVehiculoRepository;

    @Override
    public TipoClaseVehiculoDto obtenerTipoClaseVehiculoPorUuid(String uuid) {
        TipoClaseVehiculo tipoClaseVehiculo = obtenerTipoClaseVehiculoPorUuidOThrow(uuid);
        return TipoClaseVehiculoMapper.toTipoClaseVehiculoDto(tipoClaseVehiculo);
    }

    @Override
    public TipoClaseVehiculoDto obtenerTipoClaseVehiculoPorNombre(String nombre) {
        TipoClaseVehiculo tipoClaseVehiculo = tipoClaseVehiculoRepository.findByNombre(nombre)
                .orElseThrow(()-> new ResourceNotFoundException("TipoClaseVehiculo","nombre", nombre));
        return TipoClaseVehiculoMapper.toTipoClaseVehiculoDto(tipoClaseVehiculo);
    }

    @Override
    public List<TipoClaseVehiculoDto> obtenerTipoClaseVehiculos() {
        List<TipoClaseVehiculo> tipoClaseVehiculoList = tipoClaseVehiculoRepository.findAll();
        return  tipoClaseVehiculoList.stream().map( tipoClaseVehiculo -> {
            return  TipoClaseVehiculoMapper.toTipoClaseVehiculoDto(tipoClaseVehiculo);
        }).collect(Collectors.toList());
    }

    @Override
    public TipoClaseVehiculoDto crearTipoClaseVehiculo(TipoClaseVehiculoDto tipoClaseVehiculoDto) {

        ClaseVehiculo claseVehiculo =claseVehiculoRepository.findByUuid(tipoClaseVehiculoDto.getClaseVehiculoDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Clase Vehiculo","uuid", tipoClaseVehiculoDto.getClaseVehiculoDto().getUuid()));

        String nombre = tipoClaseVehiculoDto.getNombre();
        if(nombre==null || nombre.isBlank()){ throw new ResourceNotFoundException("TipoClaseVehiculo","nombre", nombre);}
        if (tipoClaseVehiculoRepository.findByNombre(nombre).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El tipoClaseVehiculo ya existe");
        }
        TipoClaseVehiculo nuevoTipoClaseVehiculo = TipoClaseVehiculoMapper.toTipoClaseVehiculo(tipoClaseVehiculoDto);
        nuevoTipoClaseVehiculo.setClaseVehiculo(claseVehiculo);
        return TipoClaseVehiculoMapper.toTipoClaseVehiculoDto(tipoClaseVehiculoRepository.save(nuevoTipoClaseVehiculo));
    }

    @Override
    public TipoClaseVehiculoDto actualizarTipoClaseVehiculo(TipoClaseVehiculoDto tipoClaseVehiculoDto) {
        ClaseVehiculo claseVehiculo =claseVehiculoRepository.findByUuid(tipoClaseVehiculoDto.getClaseVehiculoDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Clase Vehiculo","uuid", tipoClaseVehiculoDto.getClaseVehiculoDto().getUuid()));

        TipoClaseVehiculo tipoClaseVehiculo = obtenerTipoClaseVehiculoPorUuidOThrow(tipoClaseVehiculoDto.getUuid());
        if (tipoClaseVehiculoRepository.exitsTipoClaseVehiculoLikeNombre(tipoClaseVehiculoDto.getNombre().toLowerCase(), tipoClaseVehiculoDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el TipoClaseVehiculo ya existe");
        }
        TipoClaseVehiculo updateTipoClaseVehiculo = TipoClaseVehiculoMapper.toTipoClaseVehiculo(tipoClaseVehiculoDto);
        updateTipoClaseVehiculo.setIdTipoClaseVehiculo(tipoClaseVehiculo.getIdTipoClaseVehiculo());
        updateTipoClaseVehiculo.setClaseVehiculo(claseVehiculo);

        return TipoClaseVehiculoMapper.toTipoClaseVehiculoDto(tipoClaseVehiculoRepository.save(updateTipoClaseVehiculo));
    }

    @Override
    public TipoClaseVehiculoDto eliminarTipoClaseVehiculo(String uuid) {
        TipoClaseVehiculo tipoClaseVehiculo = obtenerTipoClaseVehiculoPorUuidOThrow(uuid);
      //  if(!tipoClaseVehiculo.getTipoClaseVehiculoInspeccionList().isEmpty()){
      //      throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el tipoClaseVehiculo ya esta siendo usado por las inspecciones");
      //  }
        tipoClaseVehiculoRepository.delete(tipoClaseVehiculo);
        return TipoClaseVehiculoMapper.toTipoClaseVehiculoDto(tipoClaseVehiculo);
    }

    private TipoClaseVehiculo obtenerTipoClaseVehiculoPorUuidOThrow(String uuid){
        return tipoClaseVehiculoRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("TipoClaseVehiculo", "uuid",uuid));
    }
}
