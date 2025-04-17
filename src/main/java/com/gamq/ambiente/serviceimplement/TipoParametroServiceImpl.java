package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.TipoParametroDto;
import com.gamq.ambiente.dto.mapper.TipoParametroMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.TipoParametro;
import com.gamq.ambiente.repository.TipoParametroRepository;
import com.gamq.ambiente.service.TipoParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TipoParametroServiceImpl implements TipoParametroService {
    @Autowired
    TipoParametroRepository tipoParametroRepository;
    @Override
    public TipoParametroDto obtenerTipoParametroPorUuid(String uuid) {
        Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByUuid(uuid);
        if(tipoParametroOptional.isPresent()){
            return TipoParametroMapper.toTipoParametroDto(tipoParametroOptional.get());
        }
        throw new ResourceNotFoundException("Tipo Parametro", "uuid", uuid);
    }

    @Override
    public TipoParametroDto obtenerTipoParametroPorNombre(String nombre) {
        Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByNombre(nombre);
        if(tipoParametroOptional.isPresent()){
            return TipoParametroMapper.toTipoParametroDto(tipoParametroOptional.get());
        }
        throw new ResourceNotFoundException("Tipo Parametro", "nombre", nombre.toString());
    }

    @Override
    public List<TipoParametroDto> obtenerTipoParametros() {
        List<TipoParametro> tipoParametroList = tipoParametroRepository.findAll();
        return  tipoParametroList.stream().map( tipoParametro -> {
            return  TipoParametroMapper.toTipoParametroDto(tipoParametro);
        }).collect(Collectors.toList());
    }

    @Override
    public TipoParametroDto crearTipoParametro(TipoParametroDto tipoParametroDto) {
        String nombre = tipoParametroDto.getNombre();
        if(nombre==null){ throw new ResourceNotFoundException("Tipo Parametro","nombre", nombre);}
        Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByNombre(nombre);
        if(tipoParametroOptional.isEmpty()){
            TipoParametro nuevoTipoParametro = TipoParametroMapper.toTipoParametro(tipoParametroDto);
            return TipoParametroMapper.toTipoParametroDto(tipoParametroRepository.save(nuevoTipoParametro));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Tipo Parametro ya existe");
    }

    @Override
    public TipoParametroDto actualizarTipoParametro(TipoParametroDto tipoParametroDto) {
        Optional<TipoParametro> tipoParametroOptional = tipoParametroRepository.findByUuid(tipoParametroDto.getUuid());
        if(tipoParametroOptional.isPresent()){
            if (!tipoParametroRepository.exitsTipoParametroLikeNombre(tipoParametroDto.getNombre().toLowerCase(), tipoParametroDto.getUuid())) {
                TipoParametro updateTipoParametro = TipoParametroMapper.toTipoParametro(tipoParametroDto);
                updateTipoParametro.setIdTipoParametro(tipoParametroOptional.get().getIdTipoParametro());
                return TipoParametroMapper.toTipoParametroDto(tipoParametroRepository.save(updateTipoParametro));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Tipo Parametro ya existe");
            }
        }
        throw new ResourceNotFoundException("Tipo Parametro", "uuid",tipoParametroDto.getUuid());
    }

    @Override
    public TipoParametroDto eliminarTipoParametro(String uuid) {
        TipoParametro tipoParametroQBE = new TipoParametro(uuid);
        Optional<TipoParametro> optionalTipoParametro = tipoParametroRepository.findOne(Example.of(tipoParametroQBE));
        if(optionalTipoParametro.isPresent()){
            TipoParametro tipoParametro = optionalTipoParametro.get();
            if(!tipoParametro.getLimiteEmisionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el Tipo Parametro ya esta siendo usado por los limites de emision");
            }
            tipoParametroRepository.delete(tipoParametro);
            return TipoParametroMapper.toTipoParametroDto(tipoParametro);
        }
        throw new ResourceNotFoundException("Tipo Parametro","uuid", uuid);
    }

    @Override
    public TipoParametroDto actualizarTipoParametroActivo(String uuid, boolean nuevoActivo) {
        TipoParametro tipoParametro = tipoParametroRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Tipo de parámetro no encontrado"));

        tipoParametro.setActivo(nuevoActivo);
        tipoParametroRepository.save(tipoParametro);
        return TipoParametroMapper.toTipoParametroDto(tipoParametro);
    }
}
