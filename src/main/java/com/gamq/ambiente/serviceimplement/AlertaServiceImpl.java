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
        Alerta alerta = obtenerAlertaPorUuidOThrow(uuid);
        return AlertaMapper.toAlertaDto(alerta);
    }

    @Override
    public List<AlertaDto> obtenerAlertas() {
        List<Alerta> alertaList = alertaRepository.findAll();
        return alertaList.stream().map( alerta -> {
            return AlertaMapper.toAlertaDto(alerta);
        }).collect(Collectors.toList());
    }

    @Override
    public AlertaDto crearAlerta(AlertaDto alertaDto){
        Vehiculo vehiculo = obtenerVehiculo(alertaDto.getVehiculoDto().getUuid());
        Alerta nuevoAlerta = AlertaMapper.toAlerta(alertaDto);
        nuevoAlerta.setVehiculo(vehiculo);
        return AlertaMapper.toAlertaDto(alertaRepository.save(nuevoAlerta));
    }

    @Override
    public AlertaDto actualizarAlerta(AlertaDto alertaDto) {
        Alerta alerta = obtenerAlertaPorUuidOThrow(alertaDto.getUuid());
        Vehiculo vehiculo = obtenerVehiculo(alertaDto.getVehiculoDto().getUuid());
        Alerta updateAlerta = AlertaMapper.toAlerta(alertaDto);
        updateAlerta.setIdAlerta(alerta.getIdAlerta());
        updateAlerta.setVehiculo(vehiculo);
        return AlertaMapper.toAlertaDto(alertaRepository.save(updateAlerta));
    }

    @Override
    public AlertaDto eliminarAlerta(String uuid) {
        Alerta alerta = obtenerAlertaPorUuidOThrow(uuid);
        alertaRepository.delete(alerta);
        return  AlertaMapper.toAlertaDto(alerta);
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

    private Alerta obtenerAlertaPorUuidOThrow(String uuid){
        return alertaRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta", "uuid", uuid));
    }

    private Vehiculo obtenerVehiculo(String uuidVehiculo){
        return vehiculoRepository.findByUuid(uuidVehiculo)
                .orElseThrow(()->new ResourceNotFoundException("Vehiculo", "uuid", uuidVehiculo));
    }
}
