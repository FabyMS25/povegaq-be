package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDto;
import com.gamq.ambiente.dto.mapper.DetalleInspeccionMapper;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.*;

import com.gamq.ambiente.repository.*;
import com.gamq.ambiente.service.InspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    EquipoRepository equipoRepository;

    @Override
    public InspeccionDto obtenerInspeccionPorUuid(String uuid) {
        Inspeccion inspeccion = obtenerInspeccionPorUuidOThrow(uuid);
        return InspeccionMapper.toInspeccionDto(inspeccion);
    }

    @Override
    public List<InspeccionDto> obtenerInspeccionPorPlaca(String placa) {
        List<Inspeccion> inspeccionOptional = inspeccionRepository.findByPlacaVehiculo(placa);
        return inspeccionOptional.stream().map(inspeccion -> {
            return InspeccionMapper.toInspeccionDto(inspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InspeccionDto> obtenerInspeccionPorUuidUsuario(String uuidUsuario) {
        List<Inspeccion> inspeccionList = inspeccionRepository.findByUuidUsuario(uuidUsuario);
        return inspeccionList.stream().map(inspeccion -> {
            return InspeccionMapper.toInspeccionDto(inspeccion);
        }).collect(Collectors.toList());
    }


    @Override
    public List<InspeccionDto> obtenerInspecciones() {
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        return inspeccionList.stream().map(inspeccion -> {
            return InspeccionMapper.toInspeccionDto(inspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public InspeccionDto crearInspeccion(InspeccionDto inspeccionDto) {
        String uuidUsuario = inspeccionDto.getUuidUsuario();
        if (uuidUsuario == null || uuidUsuario.trim().isEmpty()) {
            throw new ResourceNotFoundException("Inspeccion", "uuidUsuario", uuidUsuario);
        }
        String nombreInspector = inspeccionDto.getNombreInspector();
        if (nombreInspector == null || nombreInspector.trim().isEmpty()) {
            throw new ResourceNotFoundException("Inspeccion", "nombreInspector", nombreInspector);
        }

        Actividad actividad = actividadRepository.findByUuid(inspeccionDto.getActividadDto().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Actividad", "uuid", inspeccionDto.getActividadDto().getUuid()));
        Vehiculo vehiculo = vehiculoRepository.findByUuid(inspeccionDto.getVehiculoDto().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "uuid", inspeccionDto.getVehiculoDto().getUuid()));
        Equipo equipo = equipoRepository.findByUuid(inspeccionDto.getEquipoDto().getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Equipo", "uuid", inspeccionDto.getEquipoDto().getUuid()));

        Inspeccion nuevoInspeccion = InspeccionMapper.toInspeccion(inspeccionDto);
        nuevoInspeccion.setActividad(actividad);
        nuevoInspeccion.setVehiculo(vehiculo);

        if(inspeccionDto.getConductorDto() != null &&
           inspeccionDto.getConductorDto().getUuid() != null){
            Conductor conductor = conductorRepository.findByUuid(inspeccionDto.getConductorDto().getUuid())
                    .orElseThrow(() -> new ResourceNotFoundException("Conductor", "uuid", inspeccionDto.getConductorDto().getUuid()));
            nuevoInspeccion.setConductor(conductor);
        }

        nuevoInspeccion.setEquipo(equipo);
        if (inspeccionDto != null && inspeccionDto.getEventoDto() != null && inspeccionDto.getEventoDto().getUuid() != null) {
            Optional<Evento> eventoOptional = eventoRepository.findByUuid(inspeccionDto.getEventoDto().getUuid());
            nuevoInspeccion.setEvento(eventoOptional.get());
        }

        List<DetalleInspeccion> detalleInspeccionList = mapearDetalleInspeccion(inspeccionDto.getDetalleInspeccionDtoList(), nuevoInspeccion);
        nuevoInspeccion.setDetalleInspeccionList(detalleInspeccionList);
        return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(nuevoInspeccion));
    }

    private List<DetalleInspeccion> mapearDetalleInspeccion(List<DetalleInspeccionDto> detalleInspeccionDtoList, Inspeccion nuevoInspeccion) {
        Set<String> uuidsTipoParametro = new HashSet<>();
        return detalleInspeccionDtoList.stream().map(detalleInspeccionDto -> {
            if (uuidsTipoParametro.contains(detalleInspeccionDto.getUuid().toLowerCase().trim())) {
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "la uuid del Tipo Parametro '" + detalleInspeccionDto.getUuid() + "' ya existe o es duplicado");
            }
            uuidsTipoParametro.add(detalleInspeccionDto.getUuid().toLowerCase().trim());
            TipoParametro tipoParametro = obtenerTipoParametro(detalleInspeccionDto.getTipoParametroDto().getUuid());

            Integer ultimaEjecucion = detalleInspeccionRepository.findUltimaEjecucionByUuidInspeccion(nuevoInspeccion.getUuid());
            int nuevaEjecucion = (ultimaEjecucion == null) ? 1 : ultimaEjecucion + 1;

            return DetalleInspeccionMapper.toDetalleInspeccion(detalleInspeccionDto)
                    .setInspeccion(nuevoInspeccion)
                    .setNroEjecucion(ultimaEjecucion)
                    .setTipoParametro(tipoParametro);
        }).collect(Collectors.toList());
    }

    private TipoParametro obtenerTipoParametro(String tipoParametroUuid) {
        return tipoParametroRepository.findByUuid(tipoParametroUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo Parametro", "uuid", tipoParametroUuid));
    }


    @Override
    public InspeccionDto actualizarInspeccion(InspeccionDto inspeccionDto) {
        Inspeccion inspeccion = inspeccionRepository.findByUuid(inspeccionDto.getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Inspeccion", "uuid", inspeccionDto.getUuid()));
        Actividad actividad = actividadRepository.findByUuid(inspeccionDto.getActividadDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Actividad", "uuid", inspeccionDto.getActividadDto().getUuid()));
        Vehiculo vehiculo = vehiculoRepository.findByUuid(inspeccionDto.getVehiculoDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Vehiculo", "uuid", inspeccionDto.getVehiculoDto().getUuid()));
        Equipo equipo = equipoRepository.findByUuid(inspeccionDto.getEquipoDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Equipo", "uuid", inspeccionDto.getEquipoDto().getUuid()));

        Inspeccion updateInspeccion = InspeccionMapper.toInspeccion(inspeccionDto);
        updateInspeccion.setIdInspeccion(inspeccion.getIdInspeccion());
        updateInspeccion.setActividad(actividad);
        updateInspeccion.setVehiculo(vehiculo);

        if (inspeccionDto.getConductorDto() != null &&
            inspeccionDto.getConductorDto().getUuid() != null){
            Conductor conductor = conductorRepository.findByUuid(inspeccionDto.getConductorDto().getUuid())
                    .orElseThrow(()-> new ResourceNotFoundException("Conductor", "uuid", inspeccionDto.getConductorDto().getUuid()));
            updateInspeccion.setConductor(conductor);
        }

        updateInspeccion.setEquipo(equipo);

        if (inspeccionDto != null && inspeccionDto.getEventoDto() != null && inspeccionDto.getEventoDto().getUuid() != null) {
            Optional<Evento> eventoOptional = eventoRepository.findByUuid(inspeccionDto.getEventoDto().getUuid());
            updateInspeccion.setEvento(eventoOptional.get());
        }
        return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(updateInspeccion));
    }

    @Override
    public InspeccionDto eliminarInspeccion(String uuid) {
        Inspeccion inspeccion = obtenerInspeccionPorUuidOThrow(uuid);
        if (!inspeccion.getCertificadoList().isEmpty()) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "la Inspeccion ya esta siendo usado por los certificados");
        }
        inspeccionRepository.delete(inspeccion);
        return InspeccionMapper.toInspeccionDto(inspeccion);
    }

    @Override
    public List<InspeccionDto> obtenerInspeccionPorUuidActividad(String uuidActividad) {
        List<Inspeccion> inspeccionList = inspeccionRepository.findByTipoActividad(uuidActividad);
        return inspeccionList.stream().map(inspeccion -> {
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

    private Inspeccion obtenerInspeccionPorUuidOThrow(String uuid) {
        return inspeccionRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Inspeccion", "uuid", uuid));
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
