package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.NotificacionIntentoDto;
import com.gamq.ambiente.dto.mapper.NotificacionMapper;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Notificacion;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.NotificacionRepository;
import com.gamq.ambiente.service.InspeccionService;
import com.gamq.ambiente.service.NotificacionService;
import com.gamq.ambiente.utils.FechaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NotificacionServiceImpl implements NotificacionService {
    @Autowired
    NotificacionRepository notificacionRepository;

    @Autowired
    InspeccionRepository inspeccionRepository;

    @Autowired
    InspeccionService inspeccionService;

    @Override
    public NotificacionDto obtenerNotificacionPorUuid(String uuid) {
        Optional<Notificacion> notificacionOptional = notificacionRepository.findByUuid(uuid);
        if(notificacionOptional.isPresent()) {
            return NotificacionMapper.toNotificacionDto(notificacionOptional.get());
        }
        throw new ResourceNotFoundException("Notificacion", "uuid", uuid);
    }

    @Override
    public List<NotificacionDto> obtenerNotificaciones() {
        List<Notificacion> notificacionList = notificacionRepository.findAll();
        return  notificacionList.stream().map( notificacion -> {
            return  NotificacionMapper.toNotificacionDto(notificacion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<NotificacionDto> obtenerPorTipoNotificacion(TipoNotificacion typeNotificacion) {
        List<Notificacion> notificacionList = notificacionRepository.findByTypeNotificacion(typeNotificacion);
        return notificacionList.stream().map(notificacion -> {
            return NotificacionMapper.toNotificacionDto(notificacion);
        }).collect(Collectors.toList());
   }

    @Override
    public NotificacionDto crearNotificacion(NotificacionDto notificacionDto) {
        Optional<Notificacion> notificacionOptional = notificacionRepository.findByNumeroNotificacion(notificacionDto.getNumeroNotificacion());
        if (notificacionOptional.isPresent()){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el numero de notificacion ya existe");
        }
        if (notificacionDto.getInspeccionDto() == null || !esPosibleNotificar(notificacionDto.getInspeccionDto().getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "no es posible notificar");
        }
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(notificacionDto.getInspeccionDto().getUuid());
        if(inspeccionOptional.isEmpty()){
            throw new ResourceNotFoundException("inspeccion", "uuid", notificacionDto.getInspeccionDto().getUuid());
        }
        if ( inspeccionOptional.get().isResultado()){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "no es posible notificar por que resultado de inspeccion es true");
        }
        int intento = inspeccionService.obtenerNumeroIntentoActual(inspeccionOptional.get().getVehiculo());
        notificacionDto.setTypeNotificacion(determinarTipoNotificacion(inspeccionOptional.get()));
        notificacionDto.setFechaAsistencia(intento==1 ? FechaUtil.sumarDias(new Date(),365): FechaUtil.sumarDias(new Date(),90));
        Notificacion nuevoNotificacion = NotificacionMapper.toNotificacion(notificacionDto);
        nuevoNotificacion.setInspeccion(inspeccionOptional.get());
        return NotificacionMapper.toNotificacionDto(notificacionRepository.save(nuevoNotificacion));
    }

    @Override
    public NotificacionDto actualizarNotificacion(NotificacionDto notificacionDto) {
        Optional<Notificacion> notificacionOptional = notificacionRepository.findByUuid(notificacionDto.getUuid());
        if(notificacionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Notificacion", "uuid", notificacionDto.getUuid());
        }
        if (notificacionRepository.exitsNotificacionLikeNumeroNotificacion(notificacionDto.getNumeroNotificacion(), notificacionDto.getUuid())){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el numero de notificacion ya existe");
        }
        if (notificacionDto.getInspeccionDto() == null || !esPosibleNotificar(notificacionDto.getInspeccionDto().getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "no es posible notificar");
        }
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(notificacionDto.getInspeccionDto().getUuid());
        if(inspeccionOptional.isEmpty()){
            throw new ResourceNotFoundException("inspeccion", "uuid", notificacionDto.getInspeccionDto().getUuid());
        }
        if ( inspeccionOptional.get().isResultado()){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "no es posible notificar por que resultado de inspeccion es true");
        }

        Notificacion updateNotificacion = NotificacionMapper.toNotificacion(notificacionDto);
        updateNotificacion.setIdNotificacion(notificacionOptional.get().getIdNotificacion());
        updateNotificacion.setInspeccion(inspeccionOptional.get());

        return NotificacionMapper.toNotificacionDto(notificacionRepository.save(updateNotificacion));
    }

    @Override
    public NotificacionDto actualizarTipoNotificacion(String uuidNotificacion, TipoNotificacion nuevoTipoNotificacion) {
        Notificacion notificacion = notificacionRepository.findByUuid(uuidNotificacion)
                .orElseThrow(() -> new ResourceNotFoundException("notificacion", "uuid", uuidNotificacion));
        notificacion.setTypeNotificacion(nuevoTipoNotificacion);
        return NotificacionMapper.toNotificacionDto(notificacionRepository.save(notificacion));

    }

    @Override
    public NotificacionDto actualizarEstadoNotificacion(String uuidNotificacion, EstadoNotificacion nuevoEstadoNotificacion) {
        Notificacion notificacion = notificacionRepository.findByUuid(uuidNotificacion)
                .orElseThrow( () -> new ResourceNotFoundException("notificacion", "uuid", uuidNotificacion));
        notificacion.setStatusNotificacion(nuevoEstadoNotificacion);
        return  NotificacionMapper.toNotificacionDto(notificacionRepository.save(notificacion));
    }

    @Override
    public NotificacionDto eliminarNotificacion(String uuid) {
        Notificacion notificacionQBE = new Notificacion(uuid);
        Optional<Notificacion> optionalNotificacion = notificacionRepository.findOne(Example.of(notificacionQBE));
        if(optionalNotificacion.isPresent()){
            Notificacion notificacion = optionalNotificacion.get();
            notificacionRepository.delete(notificacion);
            return NotificacionMapper.toNotificacionDto(notificacion);
        }
        throw new ResourceNotFoundException("Notificacion","uuid", uuid);
    }

    //PROCESOS DE NOTIFICACIONES
    public NotificacionDto EncontrarNotificacionPendientePorUuidVehiculo(String uuidVehiculo){
        return null;
    }

    @Override
    public NotificacionIntentoDto ObtenerNotificacionIntentoPorInspeccion(String uuidInspeccion) {
        //intento = 1 plazo 1 year
        //intento =2 plazo 90 dias
        //intento = 3 plazo inicia un proceso
        int intentosActuales = getCantidadNotificaciones(uuidInspeccion);

        boolean puedeEmitirNueva = intentosActuales < 3;

        NotificacionIntentoDto notificacionIntentoDto = new NotificacionIntentoDto(
                uuidInspeccion,
                intentosActuales,
                puedeEmitirNueva,
                generarMensajeIntentoNotificacion(intentosActuales)
        );
        return  notificacionIntentoDto;
    }

    private String generarMensajeIntentoNotificacion(Integer intentoNotificacion){
        intentoNotificacion = intentoNotificacion + 1;
        return (intentoNotificacion < 3) ? intentoNotificacion.toString() + " intento disponible": "No disponible";
    }

    public TipoNotificacion determinarTipoNotificacion(Inspeccion inspeccion) {
        Vehiculo vehiculo = inspeccion.getVehiculo();
        int intento = inspeccionService.obtenerNumeroIntentoActual(vehiculo);

        if (!inspeccion.isResultado()) {
            if (intento == 1) {
                return TipoNotificacion.REINSPECCION_PENDIENTE;
            } else {
                return TipoNotificacion.INFRACCION;
            }
        } else {
            return TipoNotificacion.RECORDATORIO;
        }
    }

    @Override
    public int numeroIntentoNotificacion(String uuidInspeccion) {
        return getCantidadNotificaciones(uuidInspeccion);
    }

    private int getCantidadNotificaciones(String uuidInspeccion) {
        List<EstadoNotificacion> estadosValidos = List.of(
                EstadoNotificacion.ENTREGADA,
                EstadoNotificacion.PENDIENTE
        );
        int cantidad = notificacionRepository.countByInspeccion_UuidAndStatusNotificacionIn(uuidInspeccion, estadosValidos);
        return cantidad;
    }

    @Override
    public boolean esPosibleNotificar(String uuidInspeccion) {
        int cantidad = getCantidadNotificaciones(uuidInspeccion);
        return cantidad < 3; // máximo 3 intentos válidos
    }



}
