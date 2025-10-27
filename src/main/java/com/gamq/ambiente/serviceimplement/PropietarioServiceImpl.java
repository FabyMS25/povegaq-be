package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.PropietarioDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.mapper.PropietarioMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.PropietarioRepository;
import com.gamq.ambiente.repository.TipoContribuyenteRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PropietarioServiceImpl implements PropietarioService {
    @Autowired
    PropietarioRepository propietarioRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    TipoContribuyenteRepository tipoContribuyenteRepository;

    @Override
    public PropietarioDto obtenerPropietarioPorUuid(String uuid) {
        Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(uuid);
        if(propietarioOptional.isPresent()){
            return PropietarioMapper.toPropietarioDto(propietarioOptional.get());
        }
        throw new ResourceNotFoundException("propietario", uuid, uuid);
    }

    @Override
    public PropietarioDto obtenerPropietarioPorNumeroDocumento(String numeroDocumento) {
        Optional<Propietario> propietarioOptional = propietarioRepository.findByNumeroDocumento(numeroDocumento);
        if(propietarioOptional.isPresent()){
            return PropietarioMapper.toPropietarioDto(propietarioOptional.get());
        }
        throw new ResourceNotFoundException("propietario","numero documento", numeroDocumento);
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
        if (propietarioDto.getTipoContribuyenteDto() == null || propietarioDto.getTipoContribuyenteDto().getUuid() == null) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "El uuid de tipoContribuyenteDto no puede ser vacío");
        }
        String nroDocumento = propietarioDto.getNumeroDocumento().trim().toLowerCase();

        if (propietarioRepository.findByNumeroDocumento(nroDocumento).isPresent()) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el nro de documento del propietario ya existe");
        }
        Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByUuid(propietarioDto.getTipoContribuyenteDto().getUuid());
        if(!tipoContribuyenteOptional.isPresent()) {
            throw new ResourceNotFoundException("Tipo Contribuyente", "uuid", propietarioDto.getTipoContribuyenteDto().getUuid());
        }
        Propietario nuevoPropietario = PropietarioMapper.toPropietario(propietarioDto);
        List<Vehiculo>  vehiculoList = mapearVehiculos(propietarioDto.getVehiculoDtoList(), nuevoPropietario);
        nuevoPropietario.setVehiculoList(vehiculoList);
        nuevoPropietario.setTipoContribuyente(tipoContribuyenteOptional.get());
        return PropietarioMapper.toPropietarioDto(propietarioRepository.save(nuevoPropietario));
    }

    private List<Vehiculo> mapearVehiculos(List<VehiculoDto> vehiculoDtoList, Propietario nuevoPropietario){
        Set<String> placasVehiculo = new HashSet<>();
        List<Vehiculo> vehiculoList = new ArrayList<>();

        vehiculoDtoList.stream().forEach(vehiculoDto -> {

            Vehiculo vehiculo = obtenerVehiculo( vehiculoDto.getUuid());

            // si el vehiculo no tiene propietario
            if ( vehiculo.getPropietario() == null || !vehiculo.getPropietario().getNumeroDocumento().contains(nuevoPropietario.getNumeroDocumento())){

                if (vehiculo.getPropietario() != null){
                    throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST,"El vehículo ya tiene un propietario: Será reasignado? ");
                }

                boolean tienePlaca = vehiculo.getPlaca() != null && !vehiculo.getPlaca().trim().isEmpty();
                boolean tienePinSinPlaca = vehiculo.getPinNumeroIdentificacion() != null && !tienePlaca;

                if (tienePlaca) {
                    if (placasVehiculo.contains(vehiculo.getPlaca().toLowerCase().trim())) {
                        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la placa del vehiculo'" + vehiculo.getPlaca() + "' ya existe o es duplicado");
                    }
                    placasVehiculo.add(vehiculo.getPlaca().toLowerCase().trim());
                }

                if (tienePinSinPlaca){
                    if (placasVehiculo.contains(vehiculo.getPinNumeroIdentificacion().toLowerCase().trim())) {
                        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el PIN o numero identificacion del vehiculo'" + vehiculo.getPinNumeroIdentificacion() + "' ya existe o es duplicado");
                    }
                    placasVehiculo.add(vehiculo.getPinNumeroIdentificacion().toLowerCase().trim());
                }
                if( tienePlaca || tienePinSinPlaca) {
                    vehiculo.setPropietario(nuevoPropietario);
                    vehiculoList.add(vehiculo);
                }
                else {
                    throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el verifique los datos del vehiculo no tiene placa ese vehiculo");
                }
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
        String nroDocumento = propietarioDto.getNumeroDocumento().trim().toLowerCase();
        Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(propietarioDto.getUuid());
        if(propietarioOptional.isEmpty()){
            throw new ResourceNotFoundException("propietario", "uuid", propietarioDto.getUuid());
        }
        if(propietarioRepository.exitsPropietarioLikeNroDocumento(nroDocumento,propietarioDto.getUuid())){
            throw  new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,"el propietario ya existe");
        }
        Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByUuid(propietarioDto.getTipoContribuyenteDto().getUuid());
        if(!tipoContribuyenteOptional.isPresent()) {
            throw new ResourceNotFoundException("Tipo Contribuyente", "uuid", propietarioDto.getTipoContribuyenteDto().getUuid());
        }


        Propietario updatePropietario= PropietarioMapper.toPropietario(propietarioDto);
        updatePropietario.setIdPropietario(propietarioOptional.get().getIdPropietario());
        updatePropietario.setTipoContribuyente(tipoContribuyenteOptional.get());

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
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, uuid);
            }
        }
        throw new ResourceNotFoundException("Propietario", "uuid", uuid);
    }
}
