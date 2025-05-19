package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.DatoTecnicoDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.mapper.DatoTecnicoMapper;
import com.gamq.ambiente.dto.mapper.VehiculoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.DatoTecnico;
import com.gamq.ambiente.model.Propietario;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.DatoTecnicoRepository;
import com.gamq.ambiente.repository.PropietarioRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.VehiculoService;
import com.gamq.ambiente.validators.VehiculoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.xml.validation.Validator;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class VehiculoServiceImpl implements VehiculoService {
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    DatoTecnicoRepository datoTecnicoRepository;
    @Autowired
    VehiculoValidator vehiculoValidator;
    @Autowired
    PropietarioRepository propietarioRepository;


    @Override
    public VehiculoDto obtenerVehiculoPorUuid(String uuid) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(uuid);
        if(vehiculoOptional.isPresent()){
            return VehiculoMapper.toVehiculoDto(vehiculoOptional.get());
        }
        throw new ResourceNotFoundException("Vehiculo", "uuid", uuid);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPlaca(String placa) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlaca(placa);
        if(vehiculoOptional.isPresent()){
            return VehiculoMapper.toVehiculoDto(vehiculoOptional.get());
        }
        throw new ResourceNotFoundException("Vehiculo", "placa", placa.toString());
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPoliza(String poliza) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPoliza(poliza);
        if(vehiculoOptional.isPresent()){
            return VehiculoMapper.toVehiculoDto(vehiculoOptional.get());
        }
        throw new ResourceNotFoundException("Vehiculo", "poliza", poliza.toString());
    }

    @Override
    public VehiculoDto obtenerVehiculoPorVinNumeroIdentificacion(String vinNumeroIdentificacion) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByVinNumeroIdentificacion(vinNumeroIdentificacion);
        if(vehiculoOptional.isPresent()){
            return VehiculoMapper.toVehiculoDto(vehiculoOptional.get());
        }
        throw new ResourceNotFoundException("Vehiculo", "vin numero de identificacion", vinNumeroIdentificacion);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPinNumeroIdentificacion(String pinNumeroIdentificacion) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPinNumeroIdentificacion(pinNumeroIdentificacion);
        if(vehiculoOptional.isPresent()){
            return VehiculoMapper.toVehiculoDto(vehiculoOptional.get());
        }
        throw new ResourceNotFoundException("Vehiculo", "pin numero de identificacion", pinNumeroIdentificacion);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorChasis(String chasis) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByChasis(chasis);
        if(vehiculoOptional.isPresent()){
            return VehiculoMapper.toVehiculoDto(vehiculoOptional.get());
        }
        throw new ResourceNotFoundException("Vehiculo", "chasis", chasis);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPlacaAnterior(String placaAnterior) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByPlacaAnterior(placaAnterior);
        if(vehiculoOptional.isPresent()){
            return VehiculoMapper.toVehiculoDto(vehiculoOptional.get());
        }
        throw new ResourceNotFoundException("Vehiculo", "placa anterior", placaAnterior);
    }

    @Override
    public List<VehiculoDto> obtenerVehiculos() {
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        return  vehiculoList.stream().map( vehiculo -> {
            return  VehiculoMapper.toVehiculoDto(vehiculo);
        }).collect(Collectors.toList());
    }

    @Override
    public VehiculoDto crearVehiculo(VehiculoDto vehiculoDto) {
        if (vehiculoValidator.validateVehiculo(vehiculoDto) ) {
            if (vehiculoDto.getDatoTecnicoDto() == null || !validarDatoTecnico(vehiculoDto.getDatoTecnicoDto())) {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "error en los datos tecnicos");
            }
            Vehiculo nuevoVehiculo = VehiculoMapper.toVehiculo(vehiculoDto);

            if (vehiculoDto.getPropietarioDto()!= null && vehiculoDto.getPropietarioDto().getUuid() != null){
                Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(vehiculoDto.getPropietarioDto().getUuid());
                if (propietarioOptional.isPresent()){
                    nuevoVehiculo.setPropietario(propietarioOptional.get());
                }
                else {
                    throw new ResourceNotFoundException("propietario", "uuid", vehiculoDto.getPropietarioDto().getUuid());
                }
            }
            Vehiculo vehiculo = vehiculoRepository.save(nuevoVehiculo);
            vehiculo.setDatoTecnico(datoTecnicoRepository.save(DatoTecnicoMapper.toDatoTecnico(vehiculoDto.getDatoTecnicoDto()).setVehiculo(vehiculo)));
            return VehiculoMapper.toVehiculoDto(vehiculo);

        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Vehiculo ya existe con esa Placa o poliza o VIN o PIN ");
    }

    private boolean validarDatoTecnico(DatoTecnicoDto datoTecnicoDto) {
        return datoTecnicoDto != null && datoTecnicoDto.getClase() != null && datoTecnicoDto.getMarca() != null
        && datoTecnicoDto.getPais() != null && datoTecnicoDto.getModelo() != null && datoTecnicoDto.getColor() != null
                && (datoTecnicoDto.getYearFabricacion() != null && datoTecnicoDto.getYearFabricacion() > 1900 && datoTecnicoDto.getYearFabricacion() <= Year.now().getValue()) && datoTecnicoDto.getTipoCombustion() != null && datoTecnicoDto.getTipoMotor() != null
                && datoTecnicoDto.getTiempoMotor() != null;
    }

    @Override
    public VehiculoDto actualizarVehiculo(VehiculoDto vehiculoDto) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(vehiculoDto.getUuid());
        if(vehiculoOptional.isPresent()) {
            if (!vehiculoRepository.exitsVehiculoLikePlaca(vehiculoDto.getPlaca().toLowerCase(), vehiculoDto.getUuid())) {
                if (vehiculoDto.getDatoTecnicoDto() == null || !validarDatoTecnico(vehiculoDto.getDatoTecnicoDto())) {
                    throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "error en los datos tecnicos");
                }
                Vehiculo updateVehiculo = VehiculoMapper.toVehiculo(vehiculoDto);
                updateVehiculo.setIdVehiculo(vehiculoOptional.get().getIdVehiculo());

                Optional<DatoTecnico> datoTecnicoOptional = datoTecnicoRepository.findByUuid(vehiculoDto.getDatoTecnicoDto().getUuid());
                if(datoTecnicoOptional.isEmpty()){
                    throw new ResourceNotFoundException("dato tecnico del vehiculo", "uuid", vehiculoDto.getDatoTecnicoDto().getUuid());
                }

                DatoTecnico datoTecnico = DatoTecnicoMapper.toDatoTecnico(vehiculoDto.getDatoTecnicoDto());
                updateVehiculo.setDatoTecnico(datoTecnico);

                updateVehiculo.getDatoTecnico().setIdDatoTecnico(datoTecnicoOptional.get().getIdDatoTecnico());
                updateVehiculo.getDatoTecnico().setVehiculo(updateVehiculo);

                if (vehiculoDto.getPropietarioDto()!= null && vehiculoDto.getPropietarioDto().getUuid() != null) {
                    Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(vehiculoDto.getPropietarioDto().getUuid());
                    if (propietarioOptional.isPresent()) {
                        updateVehiculo.setPropietario(propietarioOptional.get());
                    }
                    else {
                        throw new ResourceNotFoundException("propietario", "uuid", vehiculoDto.getPropietarioDto().getUuid());
                    }
                }

                datoTecnicoRepository.save(updateVehiculo.getDatoTecnico());

                return VehiculoMapper.toVehiculoDto(vehiculoRepository.save(updateVehiculo));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Vehiculo ya existe");
            }
        }
        throw new ResourceNotFoundException("Vehiculo", "uuid",vehiculoDto.getUuid());
    }

    @Override
    public VehiculoDto eliminarVehiculo(String uuid) {
        Vehiculo vehiculoQBE = new Vehiculo(uuid);
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findByUuid(uuid);// .findOne(Example.of(vehiculoQBE));
        if(optionalVehiculo.isPresent()){
            Vehiculo vehiculo = optionalVehiculo.get();
            if(!vehiculo.getInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el Vehiculo ya esta siendo usado por las inspecciones");
            }
            vehiculoRepository.delete(vehiculo);
            return VehiculoMapper.toVehiculoDto(vehiculo);
        }
        throw new ResourceNotFoundException("Vehiculo","uuid", uuid);
    }
}
