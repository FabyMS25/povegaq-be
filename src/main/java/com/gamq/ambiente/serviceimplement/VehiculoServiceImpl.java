package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.DatoTecnicoDto;
import com.gamq.ambiente.dto.VehiculoDto;
import com.gamq.ambiente.dto.VehiculoTipoCombustibleDto;
import com.gamq.ambiente.dto.mapper.DatoTecnicoMapper;
import com.gamq.ambiente.dto.mapper.VehiculoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.*;
import com.gamq.ambiente.repository.*;
import com.gamq.ambiente.service.VehiculoService;
import com.gamq.ambiente.validators.VehiculoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.ConstraintViolation;
import javax.xml.validation.Validator;
import java.time.Year;
import java.util.*;
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
    @Autowired
    TipoCombustibleRepository tipoCombustibleRepository;
    @Autowired
    VehiculoTipoCombustibleRepository vehiculoTipoCombustibleRepository;
    @Autowired
    TipoClaseVehiculoRepository tipoClaseVehiculoRepository;


    @Override
    public VehiculoDto obtenerVehiculoPorUuid(String uuid) {
        Vehiculo vehiculo = obtenerVehiculoPorUuidOThrow(uuid);
        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPlaca(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo","placa", placa));
        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPoliza(String poliza) {
        Vehiculo vehiculo = vehiculoRepository.findByPoliza(poliza)
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo", "poliza", poliza));
        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorVinNumeroIdentificacion(String vinNumeroIdentificacion) {
        Vehiculo vehiculo = vehiculoRepository.findByVinNumeroIdentificacion(vinNumeroIdentificacion)
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo", "vin numero de identificacion", vinNumeroIdentificacion));
        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPinNumeroIdentificacion(String pinNumeroIdentificacion) {
        Vehiculo vehiculo = vehiculoRepository.findByPinNumeroIdentificacion(pinNumeroIdentificacion)
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo", "pin numero de identificacion", pinNumeroIdentificacion));
        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorChasis(String chasis) {
        Vehiculo vehiculo = vehiculoRepository.findByChasis(chasis)
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo", "chasis", chasis));
        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    @Override
    public VehiculoDto obtenerVehiculoPorPlacaAnterior(String placaAnterior) {
        Vehiculo vehiculo =vehiculoRepository.findByPlacaAnterior(placaAnterior)
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo", "placa anterior", placaAnterior));
        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    @Override
    public List<VehiculoDto> obtenerVehiculos() {
        List<Vehiculo> vehiculoList = vehiculoRepository.findAll();
        return  vehiculoList.stream().map( vehiculo -> {
            return  VehiculoMapper.toVehiculoDto(vehiculo);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VehiculoDto crearVehiculo(VehiculoDto vehiculoDto) {
        validarVehiculoNoExistente(vehiculoDto);
        validarDatosTecnicos(vehiculoDto.getDatoTecnicoDto());
        validarCombustibles(vehiculoDto.getVehiculoTipoCombustibleDtoList());

       /* if (!vehiculoValidator.validateVehiculo(vehiculoDto) ) {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Vehiculo ya existe con esa Placa o poliza o VIN o PIN ");
        }*/

      /*  if (vehiculoDto.getDatoTecnicoDto() == null || !validarDatoTecnico(vehiculoDto.getDatoTecnicoDto())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "error en los datos tecnicos");
        }*/

        TipoClaseVehiculo tipoClaseVehiculo = tipoClaseVehiculoRepository.findByUuid(vehiculoDto.getDatoTecnicoDto().getTipoClaseVehiculoDto().getUuid())
                        .orElseThrow(()-> new ResourceNotFoundException("TipoClaseVehiculo","uuid",vehiculoDto.getDatoTecnicoDto().getTipoClaseVehiculoDto().getUuid()));



        Vehiculo nuevoVehiculo = VehiculoMapper.toVehiculo(vehiculoDto);

        if (vehiculoDto.getPropietarioDto() != null && vehiculoDto.getPropietarioDto().getUuid() != null){
            Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(vehiculoDto.getPropietarioDto().getUuid());
            if (propietarioOptional.isPresent()){
                nuevoVehiculo.setPropietario(propietarioOptional.get());
            }
            else {
                throw new ResourceNotFoundException("propietario", "uuid", vehiculoDto.getPropietarioDto().getUuid());
            }
        }

        List<VehiculoTipoCombustible> vehiculoTipoCombustibleList = new ArrayList<>();
        for (VehiculoTipoCombustibleDto combustibleDTO : vehiculoDto.getVehiculoTipoCombustibleDtoList()) {
            boolean yaExisteMismoTipoCombustible = vehiculoTipoCombustibleList.stream()
                    .anyMatch(v -> v.getTipoCombustible().getUuid().equals(combustibleDTO.getTipoCombustibleDto().getUuid()));

            if (yaExisteMismoTipoCombustible) {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,
                        "El tipo de combustible no puede ser duplicado: " + combustibleDTO.getTipoCombustibleDto().getUuid());
            }
            TipoCombustible tipoCombustible = tipoCombustibleRepository
                    .findByUuid(combustibleDTO.getTipoCombustibleDto().getUuid())
                    .orElseThrow(() -> new RuntimeException("Tipo de combustible no encontrado"));

            VehiculoTipoCombustible vehiculoTipoCombustible = new VehiculoTipoCombustible();
            vehiculoTipoCombustible.setVehiculo(nuevoVehiculo);
            vehiculoTipoCombustible.setTipoCombustible(tipoCombustible);
            vehiculoTipoCombustible.setEsPrimario(combustibleDTO.getEsPrimario());
            vehiculoTipoCombustible.setEstado(false);
            vehiculoTipoCombustibleList.add(vehiculoTipoCombustible);
        }

        List<VehiculoTipoCombustible> original = nuevoVehiculo.getVehiculoTipoCombustibleList();
        original.clear();
        original.addAll(vehiculoTipoCombustibleList);

        Vehiculo vehiculo = vehiculoRepository.save(nuevoVehiculo);

        DatoTecnico datoTecnico = DatoTecnicoMapper.toDatoTecnico(vehiculoDto.getDatoTecnicoDto());
        datoTecnico.setVehiculo(vehiculo);
        datoTecnico.setTipoClaseVehiculo(tipoClaseVehiculo);

        datoTecnico = datoTecnicoRepository.save(datoTecnico);
        vehiculo.setDatoTecnico(datoTecnico);
        // graba la relacion
        vehiculo = vehiculoRepository.save(vehiculo);

        return VehiculoMapper.toVehiculoDto(vehiculo);
    }

    private boolean validarDatoTecnico(DatoTecnicoDto datoTecnicoDto) {
        return datoTecnicoDto != null && datoTecnicoDto.getTipoClaseVehiculoDto().getUuid() != null && datoTecnicoDto.getMarca() != null
        && datoTecnicoDto.getPais() != null && datoTecnicoDto.getModelo() != null && datoTecnicoDto.getColor() != null
                && (datoTecnicoDto.getYearFabricacion() != null && datoTecnicoDto.getYearFabricacion() > 1900 && datoTecnicoDto.getYearFabricacion() <= Year.now().getValue()) && datoTecnicoDto.getTipoMotor() != null
                && datoTecnicoDto.getTiempoMotor() != null;
    }

    @Override
    @Transactional
    public VehiculoDto actualizarVehiculo(VehiculoDto vehiculoDto) {
        Vehiculo vehiculo = obtenerVehiculoPorUuidOThrow(vehiculoDto.getUuid());
        if (vehiculoRepository.exitsVehiculoLikePlaca(vehiculoDto.getPlaca().toLowerCase(), vehiculoDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Vehiculo ya existe");
        }
        if (vehiculoDto.getDatoTecnicoDto() == null || !validarDatoTecnico(vehiculoDto.getDatoTecnicoDto())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "error en los datos tecnicos");
        }

        validarCombustibles(vehiculoDto.getVehiculoTipoCombustibleDtoList());

        Vehiculo updateVehiculo = VehiculoMapper.toVehiculo(vehiculoDto);
        updateVehiculo.setIdVehiculo(vehiculo.getIdVehiculo());

        Optional<DatoTecnico> datoTecnicoOptional = datoTecnicoRepository.findByUuid(vehiculoDto.getDatoTecnicoDto().getUuid());
        if(datoTecnicoOptional.isEmpty()){
            throw new ResourceNotFoundException("dato tecnico del vehiculo", "uuid", vehiculoDto.getDatoTecnicoDto().getUuid());
        }

        TipoClaseVehiculo tipoClaseVehiculo = tipoClaseVehiculoRepository.findByUuid(vehiculoDto.getDatoTecnicoDto().getTipoClaseVehiculoDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("TipoClaseVehiculo","uuid",vehiculoDto.getDatoTecnicoDto().getTipoClaseVehiculoDto().getUuid()));

        DatoTecnico datoTecnico = DatoTecnicoMapper.toDatoTecnico(vehiculoDto.getDatoTecnicoDto());
        updateVehiculo.setDatoTecnico(datoTecnico);

        updateVehiculo.getDatoTecnico().setIdDatoTecnico(datoTecnicoOptional.get().getIdDatoTecnico());
        updateVehiculo.getDatoTecnico().setVehiculo(updateVehiculo);
        updateVehiculo.getDatoTecnico().setTipoClaseVehiculo(tipoClaseVehiculo);

        if (vehiculoDto.getPropietarioDto()!= null && vehiculoDto.getPropietarioDto().getUuid() != null) {
            Optional<Propietario> propietarioOptional = propietarioRepository.findByUuid(vehiculoDto.getPropietarioDto().getUuid());
            if (propietarioOptional.isPresent()) {
                updateVehiculo.setPropietario(propietarioOptional.get());
            }
            else {
                throw new ResourceNotFoundException("propietario", "uuid", vehiculoDto.getPropietarioDto().getUuid());
            }
        }

        //Actualizar
        List<VehiculoTipoCombustible> vehiculoTipoCombustibleListNueva = new ArrayList<>();
        for (VehiculoTipoCombustibleDto combustibleDTO : vehiculoDto.getVehiculoTipoCombustibleDtoList()) {
            boolean yaExisteMismoTipoCombustible = vehiculoTipoCombustibleListNueva.stream()
                    .anyMatch(v -> v.getTipoCombustible().getUuid().equals(combustibleDTO.getTipoCombustibleDto().getUuid()));

            if (yaExisteMismoTipoCombustible) {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,
                        "Ya existe una relación para este tipo de combustible: " + combustibleDTO.getTipoCombustibleDto().getUuid());
            }

            TipoCombustible tipoCombustible = tipoCombustibleRepository
                    .findByUuid(combustibleDTO.getTipoCombustibleDto().getUuid())
                    .orElseThrow(() -> new RuntimeException("Tipo de combustible no encontrado"));
            Optional<VehiculoTipoCombustible> vehiculoTipoCombustibleModificable = vehiculoTipoCombustibleRepository.findByUuidIncluyendoEliminados(combustibleDTO.getUuid());

            VehiculoTipoCombustible vehiculoTipoCombustible;

            if (vehiculoTipoCombustibleModificable.isPresent()) {
                vehiculoTipoCombustible = vehiculoTipoCombustibleModificable.get();
                vehiculoTipoCombustible.setIdVehiculoTipoCombustible(vehiculoTipoCombustibleModificable.get().getIdVehiculoTipoCombustible());
                vehiculoTipoCombustible.setEstado(false);
            }
            else {
                vehiculoTipoCombustible =new VehiculoTipoCombustible();
                vehiculoTipoCombustible.setEstado(false);
            }
            vehiculoTipoCombustible.setVehiculo(updateVehiculo);
            vehiculoTipoCombustible.setTipoCombustible(tipoCombustible);
            vehiculoTipoCombustible.setEsPrimario(combustibleDTO.getEsPrimario());
            vehiculoTipoCombustibleRepository.saveAndFlush(vehiculoTipoCombustible);
            vehiculoTipoCombustibleListNueva.add(vehiculoTipoCombustible);
        }

        updateVehiculo.getVehiculoTipoCombustibleList().clear();
        updateVehiculo.getVehiculoTipoCombustibleList().addAll(vehiculoTipoCombustibleListNueva);

        datoTecnicoRepository.save(updateVehiculo.getDatoTecnico());

        return VehiculoMapper.toVehiculoDto(vehiculoRepository.save(updateVehiculo));
     }

    @Override
    public VehiculoDto eliminarVehiculo(String uuid) {
        Optional<Vehiculo> optionalVehiculo = vehiculoRepository.findByUuid(uuid);
        if(optionalVehiculo.isPresent()){
            Vehiculo vehiculo = optionalVehiculo.get();
            if(!vehiculo.getInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el Vehiculo ya tiene inspecciones");
            }
            vehiculoRepository.delete(vehiculo);
            return VehiculoMapper.toVehiculoDto(vehiculo);
        }
        throw new ResourceNotFoundException("Vehiculo","uuid", uuid);
    }

    private Vehiculo obtenerVehiculoPorUuidOThrow(String uuid){
        return  vehiculoRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo", "uuid", uuid));
    }

    private void validarCombustibles(List<VehiculoTipoCombustibleDto> combustibleList) {
        if (combustibleList == null || combustibleList.isEmpty()) {
            throw new IllegalArgumentException("Debe ingresar al menos un tipo de combustible.");
        }

        long primarios = combustibleList.stream()
                .filter(VehiculoTipoCombustibleDto::getEsPrimario)
                .count();

        if (primarios > 1) {
            throw new IllegalArgumentException("Solo es permitido un tipo de combustible primario por vehículo.");
        }

        for (VehiculoTipoCombustibleDto vehiculoTipoCombustibleDto : combustibleList) {
            if (vehiculoTipoCombustibleDto.getTipoCombustibleDto() == null || vehiculoTipoCombustibleDto.getTipoCombustibleDto().getUuid() == null) {
                throw new IllegalArgumentException("Cada tipo de combustible debe estar vinculado a un tipo válido.");
            }

            tipoCombustibleRepository.findByUuid(vehiculoTipoCombustibleDto.getTipoCombustibleDto().getUuid())
                    .orElseThrow(()-> new ResourceNotFoundException("Tipo Combustible", "uuid", vehiculoTipoCombustibleDto.getTipoCombustibleDto().getUuid()));
        }
        // ojo probar
        Set<String> tiposCombustible = new HashSet<>();
        for (VehiculoTipoCombustibleDto dto : combustibleList) {
            String uuid = dto.getTipoCombustibleDto().getUuid();
            if (!tiposCombustible.add(uuid)) {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,
                        "El tipo de combustible no puede ser duplicado: " + uuid);
            }
        }
    }

    private void validarVehiculoNoExistente(VehiculoDto vehiculoDto) {
        if (!vehiculoValidator.validateVehiculo(vehiculoDto)) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT,
                    "El Vehiculo ya existe con esa Placa o Póliza o VIN o PIN");
        }
    }

    private void validarDatosTecnicos(DatoTecnicoDto datoTecnicoDto) {
        if (datoTecnicoDto == null || !validarDatoTecnico(datoTecnicoDto)) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "Error en los datos técnicos");
        }
    }
}
