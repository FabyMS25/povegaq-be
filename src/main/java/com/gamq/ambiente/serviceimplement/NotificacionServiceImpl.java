package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.NotificacionDto;
import com.gamq.ambiente.dto.NotificacionIntentoDto;
import com.gamq.ambiente.dto.NotificacionIntentoView;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NotificacionServiceImpl implements NotificacionService {
    @Value("${spring.jackson.time-zone}")
    private String zonaHorario;
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
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(notificacionDto.getInspeccionDto().getUuid());
        if(inspeccionOptional.isEmpty()){
            throw new ResourceNotFoundException("inspeccion", "uuid", notificacionDto.getInspeccionDto().getUuid());
        }
        if (!esPosibleNotificar(inspeccionOptional.get().getVehiculo())) {
                  throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "ya no es posible notificar");
        }
        if ( inspeccionOptional.get().isResultado()){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "no es posible notificar por que resultado de inspeccion es true");
        }

        Notificacion nuevoNotificacion = NotificacionMapper.toNotificacion(notificacionDto);
        nuevoNotificacion.setInspeccion(inspeccionOptional.get());
        return NotificacionMapper.toNotificacionDto(notificacionRepository.save(nuevoNotificacion));
    }

    @Override
    public NotificacionDto generarNotificacionVistaPrevia(String uuidInpeccion) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(uuidInpeccion);
        if(inspeccionOptional.isEmpty()){
            throw new ResourceNotFoundException("inspeccion", "uuid", uuidInpeccion);
        }

        if ( inspeccionOptional.get().isResultado()){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "No es posible generar una notificacion por que el resultado de inspeccion Positivo o true");
        }

        if (notificacionRepository.existsByInspeccion_VehiculoAndTypeNotificacionAndFechaAsistenciaGreaterThanEqual(inspeccionOptional.get().getVehiculo(), TipoNotificacion.REINSPECCION_PENDIENTE, new Date() ) ){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "Tiene un notificacion vigente");
        }
        Optional<NotificacionIntentoDto> notificacionIntentoDto = notificacionRepository.getNumeroIntentoNotificacionByUuidVehiculo(inspeccionOptional.get().getVehiculo().getUuid()).map(vi->
                new NotificacionIntentoDto(
                        vi.getUuidVehiculo(),
                        vi.getIntentosValidos(),
                        vi.getPuedeEmitirNuevaNotificacion(),
                        vi.getProximoTipoNotificacion())
                );

       // int intento = inspeccionService.obtenerNumeroIntentoActual(inspeccionOptional.get().getVehiculo());

        NotificacionDto notificacionDto = new NotificacionDto();
        notificacionDto.setActividad( inspeccionOptional.get().getActividad().getTipoActividad());
       // if ( intento == 1 && !existeNotificacionVencidaReinspeccion(inspeccionOptional.get().getVehiculo())) {
        if ( notificacionIntentoDto.get().getIntentosValidos() == 0 ) {
            notificacionDto.setTypeNotificacion(TipoNotificacion.REINSPECCION_PENDIENTE);
            notificacionDto.setFechaNotificacion(new Date());
            notificacionDto.setFechaAsistencia(FechaUtil.sumarDias(inspeccionOptional.get().getFechaInspeccion(), 365));
            notificacionDto.setStatusNotificacion(EstadoNotificacion.ENTREGADA);
            notificacionDto.setObservacion("Se detectó exceso en emisión. Plazo 1 año para adecuación técnica.");
            notificacionDto.setNumeroIntento(1);
        }
        if (notificacionIntentoDto.get().getIntentosValidos() == 1) {
       // if ( (intento == 2) && existeNotificacionVencidaReinspeccion(inspeccionOptional.get().getVehiculo())) {
            notificacionDto.setTypeNotificacion(TipoNotificacion.INFRACCION);
            notificacionDto.setFechaNotificacion(new Date());
            notificacionDto.setFechaAsistencia(FechaUtil.sumarDias(new Date(),90));
            notificacionDto.setStatusNotificacion(EstadoNotificacion.PENDIENTE);
            notificacionDto.setObservacion("No realizó adecuación técnica tras primera notificación. Multa 3er grado.");
            notificacionDto.setNumeroIntento(2);
            notificacionDto.setSancion("Multa 3er grado");
        }
        if ( notificacionIntentoDto.get().getIntentosValidos()  == 2 ){
            notificacionDto.setTypeNotificacion(TipoNotificacion.INFRACCION_FINAL); // o solo INFRACCION
            notificacionDto.setFechaNotificacion(new Date());
            notificacionDto.setFechaAsistencia(new Date());
            notificacionDto.setStatusNotificacion(EstadoNotificacion.PENDIENTE);
            notificacionDto.setObservacion("No adecuó el vehículo dentro de los 90 días posteriores a la segunda inspección.");
            notificacionDto.setSancion("Multa 3er grado por incumplimiento final");
            notificacionDto.setNumeroIntento(3);
        }
        notificacionDto.setInspeccionDto(InspeccionMapper.toInspeccionDto(inspeccionOptional.get()));
        return notificacionDto;
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

        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(notificacionDto.getInspeccionDto().getUuid());
        if(inspeccionOptional.isEmpty()){
            throw new ResourceNotFoundException("inspeccion", "uuid", notificacionDto.getInspeccionDto().getUuid());
        }
        if (!esPosibleNotificar(inspeccionOptional.get().getVehiculo())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "no es posible notificar");
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
        LocalDate fechaPlazo = notificacion.getFechaAsistencia()
                .toInstant()
                .atZone(ZoneId.of(zonaHorario))
                .toLocalDate();

        if (nuevoEstadoNotificacion == EstadoNotificacion.VENCIDA &&
                !LocalDate.now().isAfter(fechaPlazo)
        ){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la notificacion tiene plazo");
        }
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

    @Override
    public List<NotificacionDto> obtenerNotificacionesPorFechaAsistenciaVencida() {
        List<Notificacion> notificacionList = notificacionRepository.findNotificacionesByFechaAsistenciaVencida(Arrays.asList(EstadoNotificacion.CUMPLIDA, EstadoNotificacion.FALLIDA));
        return notificacionList.stream().map(notificacion -> {
            return NotificacionMapper.toNotificacionDto(notificacion);
        }).collect(Collectors.toList());
    }

    @Override
    public boolean esPosibleNotificar(Vehiculo vehiculo) {
        int cantidad = getCantidadNotificacionesVencidas(vehiculo);
        return cantidad < 3; // máximo 3 intentos válidos contar desde 0,1,2
    }

    private  boolean existeNotificacionVencidaReinspeccion(Vehiculo vehiculo) {
        return notificacionRepository.existsByInspeccion_VehiculoAndTypeNotificacionAndStatusNotificacionIn(
                vehiculo,
                TipoNotificacion.REINSPECCION_PENDIENTE,
                Arrays.asList(EstadoNotificacion.VENCIDA)
        );
    }

    private int getCantidadNotificacionesVencidas(Vehiculo vehiculo) {
        List<EstadoNotificacion> estadosValidos = List.of(
                EstadoNotificacion.VENCIDA
        );
        int cantidad = notificacionRepository.countByInspeccion_VehiculoAndStatusNotificacionIn(vehiculo, estadosValidos);
        return cantidad;
    }
}
