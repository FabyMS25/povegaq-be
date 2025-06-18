package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.dto.InspeccionDetalleInspeccionDto;
import com.gamq.ambiente.dto.mapper.DetalleInspeccionMapper;
import com.gamq.ambiente.dto.mapper.InspeccionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.DetalleInspeccion;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.TipoParametro;
import com.gamq.ambiente.repository.DetalleInspeccionRepository;
import com.gamq.ambiente.repository.InspeccionRepository;
import com.gamq.ambiente.repository.TipoParametroRepository;
import com.gamq.ambiente.service.DetalleInspeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DetalleInspeccionServiceImpl implements DetalleInspeccionService {
    @Autowired
    DetalleInspeccionRepository detalleInspeccionRepository;
    @Autowired
    InspeccionRepository inspeccionRepository;
    @Autowired
    TipoParametroRepository tipoParametroRepository;
    @Autowired
    private EntidadHelperServiceImpl entidadHelper;

    @Override
    public DetalleInspeccionDto obtenerDetalleInspeccionPorUuid(String uuid) {
        DetalleInspeccion detalleInspeccion = obtenerDetalleInspeccionPorUuidOThrow(uuid);
        return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccion);
    }

    @Override
    public List<DetalleInspeccionDto> obtenerDetalleInspecciones() {
        List<DetalleInspeccion> detalleInspeccionList = detalleInspeccionRepository.findAll();
        return  detalleInspeccionList.stream().map( detalleInspeccion -> {
            return  DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccion);
        }).collect(Collectors.toList());
    }

    @Override
    public DetalleInspeccionDto crearDetalleInspeccion(DetalleInspeccionDto detalleInspeccionDto) {
           Inspeccion inspeccion = inspeccionRepository.findByUuid(detalleInspeccionDto.getInspeccionDto().getUuid())
                   .orElseThrow(()-> new ResourceNotFoundException("Inspeccion", "uuid", detalleInspeccionDto.getInspeccionDto().getUuid()));
           TipoParametro tipoParametro = tipoParametroRepository.findByUuid(detalleInspeccionDto.getTipoParametroDto().getUuid())
                   .orElseThrow(()-> new ResourceNotFoundException("Tipo Parametro", "uuid", detalleInspeccionDto.getTipoParametroDto().getUuid()));

           if(detalleInspeccionRepository.exitsDetalleInspeccionByUuidTipoParametroAndUuidInspeccionAndNroEjecucionAndModoCombustion(tipoParametro.getUuid(),inspeccion.getUuid(), detalleInspeccionDto.getNroEjecucion(), detalleInspeccionDto.getModoCombustion())) {
               throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el tipo de parametro de la inspecion ya existe");
           }

           DetalleInspeccion nuevoDetalleInspeccion = DetalleInspeccionMapper.toDetalleInspeccion(detalleInspeccionDto);
           nuevoDetalleInspeccion.setTipoParametro(tipoParametro);
           nuevoDetalleInspeccion.setInspeccion( inspeccion);
           return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccionRepository.save(nuevoDetalleInspeccion));
    }

    @Override
    public DetalleInspeccionDto actualizarDetalleInspeccion(DetalleInspeccionDto detalleInspeccionDto) {
        DetalleInspeccion detalleInspeccion = obtenerDetalleInspeccionPorUuidOThrow(detalleInspeccionDto.getUuid());

        Inspeccion inspeccion = inspeccionRepository.findByUuid(detalleInspeccionDto.getInspeccionDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Inspeccion", "uuid", detalleInspeccionDto.getInspeccionDto().getUuid()));

        TipoParametro tipoParametro = tipoParametroRepository.findByUuid(detalleInspeccionDto.getTipoParametroDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Tipo Parametro", "uuid", detalleInspeccionDto.getTipoParametroDto().getUuid()));

         if (detalleInspeccionRepository.exitsDetalleInspeccionLike(detalleInspeccionDto.getTipoParametroDto().getUuid(),
                                                                       detalleInspeccionDto.getNroEjecucion(),
                                                                       detalleInspeccionDto.getInspeccionDto().getUuid(),
                                                                       detalleInspeccionDto.getUuid())) {
             throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Detalle Inspeccion ya existe");
        }

        DetalleInspeccion updateDetalleInspeccion = DetalleInspeccionMapper.toDetalleInspeccion(detalleInspeccionDto);
        updateDetalleInspeccion.setIdDetalleInspeccion(detalleInspeccion.getIdDetalleInspeccion());
        updateDetalleInspeccion.setTipoParametro(tipoParametro);
        updateDetalleInspeccion.setInspeccion(inspeccion);
        return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccionRepository.save(updateDetalleInspeccion));
    }

    @Override
    public DetalleInspeccionDto eliminarDetalleInspeccion(String uuid) {
        DetalleInspeccion detalleInspeccion = obtenerDetalleInspeccionPorUuidOThrow(uuid);
            detalleInspeccionRepository.delete(detalleInspeccion);
        return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccion);
    }

    public void agregarEjecucion(String uuidInspeccion, List<DetalleInspeccion> nuevosDetalles) {
        Inspeccion inspeccion =  inspeccionRepository.findByUuid(uuidInspeccion)
                .orElseThrow(() -> new RuntimeException("Inspección no encontrada"));

        Integer ultimaEjecucion = detalleInspeccionRepository.findUltimaEjecucionByUuidInspeccion(uuidInspeccion);
        int nuevaEjecucion = (ultimaEjecucion == null) ? 1 : ultimaEjecucion + 1;

       // if (nuevaEjecucion > 5) {
       //     throw new RuntimeException("No se pueden registrar más de 5 ejecuciones.");
       // }

        for (DetalleInspeccion detalle : nuevosDetalles) {
            detalle.setNroEjecucion(nuevaEjecucion);
            detalle.setInspeccion(inspeccion);
        }
        detalleInspeccionRepository.saveAll(nuevosDetalles);
        //detalleRepo.saveAll(nuevosDetalles);
    }

    public List<DetalleInspeccionDto> obtenerDetalleInpeccionUltimaEjecucion(String uuidInspeccion) {
        Integer nro = detalleInspeccionRepository.findUltimaEjecucionByUuidInspeccion(uuidInspeccion);
        if (nro == null) return Collections.emptyList();
       List<DetalleInspeccion> detalleInspeccionList = detalleInspeccionRepository.findByUuidInspeccionAndNroEjecucion(uuidInspeccion, nro);
       return detalleInspeccionList.stream().map( detalleInspeccion -> {
           return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccion);
       }).collect(Collectors.toList());
    }

    public List<DetalleInspeccionDto> obtenerDetalleInspeccionPorNroEjecucion(String uuidInspeccion, Integer nroEjecucion) {
        List<DetalleInspeccion> detalleInspeccionList = detalleInspeccionRepository.findByUuidInspeccionAndNroEjecucion(uuidInspeccion, nroEjecucion);
        return detalleInspeccionList.stream().map( detalleInspeccion -> {
            return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccion);
        }).collect(Collectors.toList());
   }

    public List<DetalleInspeccionDto> addDetalleInspecionToInspeccion(InspeccionDetalleInspeccionDto inspeccionDetalleInspeccionDto) {
        Inspeccion inspeccion = entidadHelper.obtenerInspeccion(inspeccionDetalleInspeccionDto.getUuidInspeccion());
        Integer ultimaEjecucion = detalleInspeccionRepository.findUltimaEjecucionByUuidInspeccion(inspeccionDetalleInspeccionDto.getUuidInspeccion());
        int nuevaEjecucion = (ultimaEjecucion == null) ? 1 : ultimaEjecucion + 1;
        List<DetalleInspeccion>  detalleInspeccionList = inspeccionDetalleInspeccionDto.getDetalleInspeccionDtoList().stream().map(detalleInspeccionDto ->  {
        TipoParametro tipoParametro = entidadHelper.obtenerTipoParametro(detalleInspeccionDto.getTipoParametroDto().getUuid());

        if(!detalleInspeccionRepository.exitsDetalleInspeccionByUuidTipoParametroAndUuidInspeccionAndNroEjecucionAndModoCombustion(tipoParametro.getUuid(),inspeccion.getUuid(),nuevaEjecucion, detalleInspeccionDto.getModoCombustion())) {
            detalleInspeccionDto.setNroEjecucion(nuevaEjecucion);
            DetalleInspeccion nuevoDetalleInspeccion = crearNuevoDetalleInspeccion(detalleInspeccionDto, inspeccion, tipoParametro);
            return nuevoDetalleInspeccion;
        }
        else {return null;}
        }).filter(Objects::nonNull)
        .collect(Collectors.toList());

        inspeccion.setDetalleInspeccionList(detalleInspeccionList);

        return InspeccionMapper.toInspeccionDto(inspeccionRepository.save(inspeccion)).getDetalleInspeccionDtoList();
    }

    @Override
    public List<DetalleInspeccionDto> obtenerDetalleInspeccionPorUuidInspeccion(String uuidInspeccion) {
        List<DetalleInspeccion> detalleInspeccionList = detalleInspeccionRepository.findByUuidInspeccion(uuidInspeccion);
        return detalleInspeccionList.stream().map(detalleInspeccion -> {
            return DetalleInspeccionMapper.toDetalleInspeccionDto(detalleInspeccion);
        }).collect(Collectors.toList());
    }

    private DetalleInspeccion crearNuevoDetalleInspeccion(DetalleInspeccionDto  detalleInspeccionDto, Inspeccion inspeccion, TipoParametro tipoParametro) {
        DetalleInspeccion nuevoDetalleInspeccion = DetalleInspeccionMapper.toDetalleInspeccion(detalleInspeccionDto);
        nuevoDetalleInspeccion.setInspeccion( inspeccion);
        nuevoDetalleInspeccion.setTipoParametro(tipoParametro);
        return nuevoDetalleInspeccion;
    }

    private DetalleInspeccion obtenerDetalleInspeccionPorUuidOThrow(String uuid){
        return detalleInspeccionRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Detalle Inspeccion", "uuid", uuid));
    }
}
