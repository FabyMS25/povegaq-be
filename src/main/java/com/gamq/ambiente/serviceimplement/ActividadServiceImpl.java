package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.ActividadDto;
import com.gamq.ambiente.dto.mapper.ActividadMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Actividad;
import com.gamq.ambiente.model.LimiteEmision;
import com.gamq.ambiente.repository.ActividadRepository;
import com.gamq.ambiente.service.ActividadService;
import com.gamq.ambiente.validators.ActividadValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ActividadServiceImpl implements ActividadService {
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    ActividadValidator actividadValidator;

    @Override
    public ActividadDto obtenerActividadPorUuid(String uuid) {
        Optional<Actividad> actividadOptional = actividadRepository.findByUuid(uuid);
        if(actividadOptional.isPresent()){
            return ActividadMapper.toActividadDto(actividadOptional.get());
        }
        throw new ResourceNotFoundException("actividad", "uuid", uuid);
    }

    @Override
    public ActividadDto obtenerActividadPorTipoActividad(String tipoActividad) {
        Optional<Actividad> actividadOptional = actividadRepository.findByTipoActividad(tipoActividad.toLowerCase());
        if(actividadOptional.isPresent()){
            return ActividadMapper.toActividadDto(actividadOptional.get());
        }
        throw new ResourceNotFoundException("tipo actividad", "uuid", tipoActividad);
    }

    @Override
    public List<ActividadDto> obtenerActividades() {
        List<Actividad> actividadList = actividadRepository.findAll();
        return  actividadList.stream().map( actividad -> {
            return  ActividadMapper.toActividadDto(actividad);
        }).collect(Collectors.toList());
    }

    @Override
    public ActividadDto crearActividad(ActividadDto actividadDto) {
        if(!actividadValidator.validateFechaInicioFinActividad(actividadDto)){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "la Fecha Fin debe ser mayor a la Fecha Inicio");
        }
        String tipoActividad = actividadDto.getTipoActividad();
        if(tipoActividad==null){ throw new ResourceNotFoundException("actividad","tipo actividad", tipoActividad);}
        Optional<Actividad> actividadOptional = actividadRepository.findByTipoActividad(tipoActividad);
        if(actividadOptional.isEmpty()){
            Actividad nuevoActividad = ActividadMapper.toActividad(actividadDto);
            return ActividadMapper.toActividadDto(actividadRepository.save(nuevoActividad));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la actividad ya existe");
    }

    @Override
    public ActividadDto actualizarActividad(ActividadDto actividadDto) {
        if(!actividadValidator.validateFechaInicioFinActividad(actividadDto)){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "la Fecha Fin debe ser mayor a la Fecha Inicio");
        }
        Optional<Actividad> actividadOptional = actividadRepository.findByUuid(actividadDto.getUuid());
        if(actividadOptional.isPresent()) {
            if (!actividadRepository.exitsActividadLikeTipoActividad(actividadDto.getTipoActividad().toLowerCase(), actividadDto.getUuid())) {
                Actividad updateActividad = ActividadMapper.toActividad(actividadDto);
                updateActividad.setIdActividad(actividadOptional.get().getIdActividad());
                return ActividadMapper.toActividadDto(actividadRepository.save(updateActividad));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el tipo de actividad ya existe");
            }
        }
        throw new ResourceNotFoundException("tipo actividad", "uuid",actividadDto.getUuid());
    }

    @Override
    public ActividadDto eliminarActividad(String uuid) {
        Optional<Actividad> optionalActividad = actividadRepository.findByUuid(uuid);
        if(optionalActividad.isPresent()){
            Actividad actividad = optionalActividad.get();
            if(!actividad.getInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "esta actividad ya esta siendo usado por las inspecciones");
            }
            if(!actividad.getEventoList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "esta actividad ya esta siendo usado por los eventos programados");
            }
            actividadRepository.delete(actividad);
            return ActividadMapper.toActividadDto(actividad);
        }
        throw new ResourceNotFoundException("tipo actividad","uuid", uuid);
    }

    @Override
    public List<ActividadDto> obtenerActividadesActivas() {
        List<Actividad> actividadList = actividadRepository.findByActivoTrueOrderByFechaInicioAsc();
        return  actividadList.stream().map( actividad -> {
            return  ActividadMapper.toActividadDto(actividad);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ActividadDto> obtenerActividadesPorAnio(Integer year) {
        List<Actividad> actividadList = actividadRepository.findActividadesPorAnio(year);
        return  actividadList.stream().map( actividad -> {
            return  ActividadMapper.toActividadDto(actividad);
        }).collect(Collectors.toList());
    }

    @Override
    public ActividadDto actualizarActividadActivo(String uuid, boolean nuevoActivo) {
        Actividad actividad = actividadRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Actividad","uuid", uuid));
        actividad.setActivo(nuevoActivo);
        if(!nuevoActivo){
            actividad.setFechaFin(new Date());
        }
        else {
            actividad.setFechaInicio(new Date());
        }
        actividadRepository.save(actividad);
        return ActividadMapper.toActividadDto(actividad);
    }

    @Override
    public List<ActividadDto> obtenerActividadesEntreFechas(Date rangoInicio, Date rangoFin) {
        List<Actividad> actividadList = actividadRepository.findActividadesBetweenFechas(rangoInicio, rangoFin);
        return actividadList.stream().map(actividad -> {
            return ActividadMapper.toActividadDto(actividad);
        }).collect(Collectors.toList());
    }
}
