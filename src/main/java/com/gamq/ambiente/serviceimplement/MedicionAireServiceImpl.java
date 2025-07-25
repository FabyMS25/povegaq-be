package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.MedicionAireDto;
import com.gamq.ambiente.dto.mapper.MedicionAireMapper;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.MedicionAire;
import com.gamq.ambiente.repository.MedicionAireRepository;
import com.gamq.ambiente.service.MedicionAireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicionAireServiceImpl implements MedicionAireService {
    @Autowired
    MedicionAireRepository medicionAireRepository;

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
        MedicionAire nuevoMedicionAire = MedicionAireMapper.toMedicionAire(medicionAireDto);
        return MedicionAireMapper.toMedicionAireDto(medicionAireRepository.save(nuevoMedicionAire));
    }

    @Override
    public List<MedicionAireDto> crearMedicionesAireMasivo(List<MedicionAireDto> medicionesDto) {
        List<MedicionAire> mediciones = medicionesDto.stream()
                .map(MedicionAireMapper::toMedicionAire)
                .collect(Collectors.toList());

        List<MedicionAire> medicionesGuardadas = medicionAireRepository.saveAll(mediciones);

        return medicionesGuardadas.stream()
                .map(MedicionAireMapper::toMedicionAireDto)
                .collect(Collectors.toList());
    }


    @Override
    public MedicionAireDto actualizarMedicionAire(MedicionAireDto medicionAireDto) {
        MedicionAire medicionAire = obtenerMedicionAirePorUuidOThrow(medicionAireDto.getUuid());
        MedicionAire updateMedicionAire = MedicionAireMapper.toMedicionAire(medicionAireDto);
        updateMedicionAire.setIdMedicionAire(medicionAire.getIdMedicionAire());
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
}
