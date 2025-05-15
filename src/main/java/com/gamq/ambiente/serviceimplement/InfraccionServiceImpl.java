package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.InfraccionDto;
import com.gamq.ambiente.dto.mapper.InfraccionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.DetalleInspeccion;
import com.gamq.ambiente.model.Infraccion;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.TipoInfraccion;
import com.gamq.ambiente.repository.DetalleInspeccionRepository;
import com.gamq.ambiente.repository.InfraccionRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.TipoInfraccionRepository;
import com.gamq.ambiente.service.InfraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    public InfraccionDto crearInfraccion(InfraccionDto infraccionDto) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(infraccionDto.getInspeccionDto().getUuid());
        if (inspeccionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Inspeccion","uuid", infraccionDto.getInspeccionDto().getUuid());
        }
        Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findByUuid(infraccionDto.getTipoInfraccionDto().getUuid());
        if (tipoInfraccionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Tipo Infraccion","uuid", infraccionDto.getTipoInfraccionDto().getUuid());
        }
        Infraccion nuevoInfraccion = InfraccionMapper.toInfraccion(infraccionDto);
        nuevoInfraccion.setInspeccion(inspeccionOptional.get());
        nuevoInfraccion.setTipoInfraccion(tipoInfraccionOptional.get());
        return InfraccionMapper.toInfraccionDto(infraccionRepository.save(nuevoInfraccion));
    }

    @Override
    public InfraccionDto actualizarInfraccion(InfraccionDto infraccionDto) {
        Optional<Infraccion> infraccionOptional = infraccionRepository.findByUuid(infraccionDto.getUuid());
        if(infraccionOptional.isPresent()) {
            Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findByUuid(infraccionDto.getInspeccionDto().getUuid());
            if (inspeccionOptional.isEmpty()) {
                throw new ResourceNotFoundException("Inspeccion","uuid", infraccionDto.getInspeccionDto().getUuid());
            }
            Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findByUuid(infraccionDto.getTipoInfraccionDto().getUuid());
            if (tipoInfraccionOptional.isEmpty()) {
                throw new ResourceNotFoundException("Tipo Infraccion","uuid", infraccionDto.getTipoInfraccionDto().getUuid());
            }
            Infraccion updateInfraccion = InfraccionMapper.toInfraccion(infraccionDto);
            updateInfraccion.setIdInfraccion(infraccionOptional.get().getIdInfraccion());
            updateInfraccion.setTipoInfraccion(tipoInfraccionOptional.get());
            updateInfraccion.setInspeccion(inspeccionOptional.get());
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

    public boolean evaluarResultadoInspeccion(String uuidInspeccion) {
        List<DetalleInspeccion> detalles = detalleInspeccionRepository
                .findByInspeccionUuidOrderByNroEjecucionAsc(uuidInspeccion);

        if (detalles.isEmpty()) {
            throw new RuntimeException("No hay mediciones para esta inspección");
        }

        int medicionesMalas = 0;
        for (DetalleInspeccion detalle : detalles) {
            if (detalle.getValor().compareTo(new BigDecimal("5.0")) > 0) { //Constantes.LIMITE_PERMITIDO
                medicionesMalas++;
            }
        }

        boolean fallo = medicionesMalas > 0; // puedes ajustar esta regla si deseas "2 o más fallas", etc.
        return fallo;
    }
}
