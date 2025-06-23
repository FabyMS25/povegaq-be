package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.VehiculoTipoCombustibleDto;
import com.gamq.ambiente.dto.mapper.VehiculoTipoCombustibleMapper;
import com.gamq.ambiente.model.TipoCombustible;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.model.VehiculoTipoCombustible;
import com.gamq.ambiente.repository.TipoCombustibleRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.repository.VehiculoTipoCombustibleRepository;
import com.gamq.ambiente.service.VehiculoTipoCombustibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehiculoTipoCombustibleServiceImpl implements VehiculoTipoCombustibleService {
    @Autowired
    VehiculoTipoCombustibleRepository vehiculoTipoCombustibleRepository;

    @Autowired
    private TipoCombustibleRepository tipoCombustibleRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Transactional
    @Override
    public void asignarCombustibles(Vehiculo vehiculo, List<VehiculoTipoCombustibleDto> vehiculoTipoCombustibleDtoList) {
        vehiculo.getVehiculoTipoCombustibleList().clear();

        for (VehiculoTipoCombustibleDto dto : vehiculoTipoCombustibleDtoList) {
            TipoCombustible tipo = tipoCombustibleRepository.findByUuid(dto.getUuid())
                    .orElseThrow(() -> new RuntimeException("Tipo combustible no encontrado"));

            VehiculoTipoCombustible vc = new VehiculoTipoCombustible();
            vc.setVehiculo(vehiculo);
            vc.setTipoCombustible(tipo);
            vc.setEsPrimario(dto.getEsPrimario());

            vehiculo.getVehiculoTipoCombustibleList().add(vc);
        }
    }

    @Override
    public void eliminarTodosPorVehiculo(String uuidVehiculo) {
        Vehiculo vehiculo = vehiculoRepository.findByUuid(uuidVehiculo)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        vehiculo.getVehiculoTipoCombustibleList().clear();
        vehiculoRepository.save(vehiculo);
    }

    @Override
    public List<VehiculoTipoCombustibleDto> obtenerPorVehiculo(String uuidVehiculo) {
        Vehiculo vehiculo = vehiculoRepository.findByUuid(uuidVehiculo)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        return vehiculo.getVehiculoTipoCombustibleList().stream()
                .map( vehiculoTipoCombustible ->{  return  VehiculoTipoCombustibleMapper.toVehiculoTipoCombustibleDto(vehiculoTipoCombustible);}
                ).collect(Collectors.toList());
    }
}
