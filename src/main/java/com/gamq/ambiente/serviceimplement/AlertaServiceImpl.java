package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.AlertaDto;
import com.gamq.ambiente.dto.mapper.AlertaMapper;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Alerta;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.AlertaRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AlertaServiceImpl implements AlertaService {
    @Autowired
    AlertaRepository alertaRepository;
    @Autowired
    VehiculoRepository vehiculoRepository;

    @Override
    public AlertaDto obtenerAlertaPorUuid(String uuid) {
        Optional<Alerta> alertaOptional = alertaRepository.findByUuid(uuid);
        if(alertaOptional.isPresent()){
            return AlertaMapper.toAlertaDto(alertaOptional.get());
        }
        throw  new ResourceNotFoundException("alerta", "uuid", uuid);
    }

    @Override
    public List<AlertaDto> obtenerAlertas() {
        List<Alerta> alertaList = alertaRepository.findAll();
        return alertaList.stream().map( alerta -> {
            return AlertaMapper.toAlertaDto(alerta);
        }).collect(Collectors.toList());
    }

    @Override
    public AlertaDto crearAlerta(AlertaDto alertaDto) {
        Vehiculo vehiculo = obtenerVehiculo(alertaDto.getVehiculoDto().getUuid());
        Alerta nuevoAlerta = AlertaMapper.toAlerta(alertaDto);
        nuevoAlerta.setVehiculo( vehiculo);
        return AlertaMapper.toAlertaDto(alertaRepository.save(nuevoAlerta));
    }

    private Vehiculo obtenerVehiculo(String uuidVehiculo){
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(uuidVehiculo);
        if(vehiculoOptional.isEmpty()){
            throw  new ResourceNotFoundException("vehiculo", "uuid", uuidVehiculo);
        }
        return vehiculoOptional.get();
    }

    @Override
    public AlertaDto actualizarAlerta(AlertaDto alertaDto) {
        Alerta alertaQBE = new Alerta(alertaDto.getUuid());
        Optional<Alerta> alertaOptional = alertaRepository.findByUuid(alertaQBE.getUuid());
        if(alertaOptional.isPresent())
        {
            Vehiculo vehiculo = obtenerVehiculo(alertaDto.getVehiculoDto().getUuid());
            Alerta updateAlerta = AlertaMapper.toAlerta(alertaDto);
            updateAlerta.setIdAlerta(alertaOptional.get().getIdAlerta());
            updateAlerta.setVehiculo(vehiculo);
            return AlertaMapper.toAlertaDto(alertaRepository.save(updateAlerta));
        }
        throw  new ResourceNotFoundException("alerta", "uuid", alertaDto.getUuid());
    }

    @Override
    public AlertaDto eliminarAlerta(String uuid) {
        Alerta alertaQBE = new Alerta(uuid);
        Optional<Alerta> alertaOptional = alertaRepository.findByUuid(alertaQBE.getUuid());
        if(alertaOptional.isPresent()) {
            Alerta alerta = alertaOptional.get();
            alertaRepository.delete(alerta);
            return  AlertaMapper.toAlertaDto(alerta);
        }
        throw  new ResourceNotFoundException("alerta", "uuid", uuid);
    }

    @Override
    public List<AlertaDto> obtenerAlertasPorFechaActual(Date fechaActual) {
        List<Alerta> alertaList = alertaRepository.findByFechaAlerta(fechaActual);
        return alertaList.stream().map( alerta -> {
            return AlertaMapper.toAlertaDto(alerta);
        }).collect(Collectors.toList());
    }

    @Override
    public List<AlertaDto> obtenerAlertasPorFechaActualAndUuidDestinatario(Date fechaActual, String uuidDestinatario) {
        List<Alerta> alertaList = alertaRepository.findByFechaAlertaAndUuidDestinatario(fechaActual,uuidDestinatario);
        return alertaList.stream().map( alerta -> {
            return AlertaMapper.toAlertaDto(alerta);
        }).collect(Collectors.toList());
    }
}
