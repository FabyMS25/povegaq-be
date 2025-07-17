package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.LimiteEmisionDto;
import com.gamq.ambiente.dto.mapper.LimiteEmisionMapper;
//import com.gamq.ambiente.enumeration.TipoCombustible;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.*;
import com.gamq.ambiente.repository.ClaseVehiculoRepository;
import com.gamq.ambiente.repository.LimiteEmisionRepository;
import com.gamq.ambiente.repository.TipoCombustibleRepository;
import com.gamq.ambiente.repository.TipoParametroRepository;
import com.gamq.ambiente.service.LimiteEmisionService;
//import com.gamq.ambiente.utils.TipoCombustionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @Autowired
    TipoCombustibleRepository tipoCombustibleRepository;
    @Autowired
    ClaseVehiculoRepository claseVehiculoRepository;

    @Override
    public LimiteEmisionDto obtenerLimiteEmisionPorUuid(String uuid) {
        LimiteEmision limiteEmision = obtenerLimiteEmisionPorUuidOThrow(uuid);
        return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
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
        TipoCombustible tipoCombustible = obtenerTipoCombustiblePorUuidOThrow( limiteEmisionDto.getTipoCombustibleDto().getUuid());
        TipoParametro tipoParametro = obtenerTipoParametroPorUuidOThrow( limiteEmisionDto.getTipoParametroDto().getUuid());
        String nombre = tipoParametro.getNombre();
        if (nombre == null) {
            throw new ResourceNotFoundException("Limite Emision", "nombre", nombre);
        }
        LimiteEmision nuevoLimiteEmision = LimiteEmisionMapper.toLimiteEmision(limiteEmisionDto);
        nuevoLimiteEmision.setTipoParametro(tipoParametro);
        nuevoLimiteEmision.setTipoCombustible(tipoCombustible);

        if (limiteEmisionDto.getClaseVehiculoDto() != null &&
                limiteEmisionDto.getClaseVehiculoDto().getUuid() != null) {
            Optional<ClaseVehiculo> claseVehiculoOptional = claseVehiculoRepository.findByUuid(limiteEmisionDto.getClaseVehiculoDto().getUuid());
            if (claseVehiculoOptional.isPresent()) {
                nuevoLimiteEmision.setClaseVehiculo(claseVehiculoOptional.get());
            } else {
                throw new ResourceNotFoundException("Clase vehiculo", "uuid", limiteEmisionDto.getClaseVehiculoDto().getUuid());
            }
        }

        return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmisionRepository.save(nuevoLimiteEmision));
    }

    @Override
    public LimiteEmisionDto actualizarLimiteEmision(LimiteEmisionDto limiteEmisionDto) {
        LimiteEmision limiteEmision = obtenerLimiteEmisionPorUuidOThrow(limiteEmisionDto.getUuid());
        TipoCombustible tipoCombustible = obtenerTipoCombustiblePorUuidOThrow( limiteEmisionDto.getTipoCombustibleDto().getUuid());
        TipoParametro tipoParametro = obtenerTipoParametroPorUuidOThrow(limiteEmisionDto.getTipoParametroDto().getUuid());
        LimiteEmision updateLimiteEmision = LimiteEmisionMapper.toLimiteEmision(limiteEmisionDto);
        updateLimiteEmision.setIdLimiteEmision(limiteEmision.getIdLimiteEmision());
        updateLimiteEmision.setTipoCombustible(tipoCombustible);
        updateLimiteEmision.setTipoParametro(tipoParametro);

        if (limiteEmisionDto.getClaseVehiculoDto() != null &&
            limiteEmisionDto.getClaseVehiculoDto().getUuid() != null ){
            Optional<ClaseVehiculo> claseVehiculoOptional1 = claseVehiculoRepository.findByUuid(limiteEmisionDto.getClaseVehiculoDto().getUuid());
            if(claseVehiculoOptional1.isPresent()){
                updateLimiteEmision.setClaseVehiculo(claseVehiculoOptional1.get());
            }
            else {
                throw  new ResourceNotFoundException("Clase vehiculo", "uuid", limiteEmisionDto.getClaseVehiculoDto().getUuid());
            }
        }

        return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmisionRepository.save(updateLimiteEmision));
    }

    @Override
    public LimiteEmisionDto eliminarLimiteEmision(String uuid) {
        LimiteEmision limiteEmision = obtenerLimiteEmisionPorUuidOThrow(uuid);
        if (limiteEmision.getTipoParametro().getDetalleInspeccionList().size()> 0){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "ya tiene detalles de inspeccion");
        }
        limiteEmisionRepository.delete(limiteEmision);
        return LimiteEmisionMapper.toLimiteEmisionDto(limiteEmision);
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
    public List<LimiteEmisionDto> buscarLimitesPorFiltro(TipoParametro tipoParametro, TipoCombustible tipoCombustible, DatoTecnico datoTecnico, Integer altitud) {
        try {
            List<LimiteEmision> limiteEmisionList = limiteEmisionRepository.findAll();

      /*      //prueba paso a paso
           List<LimiteEmision> resultado = new ArrayList<>(limiteEmisionList);
            System.out.println("Total inicial: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> l.getTipoParametro() != null && l.getTipoParametro().getUuid().equals(tipoParametro.getUuid()))
                    .collect(Collectors.toList());
            System.out.println("Después de tipoParametro: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> l.getTipoCombustible() != null && l.getTipoCombustible().getUuid().equals(tipoCombustible.getUuid()))
                    .collect(Collectors.toList());
            System.out.println("Después de tipoCombustible: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> l.getTipoMotor() == null || l.getTipoMotor().equalsIgnoreCase(datoTecnico.getTipoMotor()))
                    .collect(Collectors.toList());
            System.out.println("Después de tipoMotor: " + resultado.size());



            resultado = resultado.stream()
                    .filter(l -> l.getClaseVehiculo() == null || l.getClaseVehiculo().getUuid().equals(datoTecnico.getTipoClaseVehiculo().getClaseVehiculo().getUuid()))
                    .collect(Collectors.toList());
            System.out.println("Después de claseVehiculo: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> l.getCategoriaVehiculo() == null || l.getCategoriaVehiculo().equalsIgnoreCase(datoTecnico.getCategoriaVehiculo()))
                    .collect(Collectors.toList());
            System.out.println("Después de categoriaVehiculo: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> matchBetweenInteger(l.getYearFabricacionInicio(), l.getYearFabricacionFin(), datoTecnico.getYearFabricacion()))
                    .collect(Collectors.toList());
            System.out.println("Después de yearFabricacion: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> matchBetweenInteger(l.getAltitudMinima(), l.getAltitudMaxima(), altitud))
                    .collect(Collectors.toList());
            System.out.println("Después de altitud: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> matchBetweenBigDecimal(l.getCilindradaMinimo(), l.getCilindradaMaximo(), datoTecnico.getCilindrada()))
                    .collect(Collectors.toList());
            System.out.println("Después de cilindrada: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> matchBetweenBigDecimal(l.getPesoBrutoMinimo(), l.getPesoBrutoMaximo(), datoTecnico.getCapacidadCarga()))
                    .collect(Collectors.toList());
            System.out.println("Después de pesoBruto: " + resultado.size());

            resultado = resultado.stream()
                    .filter(l -> matchEqualsIgnoreCase(l.getTiempoMotor(), datoTecnico.getTiempoMotor()))
                    .collect(Collectors.toList());
            System.out.println("Después de tiempoMotor: " + resultado.size());

            resultado = resultado.stream()
                    .filter(LimiteEmision::isActivo)
                    .collect(Collectors.toList());
            System.out.println("Después de isActivo: " + resultado.size());
*/

        List<LimiteEmision> limiteEmisionFitrados = limiteEmisionList.stream()
                .filter(l -> l.getTipoParametro() != null && l.getTipoParametro().getUuid().equals(tipoParametro.getUuid()))
                .filter(l -> l.getTipoMotor() == null || l.getTipoMotor().equalsIgnoreCase(datoTecnico.getTipoMotor()))
                .filter(l -> l.getTipoCombustible() != null && l.getTipoCombustible().getUuid().equals(tipoCombustible.getUuid()))
                .filter(l-> l.getClaseVehiculo() == null || l.getClaseVehiculo().getUuid().equals(datoTecnico.getTipoClaseVehiculo().getClaseVehiculo().getUuid()))
                .filter(l -> l.getCategoriaVehiculo() == null
                        || l.getCategoriaVehiculo().equalsIgnoreCase(datoTecnico.getCategoriaVehiculo()))
                .filter(l -> matchBetweenInteger(l.getYearFabricacionInicio(), l.getYearFabricacionFin(), datoTecnico.getYearFabricacion()))
                .filter(l -> matchBetweenInteger(l.getAltitudMinima(), l.getAltitudMaxima(), altitud) )
                .filter(l -> matchBetweenBigDecimal(l.getCilindradaMinimo(), l.getCilindradaMaximo(), datoTecnico.getCilindrada()))
                .filter(l -> matchBetweenBigDecimal(l.getPesoBrutoMinimo(), l.getPesoBrutoMaximo(), datoTecnico.getCapacidadCarga()))
                .filter(l -> matchEqualsIgnoreCase(l.getTiempoMotor(), datoTecnico.getTiempoMotor()))
                .filter(LimiteEmision::isActivo)
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
        if (min == null && max == null) return true;
        int val = value.intValue();
        return (min == null || val >= min) && (max == null || val <= max);
    }

    private boolean matchBetweenInteger(Integer min, Integer max, Integer value) {
        // Si el valor medido es nulo, se considera válido solo si también no hay rangos definidos
        if (value == null) {
            return min == null && max == null;
        }
        if (min == null && max == null) {return true;};  //2026
        return (min == null || value >= min) && (max == null || value <= max);
    }

    private boolean matchEqualsIgnoreCase(String dataLimiteEmision, String dataDatoTecnico) {
        if (dataLimiteEmision == null || dataDatoTecnico == null) return true; // Si alguno es nulo,  no filtra
        return dataLimiteEmision.trim().equalsIgnoreCase(dataDatoTecnico.trim());
    }

    private LimiteEmision obtenerLimiteEmisionPorUuidOThrow(String uuid){
        return limiteEmisionRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Limite Emision", "uuid", uuid));
    }

    private TipoCombustible obtenerTipoCombustiblePorUuidOThrow(String uuid){
           return tipoCombustibleRepository.findByUuid(uuid)
            .orElseThrow(()-> new ResourceNotFoundException("Tipo Combustible", "uuid", uuid));
    }

    private TipoParametro obtenerTipoParametroPorUuidOThrow(String uuid){
        return tipoParametroRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("Tipo Parametro", "uuid", uuid));
    }

}
