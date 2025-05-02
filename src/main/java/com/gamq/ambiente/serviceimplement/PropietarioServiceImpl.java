package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.PropietarioDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.mapper.PropietarioMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.PropietarioRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

public class PropietarioServiceImpl implements PropietarioService {
    @Autowired
    PropietarioRepository propietarioRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Override
    public PropietarioDto obtenerPropietarioPorUuid(String uuid) {
        Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(uuid);
        if(propietarioOptional.isPresent()){
            return PropietarioMapper.toPropietarioDto(propietarioOptional.get());
        }
        throw new ResourceNotFoundException("propietario", uuid, uuid);
    }

    @Override
    public List<PropietarioDto> obtenerPropietarios() {
        List<Propietario> propietarioList = propietarioRepository.findAll();
        return propietarioList.stream().map( propietario -> {
            return  PropietarioMapper.toPropietarioDto(propietario);
        }).collect(Collectors.toList());
    }

    @Override
    public PropietarioDto crearPropietario(PropietarioDto propietarioDto) {
        String nroDocumento = propietarioDto.getNroDocumento().trim().toLowerCase();

        if (propietarioRepository.findByNroDocumento(nroDocumento).isPresent()) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el nro de documento del propietario ya existe");
        }
        Propietario nuevoPropietario = PropietarioMapper.toPropietario(propietarioDto);
        List<Vehiculo>  vehiculoList = mapearVehiculos(propietarioDto.getVehiculoDtoList(), nuevoPropietario);
        nuevoPropietario.setVehiculoList(vehiculoList);
        return PropietarioMapper.toPropietarioDto(propietarioRepository.save(nuevoPropietario));
    }

    private List<Vehiculo> mapearVehiculos(List<VehiculoDto> vehiculoDtoList, Propietario nuevoPropietario){
        Set<String> placasVehiculo = new HashSet<>();
        List<Vehiculo> vehiculoList = new ArrayList<>();

        vehiculoDtoList.stream().forEach(vehiculoDto -> {

            Vehiculo vehiculo = obtenerVehiculo( vehiculoDto.getUuid());

            if ( vehiculo.getPropietario() == null || !vehiculo.getPropietario().getNroDocumento().contains(nuevoPropietario.getNroDocumento())){
                if ( placasVehiculo.contains(vehiculoDto.getPlaca().toLowerCase().trim())){
                    throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST,"la placa del vehiculo'" + vehiculoDto.getPlaca() + "' ya existe o es duplicado");
                }
                placasVehiculo.add(vehiculoDto.getPlaca().toLowerCase().trim());
                vehiculo.setPropietario(nuevoPropietario);
                vehiculoList.add(vehiculo);
            }
        } );
        return vehiculoList;
    }

    private Vehiculo obtenerVehiculo(String vehiculoUuid){
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(vehiculoUuid);
        if(vehiculoOptional.isEmpty()){
            throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "el uuid del vehiculo no existe");
        }
        return vehiculoOptional.get();
    }


    @Override
    public PropietarioDto actualizarPropietario(PropietarioDto propietarioDto) {
        String nroDocumento = propietarioDto.getNroDocumento().trim().toLowerCase();
        Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(propietarioDto.getUuid());
        if(propietarioOptional.isEmpty()){
            throw new ResourceNotFoundException("propietario", "uuid", propietarioDto.getUuid());
        }
        if(propietarioRepository.exitsPropietarioLikeNroDocumento(nroDocumento,propietarioDto.getUuid())){
            throw  new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,"el propietario ya existe");
        }
        Propietario updatePropietario= PropietarioMapper.toPropietario(propietarioDto);
        updatePropietario.setIdPropietario(propietarioOptional.get().getIdPropietario());

        propietarioOptional.get().getVehiculoList().forEach(vehiculo -> {
            vehiculo.setPropietario(null);
        });

        propietarioOptional.get().getVehiculoList().clear();
        List<Vehiculo> vehiculoList = mapearVehiculos(propietarioDto.getVehiculoDtoList(), propietarioOptional.get());
        updatePropietario.setVehiculoList(vehiculoList);

        return PropietarioMapper.toPropietarioDto(propietarioRepository.save(updatePropietario));
    }

    @Override
    public PropietarioDto eliminarPropietario(String uuid) {
        Propietario propietarioQBE = new Propietario(uuid);
        Optional<Propietario> propietarioOptional = propietarioRepository.findOne(Example.of(propietarioQBE));
        if(propietarioOptional.isPresent()){
            if (propietarioOptional.get().getVehiculoList().size() <= 0 ) {
                Propietario propietario = propietarioOptional.get();
                propietarioRepository.delete(propietario);
                return PropietarioMapper.toPropietarioDto(propietario);
            }
            else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, uuid);
            }
        }
        throw new ResourceNotFoundException("Propietario", "uuid", uuid);
    }

}
