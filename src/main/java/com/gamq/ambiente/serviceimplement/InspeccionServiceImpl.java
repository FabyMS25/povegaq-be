package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.mapper.DetalleInspeccionMapper;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.*;

import com.gamq.ambiente.repository.*;
import com.gamq.ambiente.service.InspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InspeccionServiceImpl implements InspeccionService {
    @Autowired
    InspeccionRepository inspeccionRepository;
    @Autowired
    DetalleInspeccionRepository detalleInspeccionRepository;
    @Autowired
    TipoParametroRepository tipoParametroRepository;
    @Autowired
    ActividadRepository actividadRepository;
    @Autowired
    EventoRepository eventoRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    ConductorRepository conductorRepository;

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
        Optional<Conductor> conductorOptional = conductorRepository.findByUuid(inspeccionDto.getConductorDto().getUuid());
        if(actividadOptional.isPresent()){
           if (vehiculoOptional.isPresent()){
               if (conductorOptional.isPresent()) {
                   Inspeccion nuevoInspeccion = InspeccionMapper.toInspeccion(inspeccionDto);
                   nuevoInspeccion.setActividad(actividadOptional.get());
                   nuevoInspeccion.setVehiculo(vehiculoOptional.get());
                   nuevoInspeccion.setConductor(conductorOptional.get());
                   if (inspeccionDto != null && inspeccionDto.getEventoDto() != null && inspeccionDto.getEventoDto().getUuid() != null) {
                       Optional<Evento> eventoOptional = eventoRepository.findByUuid(inspeccionDto.getEventoDto().getUuid());
                       nuevoInspeccion.setEvento(eventoOptional.get());
                   }

                   List<DetalleInspeccion> detalleInspeccionList = mapearDetalleInspeccion(inspeccionDto.getDetalleInspeccionDtoList(), nuevoInspeccion);

                   nuevoInspeccion.setDetalleInspeccionList(detalleInspeccionList);


                   return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(nuevoInspeccion));
               }
               else {
                   throw new ResourceNotFoundException("Vehiculo", "uuid", inspeccionDto.getVehiculoDto().getUuid());
               }
            }
            else {
                throw new ResourceNotFoundException("Vehiculo", "uuid", inspeccionDto.getVehiculoDto().getUuid());
            }
        }
        throw new ResourceNotFoundException("Actividad", "uuid", inspeccionDto.getActividadDto().getUuid());
    }

    private List<DetalleInspeccion> mapearDetalleInspeccion(List<DetalleInspeccionDto> detalleInspeccionDtoList, Inspeccion nuevoInspeccion) {
        Set<String> uuidsTipoParametro = new HashSet<>();
        return detalleInspeccionDtoList.stream().map(detalleInspeccionDto -> {
            if(uuidsTipoParametro.contains(detalleInspeccionDto.getUuid().toLowerCase().trim())){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST,"la uuid del Tipo Parametro '" + detalleInspeccionDto.getUuid() + "' ya existe o es duplicado");
            }
            uuidsTipoParametro.add(detalleInspeccionDto.getUuid().toLowerCase().trim());
            TipoParametro tipoParametro = obtenerTipoParametro( detalleInspeccionDto.getTipoParametroDto().getUuid());

            Integer ultimaEjecucion = detalleInspeccionRepository.findUltimaEjecucionByUuidInspeccion(nuevoInspeccion.getUuid());
            int nuevaEjecucion = (ultimaEjecucion == null) ? 1 : ultimaEjecucion + 1;

            return DetalleInspeccionMapper.toDetalleInspeccion(detalleInspeccionDto)
                    .setInspeccion(nuevoInspeccion)
                    .setNroEjecucion(ultimaEjecucion)
                    .setTipoParametro(tipoParametro);
        }).collect(Collectors.toList());
    }

    private TipoParametro obtenerTipoParametro(String tipoParametroUuid) {
        Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByUuid(tipoParametroUuid);
        if (tipoParametroOptional.isEmpty()) {
            throw new ResourceNotFoundException("tipo parametro", "uuid", tipoParametroUuid);
        }
        return tipoParametroOptional.get();
    }


    @Override
    public InspeccionDto actualizarInspeccion(InspeccionDto inspeccionDto) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(inspeccionDto.getUuid());
        if(inspeccionOptional.isPresent()) {
            Optional<Actividad> actividadOptional = actividadRepository.findByUuid(inspeccionDto.getActividadDto().getUuid());
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(inspeccionDto.getVehiculoDto().getUuid());
            Optional<Conductor> conductorOptional = conductorRepository.findByUuid(inspeccionDto.getConductorDto().getUuid());
            if(actividadOptional.isPresent()){
                if (vehiculoOptional.isPresent()){
                    if (conductorOptional.isPresent()) {
                        Inspeccion updateInspeccion = InspeccionMapper.toInspeccion(inspeccionDto);
                        updateInspeccion.setIdInspeccion(inspeccionOptional.get().getIdInspeccion());
                        updateInspeccion.setActividad(actividadOptional.get());
                        updateInspeccion.setVehiculo(vehiculoOptional.get());
                        updateInspeccion.setConductor(conductorOptional.get());

                        if (inspeccionDto != null && inspeccionDto.getEventoDto() != null && inspeccionDto.getEventoDto().getUuid() != null) {
                            Optional<Evento> eventoOptional = eventoRepository.findByUuid(inspeccionDto.getEventoDto().getUuid());
                            updateInspeccion.setEvento(eventoOptional.get());
                        }
                        return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(updateInspeccion));
                    }
                    else {
                        throw new ResourceNotFoundException("Conductor", "uuid", inspeccionDto.getConductorDto().getUuid());
                    }
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

    @Override
    public List<InspeccionDto> obtenerInspeccionPorUuidActividad(String uuidActividad) {
        List<Inspeccion> inspeccionList = inspeccionRepository.findByTipoActividad(uuidActividad);
        return inspeccionList.stream().map( inspeccion -> {
            return InspeccionMapper.toInspeccionDto(inspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionDto> obtenerInspeccionPorFechaInspeccion(Date fechaInspeccion) {
        List<Inspeccion> inspeccionList = inspeccionRepository.findByFechaInspeccion(fechaInspeccion);
        return inspeccionList.stream().map(inspeccion -> {
            return InspeccionMapper.toInspeccionDto(inspeccion);
        }).collect(Collectors.toList());
    }

    public int obtenerNumeroIntentoActual(Vehiculo vehiculo) {
        List<Inspeccion> inspecciones = inspeccionRepository
                .findByVehiculoAndResultadoFalseOrderByFechaInspeccionDesc(vehiculo);

        for (Inspeccion inspeccion : inspecciones) {
            boolean tieneNotificacionActiva = inspeccion.getNotificacionList().stream()
                    .anyMatch(n -> n.getStatusNotificacion() != EstadoNotificacion.CUMPLIDA &&
                            n.getStatusNotificacion() != EstadoNotificacion.FALLIDA
                    );

            if (tieneNotificacionActiva) {
                return 2; //segunda
            }
        }
        return 1; //primera
    }
}
