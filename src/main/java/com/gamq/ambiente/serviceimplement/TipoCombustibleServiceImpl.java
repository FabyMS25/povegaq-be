package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.TipoCombustibleDto;
import com.gamq.ambiente.dto.mapper.TipoCombustibleMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.TipoCombustible;
import com.gamq.ambiente.repository.TipoCombustibleRepository;
import com.gamq.ambiente.service.TipoCombustibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TipoCombustibleServiceImpl implements TipoCombustibleService {
    @Autowired
    TipoCombustibleRepository tipoCombustibleRepository;

    @Override
    public TipoCombustibleDto obtenerTipoCombustiblePorUuid(String uuid) {
        TipoCombustible tipoCombustible = obtenerTipoCombustiblePorUuidOThrow(uuid);
        return TipoCombustibleMapper.toTipoCombustibleDto(tipoCombustible);
    }

    @Override
    public TipoCombustibleDto obtenerTipoCombustiblePorNombre(String nombre) {
        TipoCombustible tipoCombustible = tipoCombustibleRepository.findByNombre(nombre)
                .orElseThrow(()-> new ResourceNotFoundException("TipoCombustible","nombre", nombre));
        return TipoCombustibleMapper.toTipoCombustibleDto(tipoCombustible);
    }

    @Override
    public List<TipoCombustibleDto> obtenerTipoCombustibles() {
        List<TipoCombustible> tipoCombustibleList = tipoCombustibleRepository.findAll();
        return  tipoCombustibleList.stream().map( tipoCombustible -> {
            return  TipoCombustibleMapper.toTipoCombustibleDto(tipoCombustible);
        }).collect(Collectors.toList());
    }

    @Override
    public TipoCombustibleDto crearTipoCombustible(TipoCombustibleDto tipoCombustibleDto) {
        String nombre = tipoCombustibleDto.getNombre();
        if(nombre==null || nombre.isBlank()){ throw new ResourceNotFoundException("TipoCombustible","nombre", nombre);}
        if (tipoCombustibleRepository.findByNombre(nombre).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El tipoCombustible ya existe");
        }
        TipoCombustible nuevoTipoCombustible = TipoCombustibleMapper.toTipoCombustible(tipoCombustibleDto);
        return TipoCombustibleMapper.toTipoCombustibleDto(tipoCombustibleRepository.save(nuevoTipoCombustible));
    }

    @Override
    public TipoCombustibleDto actualizarTipoCombustible(TipoCombustibleDto tipoCombustibleDto) {
        TipoCombustible tipoCombustible = obtenerTipoCombustiblePorUuidOThrow(tipoCombustibleDto.getUuid());
        if (tipoCombustibleRepository.exitsTipoCombustibleLikeNombre(tipoCombustibleDto.getNombre().toLowerCase(), tipoCombustibleDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el TipoCombustible ya existe");
        }
        TipoCombustible updateTipoCombustible = TipoCombustibleMapper.toTipoCombustible(tipoCombustibleDto);
        updateTipoCombustible.setIdTipoCombustible(tipoCombustible.getIdTipoCombustible());
        return TipoCombustibleMapper.toTipoCombustibleDto(tipoCombustibleRepository.save(updateTipoCombustible));
    }

    @Override
    public TipoCombustibleDto eliminarTipoCombustible(String uuid) {
        TipoCombustible tipoCombustible = obtenerTipoCombustiblePorUuidOThrow(uuid);
        if(!tipoCombustible.getVehiculoTipoCombustibleList().isEmpty()){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el tipoCombustible ya esta siendo usado por los vehiculos");
        }
        tipoCombustibleRepository.delete(tipoCombustible);
        return TipoCombustibleMapper.toTipoCombustibleDto(tipoCombustible);
    }

    private TipoCombustible obtenerTipoCombustiblePorUuidOThrow(String uuid){
        return tipoCombustibleRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("TipoCombustible", "uuid", uuid));
    }
}
