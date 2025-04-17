package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Actividad;
import com.gamq.ambiente.model.Evento;
import com.gamq.ambiente.model.Inspeccion;

import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.ActividadRepository;
import com.gamq.ambiente.repository.EventoRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.InspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InspeccionServiceImpl implements InspeccionService {
    @Autowired
    InspeccionRepository inspeccionRepository;
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Override
    public InspeccionDto obtenerInspeccionPorUuid(String uuid) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(uuid);
        if(inspeccionOptional.isPresent()){
            return InspeccionMapper.toInspeccionDto(inspeccionOptional.get());
        }
        throw new ResourceNotFoundException("Inspeccion", "uuid", uuid);
    }

    @Override
    public List<InspeccionDto> obtenerInspeccionPorPlaca(String placa) {
        List<Inspeccion> inspeccionOptional = inspeccionRepository.findByPlacaVehiculo(placa);
        return inspeccionOptional.stream().map( inspeccion -> {
            return InspeccionMapper.toInspeccionDto(inspeccion);
        } ).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionDto> obtenerInspeccionPorUuidUsuario(String uuidUsuario) {
        List<Inspeccion> inspeccionList = inspeccionRepository.findByUuidUsuario(uuidUsuario);
        return inspeccionList.stream().map( inspeccion -> {
            return  InspeccionMapper.toInspeccionDto(inspeccion);
        }).collect(Collectors.toList());
    }


    @Override
    public List<InspeccionDto> obtenerInspecciones() {
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        return  inspeccionList.stream().map( inspeccion -> {
            return  InspeccionMapper.toInspeccionDto(inspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public InspeccionDto crearInspeccion(InspeccionDto inspeccionDto) {
        String uuidUsuario = inspeccionDto.getUuidUsuario();
        if(uuidUsuario == null){ throw new ResourceNotFoundException("Inspeccion","uuidUsuario", uuidUsuario);}
        String nombreInspector = inspeccionDto.getNombreInspector();
        if(nombreInspector == null){ throw new ResourceNotFoundException("Inspeccion","nombreInspector", nombreInspector);}


        Optional<Actividad> actividadOptional = actividadRepository.findByUuid(inspeccionDto.getActividadDto().getUuid());
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(inspeccionDto.getVehiculoDto().getUuid());


        if(actividadOptional.isPresent()){
           if (vehiculoOptional.isPresent()){
                Inspeccion nuevoInspeccion = InspeccionMapper.toInspeccion(inspeccionDto);
                nuevoInspeccion.setActividad(actividadOptional.get());
                nuevoInspeccion.setVehiculo(vehiculoOptional.get());
               if(inspeccionDto != null && inspeccionDto.getEventoDto() != null && inspeccionDto.getEventoDto().getUuid() != null) {
                   Optional<Evento> eventoOptional = eventoRepository.findByUuid(inspeccionDto.getEventoDto().getUuid());
                   nuevoInspeccion.setEvento(eventoOptional.get());
               }
               return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(nuevoInspeccion));
            }
            else {
                throw new ResourceNotFoundException("Vehiculo", "uuid", inspeccionDto.getVehiculoDto().getUuid());
            }
        }
        throw new ResourceNotFoundException("Actividad", "uuid", inspeccionDto.getActividadDto().getUuid());
    }

    @Override
    public InspeccionDto actualizarInspeccion(InspeccionDto inspeccionDto) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(inspeccionDto.getUuid());
        if(inspeccionOptional.isPresent()) {
            Optional<Actividad> actividadOptional = actividadRepository.findByUuid(inspeccionDto.getActividadDto().getUuid());
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(inspeccionDto.getVehiculoDto().getUuid());

            if(actividadOptional.isPresent()){
                if (vehiculoOptional.isPresent()){
                    Inspeccion updateInspeccion = InspeccionMapper.toInspeccion(inspeccionDto);
                    updateInspeccion.setIdInspeccion(inspeccionOptional.get().getIdInspeccion());
                    updateInspeccion.setActividad(actividadOptional.get());
                    updateInspeccion.setVehiculo(vehiculoOptional.get());

                    if(inspeccionDto != null && inspeccionDto.getEventoDto() != null && inspeccionDto.getEventoDto().getUuid() != null) {
                        Optional<Evento> eventoOptional = eventoRepository.findByUuid(inspeccionDto.getEventoDto().getUuid());
                        updateInspeccion.setEvento(eventoOptional.get());
                    }

                    return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(updateInspeccion));
                }
                else {
                    throw new ResourceNotFoundException("Vehiculo", "uuid", inspeccionDto.getVehiculoDto().getUuid());
                }
            }
            else {
                throw new ResourceNotFoundException("Actividad", "uuid", inspeccionDto.getActividadDto().getUuid());
            }
        }
        throw new ResourceNotFoundException("Inspeccion", "uuid",inspeccionDto.getUuid());
    }

    @Override
    public InspeccionDto eliminarInspeccion(String uuid) {
        Inspeccion inspeccionQBE = new Inspeccion(uuid);
       // Optional<Inspeccion> optionalInspeccion = inspeccionRepository.findOne(Example.of(inspeccionQBE));
       Optional<Inspeccion> optionalInspeccion =  inspeccionRepository.findByUuid(uuid);
        if(optionalInspeccion.isPresent()){
            Inspeccion inspeccion = optionalInspeccion.get();
            if(!inspeccion.getCertificadoList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "la Inspeccion ya esta siendo usado por los certificados");
            }
            inspeccionRepository.delete(inspeccion);
            return InspeccionMapper.toInspeccionDto(inspeccion);
        }
        throw new ResourceNotFoundException("Inspeccion","uuid", uuid);
    }

}
