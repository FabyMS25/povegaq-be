package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.mapper.InfraccionMapper;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.dto.mapper.TipoInfraccionMapper;
import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.enumeration.StatusInfraccion;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.*;
import com.gamq.ambiente.repository.*;
import com.gamq.ambiente.service.InfraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public InfraccionDto obtenerInfraccionPorUuid(String uuid) {
        Optional<Infraccion> infraccionOptional = infraccionRepository.findByUuid(uuid);
        if(infraccionOptional.isPresent()){
            return InfraccionMapper.toInfraccionDto(infraccionOptional.get());
        }
        throw new ResourceNotFoundException("Infraccion", "uuid", uuid);
    }

    @Override
    public List<InfraccionDto> obtenerInfraccionPorFecha(Date fecha) {
        List<Infraccion> infraccionList = infraccionRepository.findByFechaInfraccion(fecha);
        return infraccionList.stream().map(infraccion -> {
            return InfraccionMapper.toInfraccionDto(infraccion);
        }).collect(Collectors.toList());
    }

    @Override
    public List<InfraccionDto> obtenerInfracciones() {
        List<Infraccion> infraccionList = infraccionRepository.findAll();
        return  infraccionList.stream().map( infraccion -> {
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
        return  infraccionList.stream().map( infraccion -> {
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
            updateInfraccion.setTipoInfraccion(tipoInfraccionOptional.get());
            updateInfraccion.setVehiculo(vehiculoOptional.get());
            updateInfraccion.setGeneradoSistema(false);
            return InfraccionMapper.toInfraccionDto(infraccionRepository.save(updateInfraccion));
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

    private InfraccionDto vistaPreviaInfraccion(Inspeccion inspeccion, GradoInfraccion grado, TipoContribuyente tipoContribuyente, String descripcion) {
        Optional<TipoInfraccion> tipo = tipoInfraccionRepository.findByGradoAndTipoContribuyente( grado, tipoContribuyente);
        InfraccionDto  infraccionDto = new InfraccionDto();
        infraccionDto.setFechaInfraccion(new Date());
        infraccionDto.setInspeccionDto(InspeccionMapper.toInspeccionDto(inspeccion));
        infraccionDto.setTipoInfraccionDto(TipoInfraccionMapper.toTipoInfraccionDto(tipo.get()));
        infraccionDto.setMontoTotal(tipo.get().getValorUFV());
        infraccionDto.setStatusInfraccion(StatusInfraccion.PENDIENTE.name());
        infraccionDto.setEstadoPago(false);
        infraccionDto.setEstado(true);
        return infraccionDto;
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

}
