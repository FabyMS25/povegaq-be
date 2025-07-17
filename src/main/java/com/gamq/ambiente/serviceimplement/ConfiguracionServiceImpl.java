package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.ConfiguracionDto;
import com.gamq.ambiente.dto.mapper.ConfiguracionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Configuracion;
import com.gamq.ambiente.repository.ConfiguracionRepository;
import com.gamq.ambiente.service.ConfiguracionService;
import com.gamq.ambiente.validators.ConfiguracionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfiguracionServiceImpl implements ConfiguracionService {
    @Autowired
    ConfiguracionRepository configuracionRepository;
    @Autowired
    ConfiguracionValidator configuracionValidator;

    @Override
    public ConfiguracionDto obtenerConfiguracionPorUuid(String uuid) {
        Optional<Configuracion> configuracionOptional = configuracionRepository.findByUuid(uuid);
        if(configuracionOptional.isPresent()){
            return ConfiguracionMapper.toConfiguracionDto(configuracionOptional.get());
        }
        throw new ResourceNotFoundException("configuracion", "uuid", uuid);
    }

    @Override
    public ConfiguracionDto obtenerConfiguracionPorClave(String clave) {
        Optional<Configuracion> configuracionOptional = configuracionRepository.findByClave(clave.toLowerCase());
        if(configuracionOptional.isPresent()){
            return ConfiguracionMapper.toConfiguracionDto(configuracionOptional.get());
        }
        throw new ResourceNotFoundException("tipo configuracion", "uuid", clave);
    }

    @Override
    public List<ConfiguracionDto> obtenerConfiguraciones() {
        List<Configuracion> configuracionList = configuracionRepository.findAll();
        return  configuracionList.stream().map( configuracion -> {
            return  ConfiguracionMapper.toConfiguracionDto(configuracion);
        }).collect(Collectors.toList());
    }

    @Override
    public ConfiguracionDto crearConfiguracion(ConfiguracionDto configuracionDto) {
        if(!configuracionValidator.validateFechaInicioFinConfiguracion(configuracionDto)){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la Fecha Fin debe ser mayor a la Fecha Inicio");
        }
        String clave = configuracionDto.getClave();
        if(clave==null){ throw new ResourceNotFoundException("configuracion","tipo configuracion", clave);}
        Optional<Configuracion> configuracionOptional = configuracionRepository.findByClave(clave);
        if(configuracionOptional.isEmpty()){
            Configuracion nuevoConfiguracion = ConfiguracionMapper.toConfiguracion(configuracionDto);
            return ConfiguracionMapper.toConfiguracionDto(configuracionRepository.save(nuevoConfiguracion));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la configuracion ya existe");
    }

    @Override
    public ConfiguracionDto actualizarConfiguracion(ConfiguracionDto configuracionDto) {
        if(!configuracionValidator.validateFechaInicioFinConfiguracion(configuracionDto)){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la Fecha Fin debe ser mayor a la Fecha Inicio");
        }
        Optional<Configuracion> configuracionOptional = configuracionRepository.findByUuid(configuracionDto.getUuid());
        if(configuracionOptional.isPresent()) {
            if (!configuracionRepository.exitsConfiguracionLikeClave(configuracionDto.getClave().toLowerCase(), configuracionDto.getUuid())) {
                Configuracion updateConfiguracion = ConfiguracionMapper.toConfiguracion(configuracionDto);
                updateConfiguracion.setIdConfiguracion(configuracionOptional.get().getIdConfiguracion());
                return ConfiguracionMapper.toConfiguracionDto(configuracionRepository.save(updateConfiguracion));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el tipo de configuracion ya existe");
            }
        }
        throw new ResourceNotFoundException("tipo actividad", "uuid",configuracionDto.getUuid());
    }

    @Override
    public ConfiguracionDto eliminarConfiguracion(String uuid) {
        Optional<Configuracion> optionalConfiguracion = configuracionRepository.findByUuid(uuid);
        if(optionalConfiguracion.isPresent()){
            Configuracion configuracion = optionalConfiguracion.get();
            configuracionRepository.delete(configuracion);
            return ConfiguracionMapper.toConfiguracionDto(configuracion);
        }
        throw new ResourceNotFoundException("tipo configuracion","uuid", uuid);
    }

    @Override
    public List<ConfiguracionDto> obtenerConfiguracionesActivas() {
        List<Configuracion> configuracionList = configuracionRepository.findByActivoTrueOrderByFechaInicioAsc();
        return  configuracionList.stream().map( configuracion -> {
            return  ConfiguracionMapper.toConfiguracionDto(configuracion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ConfiguracionDto> obtenerConfiguracionesPorAnio(Integer year) {
        List<Configuracion> configuracionList = configuracionRepository.findConfiguracionesPorAnio(year);
        return  configuracionList.stream().map( configuracion -> {
            return  ConfiguracionMapper.toConfiguracionDto(configuracion);
        }).collect(Collectors.toList());
    }

    @Override
    public ConfiguracionDto actualizarConfiguracionActivo(String uuid, boolean nuevoActivo) {
        Configuracion configuracion = configuracionRepository.findByUuid(uuid)
                .orElseThrow(()-> new RuntimeException("la configuracion no encontrado"));
        configuracion.setActivo(nuevoActivo);
        if(!nuevoActivo){
            configuracion.setFechaFin(new Date());
        }
        else {
            configuracion.setFechaInicio(new Date());
        }
        configuracionRepository.save(configuracion);
        return ConfiguracionMapper.toConfiguracionDto(configuracion);
    }

    @Override
    public List<ConfiguracionDto> obtenerConfiguracionesEntreFechas(Date rangoInicio, Date rangoFin) {
        List<Configuracion> configuracionList = configuracionRepository.findConfiguracionesBetweenFechas(rangoInicio, rangoFin);
        return configuracionList.stream().map(configuracion -> {
            return ConfiguracionMapper.toConfiguracionDto(configuracion);
        }).collect(Collectors.toList());
    }

    @Override
    public String obtenerValorString(String clave) {
        return configuracionRepository.findByClave(clave)
                .orElseThrow(()-> new ResourceNotFoundException("Configuracion", "clave", clave))
                .getClave();
    }

    @Override
    public Integer obtenerValorEntero(String clave) {
        return Integer.parseInt(obtenerValorString(clave));
    }

    @Override
    public boolean obtenerValorBoolean(String clave) {
        return Boolean.parseBoolean(obtenerValorString(clave));
    }

    @Override
    public Double obtenerValorDouble(String clave) {
        return Double.parseDouble(obtenerValorString(clave));
    }
}
