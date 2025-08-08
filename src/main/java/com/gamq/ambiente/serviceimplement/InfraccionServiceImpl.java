package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.mapper.InfraccionMapper;
import com.gamq.ambiente.enumeration.StatusInfraccion;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.*;
import com.gamq.ambiente.repository.*;
import com.gamq.ambiente.service.ConfiguracionService;
import com.gamq.ambiente.service.InfraccionService;
import com.gamq.ambiente.utils.ZonaHorariaBolivia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InfraccionServiceImpl implements InfraccionService {
    @Autowired
    InfraccionRepository infraccionRepository;
    @Autowired
    TipoInfraccionRepository tipoInfraccionRepository;
    @Autowired
    InspeccionRepository inspeccionRepository;
    @Autowired
    DetalleInspeccionRepository detalleInspeccionRepository;
    @Autowired
    GeneradorInfraccionServiceImpl generadorInfraccionService;
    @Autowired
    VehiculoRepository vehiculoRepository;
    @Autowired
    AlertaRepository alertaRepository;
    @Autowired
    ConfiguracionService configuracionService;
    @Autowired
    ZonaHorariaBolivia zonaHorariaBolivia;
    @Autowired
    NotificacionRepository notificacionRepository;

    @Override
    public InfraccionDto obtenerInfraccionPorUuid(String uuid) {
        Optional<Infraccion> infraccionOptional = infraccionRepository.findByUuid(uuid);
        if(infraccionOptional.isPresent()){
            Infraccion infraccion = infraccionOptional.get();
            this.calcularYSetearEnPlazo(infraccion);
            return InfraccionMapper.toInfraccionDto(infraccion);
        }
        throw new ResourceNotFoundException("Infraccion", "uuid", uuid);
    }

    @Override
    public List<InfraccionDto> obtenerInfraccionPorFecha(Date fecha) {
        List<Infraccion> infraccionList = infraccionRepository.findByFechaInfraccion(fecha);
        return infraccionList.stream()
                .peek(this::calcularYSetearEnPlazo)
                .map(infraccion -> {
            return InfraccionMapper.toInfraccionDto(infraccion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InfraccionDto> obtenerInfracciones() {
        List<Infraccion> infraccionList = infraccionRepository.findAll();
        return  infraccionList.stream()
                .peek(this::calcularYSetearEnPlazo)
                .map(infraccion -> {
            return  InfraccionMapper.toInfraccionDto(infraccion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InfraccionDto> obtenerInfraccionPorVehiculo(String uuidVehiculo) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(uuidVehiculo);
        if (vehiculoOptional.isEmpty()){
            throw new ResourceNotFoundException("Vehiculo","uuid", uuidVehiculo);
        }
        List<Infraccion> infraccionList = infraccionRepository.findByVehiculo(vehiculoOptional.get());
        return  infraccionList.stream()
                .peek(this::calcularYSetearEnPlazo)
                .map( infraccion -> {
            return  InfraccionMapper.toInfraccionDto(infraccion);
        }).collect(Collectors.toList());
    }

    @Override
    public InfraccionDto crearInfraccion(InfraccionDto infraccionDto) {
        Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findByUuid(infraccionDto.getTipoInfraccionDto().getUuid());
        if (tipoInfraccionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Tipo Infraccion","uuid", infraccionDto.getTipoInfraccionDto().getUuid());
        }
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(infraccionDto.getVehiculoDto().getUuid());
        if (vehiculoOptional.isEmpty()) {
            throw new ResourceNotFoundException("Vehiculo","uuid", infraccionDto.getVehiculoDto().getUuid());
        }

        Infraccion nuevoInfraccion = InfraccionMapper.toInfraccion(infraccionDto);
        nuevoInfraccion.setTipoInfraccion(tipoInfraccionOptional.get());

        if(infraccionDto.getInspeccionDto() != null &&
           infraccionDto.getInspeccionDto().getUuid() != null )
        {
            Inspeccion inspeccion = inspeccionRepository.findByUuid(infraccionDto.getInspeccionDto().getUuid())
                    .orElseThrow(()-> new ResourceNotFoundException("Inspeccion", "uuid", infraccionDto.getInspeccionDto().getUuid()));
             nuevoInfraccion.setInspeccion(inspeccion);
        }

        if(infraccionDto.getNotificacionDto() != null &&
           infraccionDto.getNotificacionDto().getUuid() != null)
        {
            Notificacion notificacion = notificacionRepository.findByUuid(infraccionDto.getNotificacionDto().getUuid())
                    .orElseThrow(()-> new ResourceNotFoundException("Notificacion", "uuid", infraccionDto.getNotificacionDto().getUuid()));
            nuevoInfraccion.setNotificacion(notificacion);
        }

        nuevoInfraccion.setVehiculo(vehiculoOptional.get());
        nuevoInfraccion.setGeneradoSistema(false);
        Infraccion nuevoInfraccionGrabada = infraccionRepository.save(nuevoInfraccion);
        generarAlertaParaInfraccion(nuevoInfraccionGrabada);
        return InfraccionMapper.toInfraccionDto(nuevoInfraccionGrabada);
    }

    @Override
    public InfraccionDto actualizarInfraccion(InfraccionDto infraccionDto) {
        Optional<Infraccion> infraccionOptional = infraccionRepository.findByUuid(infraccionDto.getUuid());
        if(infraccionOptional.isPresent()) {
            Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(infraccionDto.getVehiculoDto().getUuid());
            if (vehiculoOptional.isEmpty()) {
                throw new ResourceNotFoundException("Vehiculo","uuid", infraccionDto.getVehiculoDto().getUuid());
            }
            Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findByUuid(infraccionDto.getTipoInfraccionDto().getUuid());
            if (tipoInfraccionOptional.isEmpty()) {
                throw new ResourceNotFoundException("Tipo Infraccion","uuid", infraccionDto.getTipoInfraccionDto().getUuid());
            }

            Infraccion updateInfraccion = InfraccionMapper.toInfraccion(infraccionDto);
            updateInfraccion.setIdInfraccion(infraccionOptional.get().getIdInfraccion());

            if(infraccionDto.getInspeccionDto() != null &&
                    infraccionDto.getInspeccionDto().getUuid() != null )
            {
                Inspeccion inspeccion = inspeccionRepository.findByUuid(infraccionDto.getInspeccionDto().getUuid())
                        .orElseThrow(()-> new ResourceNotFoundException("Inspeccion", "uuid", infraccionDto.getInspeccionDto().getUuid()));
                updateInfraccion.setInspeccion(inspeccion);
            }

            if(infraccionDto.getNotificacionDto() != null &&
               infraccionDto.getNotificacionDto().getUuid() != null){
                Notificacion notificacion = notificacionRepository.findByUuid(infraccionDto.getNotificacionDto().getUuid())
                        .orElseThrow(()-> new ResourceNotFoundException("Notificacion", "uuid", infraccionDto.getNotificacionDto().getUuid()));
                updateInfraccion.setNotificacion(notificacion);
            }

            updateInfraccion.setTipoInfraccion(tipoInfraccionOptional.get());
            updateInfraccion.setMontoTotal(tipoInfraccionOptional.get().getValorUFV());
            updateInfraccion.setStatusInfraccion(StatusInfraccion.GENERADA);
            updateInfraccion.setEstadoPago(false);
            updateInfraccion.setVehiculo(vehiculoOptional.get());
            updateInfraccion.setGeneradoSistema(false);
            infraccionRepository.save(updateInfraccion);
            this.calcularYSetearEnPlazo(updateInfraccion);
            return InfraccionMapper.toInfraccionDto(updateInfraccion);
        }
        throw new ResourceNotFoundException("Infraccion", "uuid",infraccionDto.getUuid());
    }

    @Override
    public InfraccionDto eliminarInfraccion(String uuid) {
        Infraccion infraccionQBE = new Infraccion(uuid);
        Optional<Infraccion> optionalInfraccion = infraccionRepository.findOne(Example.of(infraccionQBE));
        if(optionalInfraccion.isPresent()){
            Infraccion infraccion = optionalInfraccion.get();
            if (infraccion.isEstadoPago()) {
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "la Infraccion ha sido cancelada no puede eliminar");
            }
            infraccionRepository.delete(infraccion);
            return InfraccionMapper.toInfraccionDto(infraccion);
        }
        throw new ResourceNotFoundException("Infraccion","uuid", uuid);
    }

    @Transactional
    public InfraccionDto marcarInfraccionComoPagada(String uuidInfraccion, String numeroTasa, Date fechaPago) {
        Infraccion infraccion = infraccionRepository.findByUuid(uuidInfraccion)
                .orElseThrow(() -> new ResourceNotFoundException("Infraccion", "uuid", uuidInfraccion));

        if (infraccion.getStatusInfraccion() == StatusInfraccion.PAGADA ||
                infraccion.getStatusInfraccion() == StatusInfraccion.CANCELADA) {
            throw new IllegalStateException("La infracción ya está pagada o cancelada.");
        }

        infraccion.setEstadoPago(true);
        infraccion.setFechaPago(fechaPago);
        infraccion.setNumeroTasa(numeroTasa);
        infraccion.setStatusInfraccion(StatusInfraccion.PAGADA);

        infraccionRepository.save(infraccion);
        return InfraccionMapper.toInfraccionDto(infraccion);
    }

    @Transactional
    public InfraccionDto notificarInfraccion(String uuidInfraccion) {
        Infraccion infraccion = infraccionRepository.findByUuid(uuidInfraccion)
                .orElseThrow(() -> new ResourceNotFoundException("Infraccion", "uuid", uuidInfraccion));

        if (infraccion.getStatusInfraccion() != StatusInfraccion.NOTIFICADA) {
            infraccion.setStatusInfraccion(StatusInfraccion.NOTIFICADA);
            infraccionRepository.save(infraccion);
        }

        this.calcularYSetearEnPlazo(infraccion);
        return InfraccionMapper.toInfraccionDto(infraccion);
    }

    public InfraccionDto actualizarStatusInfraccion(String uuidInfraccion, StatusInfraccion nuevoStatus) {
        Infraccion infraccion = infraccionRepository.findByUuid(uuidInfraccion)
                .orElseThrow(() -> new ResourceNotFoundException("Infraccion", "uuid", uuidInfraccion));

        infraccion.setStatusInfraccion(nuevoStatus);
        return InfraccionMapper.toInfraccionDto(infraccionRepository.save(infraccion));
    }

     //EVALUAR LOS METODOS
    @Override
    public InfraccionDto generarInfraccion(String uuidInspeccion) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(uuidInspeccion);
        if(inspeccionOptional.isEmpty()){
            throw new ResourceNotFoundException("Inspeccion","uuid", uuidInspeccion);
        }
        InfraccionDto infraccionDto = generadorInfraccionService.generarDesdeInspeccion(inspeccionOptional.get());
        if(infraccionDto == null){
            throw  new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "No es posible generar INFRACCION para inspeccion "+ uuidInspeccion);
        }
        return infraccionDto;//  InfraccionMapper.toInfraccionDto(infraccion);
    }

    @Transactional
    public void generarAlertaParaInfraccion(Infraccion infraccion){
        Alerta alerta = new Alerta();
        alerta.setInfraccion(infraccion);
        alerta.setEstado(false);
        alerta.setFechaAlerta(infraccion.getFechaInfraccion());
        alerta.setMensaje("El vehiculo con placa " + infraccion.getVehiculo().getPlaca() + " tiene una infraccion");
        alerta.setTipo("INFRACCION");
        alerta.setRolDestinatario(infraccion.getVehiculo().getPropietario() != null?"PROPIETARIO":"VEHICULO");
        alerta.setUuidDestinatario(infraccion.getVehiculo().getPropietario() != null? infraccion.getVehiculo().getPropietario().getUuid(): infraccion.getVehiculo().getUuid());
        alertaRepository.save(alerta);
    }

    public void calcularYSetearEnPlazo(Infraccion infraccion) {
        int plazoPagoInfraccionEnDias = configuracionService.obtenerValorEntero("infraccion.plazo_pago.dias");
        boolean estaEnPlazo = estaEnPlazo(infraccion, plazoPagoInfraccionEnDias);
        infraccion.setEnPlazo(estaEnPlazo);
    }

    public boolean estaEnPlazo(Infraccion infraccion, int diasPermitidos) {
        if (infraccion.getStatusInfraccion() == StatusInfraccion.PAGADA
                || infraccion.getStatusInfraccion() == StatusInfraccion.CANCELADA
                || infraccion.getStatusInfraccion() == StatusInfraccion.VENCIDA
                || infraccion.getFechaInfraccion() == null) {
            return false;
        }

        LocalDate fecha = infraccion.getFechaInfraccion()
                .toInstant()
                .atZone(zonaHorariaBolivia.getZonaId())
                .toLocalDate();
        return !LocalDate.now().isAfter(fecha.plusDays(diasPermitidos));
    }
}
