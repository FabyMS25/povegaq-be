package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.LimiteEmisionDto;
import com.gamq.ambiente.dto.mapper.LimiteEmisionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.DatoTecnico;
import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.LimiteEmision;
import com.gamq.ambiente.model.TipoParametro;
import com.gamq.ambiente.repository.LimiteEmisionRepository;
import com.gamq.ambiente.repository.TipoParametroRepository;
import com.gamq.ambiente.service.LimiteEmisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LimiteEmisionServiceImpl implements LimiteEmisionService {
    @Autowired
    LimiteEmisionRepository limiteEmisionRepository;
    @Autowired
    TipoParametroRepository tipoParametroRepository;

    @Override
    public LimiteEmisionDto obtenerLimiteEmisionPorUuid(String uuid) {
        Optional<LimiteEmision> limiteEmisionOptional = limiteEmisionRepository.findByUuid(uuid);
        if(limiteEmisionOptional.isPresent()){
            return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmisionOptional.get());
        }
        throw new ResourceNotFoundException("Limite Emision", "uuid", uuid);
    }

    @Override
    public List<LimiteEmisionDto> obtenerLimiteEmisionPorUuidTipoParametro(String uuidTipoParametro) {
        List<LimiteEmision> limiteEmisionList = limiteEmisionRepository.findByUuidTipoParametro(uuidTipoParametro);
        return limiteEmisionList.stream().map(limiteEmision -> {
            return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
        }).collect(Collectors.toList());
    }

    @Override
    public List<LimiteEmisionDto> obtenerLimiteEmisiones() {
        List<LimiteEmision> limiteEmisionList = limiteEmisionRepository.findAll();
        return  limiteEmisionList.stream().map( limiteEmision -> {
            return  LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
        }).collect(Collectors.toList());
    }

    @Override
    public LimiteEmisionDto crearLimiteEmision(LimiteEmisionDto limiteEmisionDto) {
        String tipoCombustible = limiteEmisionDto.getTipoCombustible();
        if (tipoCombustible == null) { throw new ResourceNotFoundException("Tipo Combustible", "tipoCombustible", tipoCombustible);}

        Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByUuid(limiteEmisionDto.getTipoParametroDto().getUuid());
        if (tipoParametroOptional.isPresent()) {
            String nombre = tipoParametroOptional.get().getNombre();
            if (nombre == null) {
                throw new ResourceNotFoundException("Limite Emision", "nombre", nombre);
            }
          //  if(!limiteEmisionRepository.exitsLimiteEmisionLikeTipoConbustibleAndNombreTipoParametro(limiteEmisionDto.getTipoCombustible(), tipoParametroOptional.get().getNombre()))
           // {
            LimiteEmision nuevoLimiteEmision = LimiteEmisionMapper.toLimiteEmision(limiteEmisionDto);
            nuevoLimiteEmision.setTipoParametro(tipoParametroOptional.get());
            return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmisionRepository.save(nuevoLimiteEmision));
          //  } else {
          //      throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "ya existe el limite de emision para el tipo de combustible  " + limiteEmisionDto.getTipoCombustible() + " y el parametro " + tipoParametroOptional.get().getNombre());
          //  }
        }
        else {
            throw new ResourceNotFoundException("tipo parametro", "uuid", limiteEmisionDto.getTipoParametroDto().getUuid());
        }
    }

    @Override
    public LimiteEmisionDto actualizarLimiteEmision(LimiteEmisionDto limiteEmisionDto) {
        Optional<LimiteEmision> limiteEmisionOptional = limiteEmisionRepository.findByUuid(limiteEmisionDto.getUuid());
        if(limiteEmisionOptional.isPresent()) {
            Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByUuid(limiteEmisionDto.getTipoParametroDto().getUuid());
            if (tipoParametroOptional.isPresent()) {
                //  if (!limiteEmisionRepository.exitsLimiteEmisionLikeNombreTipoParametro(limiteEmisionDto.getTipoCombustible(), limiteEmisionDto.getTipoParametroDto().getNombre().toLowerCase(), limiteEmisionDto.getUuid())) {
                LimiteEmision updateLimiteEmision = LimiteEmisionMapper.toLimiteEmision(limiteEmisionDto);
                updateLimiteEmision.setIdLimiteEmision(limiteEmisionOptional.get().getIdLimiteEmision());
                updateLimiteEmision.setTipoParametro(tipoParametroOptional.get());
                return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmisionRepository.save(updateLimiteEmision));
                // } else {
                //     throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Limite Emision ya existe");
                // }
            }
            else {
                throw new ResourceNotFoundException("tipo parametro", "uuid", limiteEmisionDto.getTipoParametroDto().getUuid());
            }
        }
        throw new ResourceNotFoundException("Limite Emision", "uuid",limiteEmisionDto.getUuid());
    }

    @Override
    public LimiteEmisionDto eliminarLimiteEmision(String uuid) {
        LimiteEmision limiteEmisionQBE = new LimiteEmision(uuid);
        Optional<LimiteEmision> optionalLimiteEmision = limiteEmisionRepository.findByUuid(uuid);
        if(optionalLimiteEmision.isPresent()){
            if (optionalLimiteEmision.get().getTipoParametro().getDetalleInspeccionList().size()>0) {
                LimiteEmision limiteEmision = optionalLimiteEmision.get();
                limiteEmisionRepository.delete(limiteEmision);
                return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
            }
            else {
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "ya tiene detalles de inspeccion");
            }
        }
        throw new ResourceNotFoundException("Limite Emision","uuid", uuid);
    }

    @Override
    public LimiteEmisionDto actualizarLimiteEmisionActivo(String uuid, boolean nuevoActivo) {
        LimiteEmision limiteEmision = limiteEmisionRepository.findByUuid(uuid)
                .orElseThrow(()-> new RuntimeException("Limite de emision no encontrado"));
        limiteEmision.setActivo(nuevoActivo);
        //Si se desactiva, se marca la fecha de fin
        if (!nuevoActivo){
            limiteEmision.setFechaFin(new Date());
        }
        else {
            limiteEmision.setFechaInicio(new Date()); // Opcional: actualiza inicio
        }
        limiteEmisionRepository.save(limiteEmision);
        return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
    }

    @Override
    public List<LimiteEmisionDto> buscarLimitesPorFiltro(TipoParametro tipoParametro, DatoTecnico datoTecnico, Integer altitud) {
        try {
        List<LimiteEmision> limiteEmisionList = limiteEmisionRepository.findAll();
        List<LimiteEmision> limiteEmisionFitrados = limiteEmisionList.stream()
                .filter(l -> l.getTipoParametro() != null && l.getTipoParametro().getUuid().equals(tipoParametro.getUuid()))
                .filter(l -> l.getTipoMotor() != null && l.getTipoMotor().equalsIgnoreCase(datoTecnico.getTipoMotor()))
                .filter(l -> l.getTipoCombustible() != null && l.getTipoCombustible().equalsIgnoreCase(datoTecnico.getTipoCombustion()))
                .filter(l-> l.getClaseVehiculo() != null && l.getClaseVehiculo().equalsIgnoreCase(datoTecnico.getClase()))
                // si es null deja pasar
                .filter(l -> l.getCategoriaVehiculo() == null
                        || l.getCategoriaVehiculo().equalsIgnoreCase(datoTecnico.getCategoriaVehiculo()))

               // .filter(l ->l.getCategoriaVehiculo() != null &&  l.getCategoriaVehiculo().equalsIgnoreCase(datoTecnico.getCategoriaVehiculo()))
                .filter(l -> matchBetweenInteger(l.getYearFabricacionInicio(), l.getYearFabricacionFin(), datoTecnico.getYearFabricacion()))
                .filter(l -> matchBetweenInteger(l.getAltitudMinima(), l.getAltitudMaxima(), altitud) )
                .filter(l -> matchBetweenBigDecimal(l.getCilindradaMinimo(), l.getCilindradaMaximo(), datoTecnico.getCilindrada()))
                .filter(l -> matchBetweenBigDecimal(l.getPesoBrutoMinimo(), l.getPesoBrutoMaximo(), datoTecnico.getCapacidadCarga()))
                .filter(l -> matchEqualsIgnoreCase(l.getTiempoMotor(), datoTecnico.getTiempoMotor()))
                .filter(LimiteEmision::isActivo)
                //.filter(LimiteEmision::isEstado)
                .collect(Collectors.toList());
        return limiteEmisionFitrados.stream().map(limiteEmision -> {
            return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
        }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace(); // solo para depuración, luego eliminar
            throw new RuntimeException("Error al buscar límites de emisión: " + e.getMessage(), e);
        }
    }

    private boolean matchBetweenBigDecimal(Integer min, Integer max, BigDecimal value) {

        // Si el valor medido es nulo, se considera válido solo si también no hay rangos definidos
        if (value == null) {
            return min == null && max == null;
        }
        //if (min == null && max == null) return true;
        int val = value.intValue();
        return (min == null || val >= min) && (max == null || val <= max);
    }

    private boolean matchBetweenInteger(Integer min, Integer max, Integer value) {
        // Si el valor medido es nulo, se considera válido solo si también no hay rangos definidos
        if (value == null) {
            return min == null && max == null;
        }
        //if (value == null) return true;
        return (min == null || value >= min) && (max == null || value <= max);
    }

    private boolean matchEqualsIgnoreCase(String a, String b) {
        if (a == null || b == null) return true; // Si alguno es nulo, se considera que no filtra
        return a.trim().equalsIgnoreCase(b.trim());
    }

}
