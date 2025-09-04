package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.MedicionAireDto;
import com.gamq.ambiente.dto.mapper.MedicionAireMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Contaminante;
import com.gamq.ambiente.model.Estacion;
import com.gamq.ambiente.model.MedicionAire;
import com.gamq.ambiente.repository.ContaminanteRepository;
import com.gamq.ambiente.repository.EstacionRepository;
import com.gamq.ambiente.repository.MedicionAireRepository;
import com.gamq.ambiente.service.MedicionAireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicionAireServiceImpl implements MedicionAireService {
    @Autowired
    MedicionAireRepository medicionAireRepository;
    @Autowired
    EstacionRepository estacionRepository;
    @Autowired
    ContaminanteRepository contaminanteRepository;

    @Override
    public MedicionAireDto obtenerMedicionAirePorUuid(String uuid) {
        MedicionAire medicionAire = obtenerMedicionAirePorUuidOThrow(uuid);
        return MedicionAireMapper.toMedicionAireDto(medicionAire);
    }

    @Override
    public List<MedicionAireDto> obtenerMedicionAirePorFecha(Date fecha) {
       List<MedicionAire> medicionAireList = medicionAireRepository.findByFecha(fecha);
          return medicionAireList.stream().map(medicionAire -> {
              return MedicionAireMapper.toMedicionAireDto(medicionAire);
          }).collect(Collectors.toList());
    }

    @Override
    public List<MedicionAireDto> obtenerMedicionesAire() {
        List<MedicionAire> medicionAireList = medicionAireRepository.findAll();
        return  medicionAireList.stream().map( medicionAire -> {
            return  MedicionAireMapper.toMedicionAireDto(medicionAire);
        }).collect(Collectors.toList());
    }

    @Override
    public MedicionAireDto crearMedicionAire(MedicionAireDto medicionAireDto) {
        validarRelacion(medicionAireDto);
        Estacion estacion = obtenerEstacionPorUuid(medicionAireDto.getEstacionDto().getUuid());
        Contaminante contaminante = obtenerContaminantePorUuid(medicionAireDto.getContaminanteDto().getUuid());

        MedicionAire nuevoMedicionAire = MedicionAireMapper.toMedicionAire(medicionAireDto);
        nuevoMedicionAire.setEstacion(estacion);
        nuevoMedicionAire.setContaminante(contaminante);
        return MedicionAireMapper.toMedicionAireDto(medicionAireRepository.save(nuevoMedicionAire));
    }

    @Override
    public List<MedicionAireDto> crearMedicionesAireMasivo(List<MedicionAireDto> medicionesDto) {
        List<MedicionAire> mediciones = medicionesDto.stream().map( medicionAireDto -> {
                    validarRelacion(medicionAireDto);
                    Estacion estacion = obtenerEstacionPorUuid(medicionAireDto.getEstacionDto().getUuid());
                    Contaminante contaminante = obtenerContaminantePorUuid(medicionAireDto.getContaminanteDto().getUuid());
                    MedicionAire medicionAire = MedicionAireMapper.toMedicionAire(medicionAireDto);
                    medicionAire.setEstacion(estacion);
                    medicionAire.setContaminante(contaminante);
                    return medicionAire;
                })
                .collect(Collectors.toList());

        List<MedicionAire> medicionesGuardadas = medicionAireRepository.saveAll(mediciones);

        return medicionesGuardadas.stream()
                .map(MedicionAireMapper::toMedicionAireDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicionAireDto actualizarMedicionAire(MedicionAireDto medicionAireDto) {
        validarRelacion(medicionAireDto);
        Estacion estacion = obtenerEstacionPorUuid(medicionAireDto.getEstacionDto().getUuid());
        Contaminante contaminante = obtenerContaminantePorUuid(medicionAireDto.getContaminanteDto().getUuid());
        MedicionAire medicionAire = obtenerMedicionAirePorUuidOThrow(medicionAireDto.getUuid());
        MedicionAire updateMedicionAire = MedicionAireMapper.toMedicionAire(medicionAireDto);
        updateMedicionAire.setIdMedicionAire(medicionAire.getIdMedicionAire());

        updateMedicionAire.setEstacion(estacion);
        updateMedicionAire.setContaminante(contaminante);

        return MedicionAireMapper.toMedicionAireDto(medicionAireRepository.save(updateMedicionAire));
    }

    @Override
    public MedicionAireDto eliminarMedicionAire(String uuid) {
        MedicionAire medicionAire = obtenerMedicionAirePorUuidOThrow(uuid);
        medicionAireRepository.delete(medicionAire);
        return MedicionAireMapper.toMedicionAireDto(medicionAire);
    }

    private MedicionAire obtenerMedicionAirePorUuidOThrow(String uuid){
        return medicionAireRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("MedicionAire", "uuid",uuid));
    }

    private void validarRelacion(MedicionAireDto medicionAireDto) {
        if (medicionAireDto.getEstacionDto() == null || medicionAireDto.getEstacionDto().getUuid() == null) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "Ingrese el uuid de la estación");
        }
        if (medicionAireDto.getContaminanteDto() == null || medicionAireDto.getContaminanteDto().getUuid() == null) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "Ingrese el uuid del contaminante");
        }
    }

    private Estacion obtenerEstacionPorUuid(String uuid) {
        return estacionRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Estacion", "uuid", uuid));
    }

    private Contaminante obtenerContaminantePorUuid(String uuid) {
        return contaminanteRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Contaminante", "uuid", uuid));
    }
}
