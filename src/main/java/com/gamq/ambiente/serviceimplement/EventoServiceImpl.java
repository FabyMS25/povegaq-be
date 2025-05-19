package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.EventoDto;
import com.gamq.ambiente.dto.mapper.EventoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Actividad;
import com.gamq.ambiente.model.Evento;
import com.gamq.ambiente.repository.ActividadRepository;
import com.gamq.ambiente.repository.EventoRepository;
import com.gamq.ambiente.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EventoServiceImpl implements EventoService {
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    ActividadRepository actividadRepository;

    @Override
    public EventoDto obtenerEventoPorUuid(String uuid) {
        Optional<Evento> eventoOptional = eventoRepository.findByUuid(uuid);
        if(eventoOptional.isPresent()){
            return EventoMapper.toEventoDto(eventoOptional.get());
        }
        throw new ResourceNotFoundException("Evento", "uuid", uuid);
    }

    @Override
    public List<EventoDto> obtenerEventosAntesDeFecha(Date fecha) {
        List<Evento> eventoList = eventoRepository.findByFechaInicioBefore(fecha);
        return  eventoList.stream().map( evento -> {
            return  EventoMapper.toEventoDto(evento);
        }).collect(Collectors.toList());
    }

    @Override
    public List<EventoDto> obtenerEventoPorInstitucion(String institucion) {
        List<Evento> eventoList = eventoRepository.findByInstitucion(institucion);
        return  eventoList.stream().map( evento -> {
            return  EventoMapper.toEventoDto(evento);
        }).collect(Collectors.toList());
    }

    @Override
    public List<EventoDto> obtenerEventos() {
        List<Evento> eventoList = eventoRepository.findAll();
        return  eventoList.stream().map( evento -> {
            return  EventoMapper.toEventoDto(evento);
        }).collect(Collectors.toList());
    }

    @Override
    public EventoDto crearEvento(EventoDto eventoDto) {
       // String institucion = eventoDto.getInstitucion();
        //if(institucion==null){ throw new ResourceNotFoundException("Evento","Institucion", institucion);}
        Optional<Actividad> actividadOptional = actividadRepository.findByUuid(eventoDto.getActividadDto().getUuid());
        if (actividadOptional.isPresent()) {
            Evento nuevoEvento = EventoMapper.toEvento(eventoDto);
            nuevoEvento.setActividad(actividadOptional.get());
            return EventoMapper.toEventoDto(eventoRepository.save(nuevoEvento));
        }
        else {
            throw new ResourceNotFoundException("Actividad", "uuid", eventoDto.getActividadDto().getUuid());
        }
    }

    @Override
    public EventoDto actualizarEvento(EventoDto eventoDto) {
        Optional<Evento> eventoOptional = eventoRepository.findByUuid(eventoDto.getUuid());
        if(eventoOptional.isPresent()) {
            Optional<Actividad> actividadOptional = actividadRepository.findByUuid(eventoDto.getActividadDto().getUuid());
            if (actividadOptional.isPresent()) {
                Evento updateEvento = EventoMapper.toEvento(eventoDto);
                updateEvento.setActividad(actividadOptional.get());
                updateEvento.setIdEvento(eventoOptional.get().getIdEvento());
                return EventoMapper.toEventoDto(eventoRepository.save(updateEvento));
            }
            else {
                throw new ResourceNotFoundException("Actividad", "uuid", eventoDto.getActividadDto().getUuid());
            }
        }
        throw new ResourceNotFoundException("Evento", "uuid",eventoDto.getUuid());
    }

    @Override
    public EventoDto eliminarEvento(String uuid) {
        Evento eventoQBE = new Evento(uuid);
        Optional<Evento> optionalEvento = eventoRepository.findByUuid(eventoQBE.getUuid());// .findOne(Example.of(eventoQBE));
        if(optionalEvento.isPresent()){
            Evento evento = optionalEvento.get();
            if(!evento.getInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "este evento ya esta siendo usado por las inspecciones");
            }
            eventoRepository.delete(evento);
            return EventoMapper.toEventoDto(evento);
        }
        throw new ResourceNotFoundException("Evento","uuid", uuid);
    }

    @Override
    public List<EventoDto> obtenerEventosActivas(Date fecha) {
        List<Evento> eventoList = eventoRepository.findEventosActivos(fecha);
        return  eventoList.stream().map( evento -> {
            return  EventoMapper.toEventoDto(evento);
        }).collect(Collectors.toList());
    }

    @Override
    public List<EventoDto> obtenerEventosPorAnio(Integer year) {
        List<Evento> eventoList = eventoRepository.findEventosPorAnio(year);
        return  eventoList.stream().map( evento -> {
            return  EventoMapper.toEventoDto(evento);
        }).collect(Collectors.toList());
    }
}
