package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.TipoContribuyenteDto;
import com.gamq.ambiente.dto.mapper.TipoContribuyenteMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.repository.TipoContribuyenteRepository;
import com.gamq.ambiente.service.TipoContribuyenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TipoContribuyenteServiceImpl implements TipoContribuyenteService {
    @Autowired
    TipoContribuyenteRepository tipoContribuyenteRepository;

    @Override
    public TipoContribuyenteDto obtenerTipoContribuyentePorUuid(String uuid) {
        Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByUuid(uuid);
        if( tipoContribuyenteOptional.isPresent()){
            return TipoContribuyenteMapper.toTipoContribuyenteDto(tipoContribuyenteOptional.get());
        }
        throw new ResourceNotFoundException("Tipo Contribuyente", "uuid", uuid);
    }

    @Override
    public TipoContribuyenteDto obtenerTipoContribuyentePorDescripcion(String descripcion) {
        Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByDescripcion(descripcion);
        if(tipoContribuyenteOptional.isPresent()){
            return TipoContribuyenteMapper.toTipoContribuyenteDto(tipoContribuyenteOptional.get());
        }
        throw new ResourceNotFoundException("Tipo Contribuyente", "descripcion", descripcion.toString());
    }

    @Override
    public List<TipoContribuyenteDto> obtenerTipoContribuyentes() {
        List<TipoContribuyente> tipoContribuyenteList = tipoContribuyenteRepository.findAll();
        return  tipoContribuyenteList.stream().map( tipoContribuyente -> {
            return  TipoContribuyenteMapper.toTipoContribuyenteDto(tipoContribuyente);
        }).collect(Collectors.toList());
    }

    @Override
    public TipoContribuyenteDto crearTipoContribuyente(TipoContribuyenteDto tipoContribuyenteDto) {
        String descripcion = tipoContribuyenteDto.getDescripcion();
        if(descripcion==null){ throw new ResourceNotFoundException("Tipo Contribuyente","descripcion", descripcion);}
        Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByDescripcion(descripcion);
        if(tipoContribuyenteOptional.isEmpty()){
            TipoContribuyente nuevoTipoContribuyente = TipoContribuyenteMapper.toTipoContribuyente(tipoContribuyenteDto);
            return TipoContribuyenteMapper.toTipoContribuyenteDto(tipoContribuyenteRepository.save(nuevoTipoContribuyente));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Tipo Contribuyente ya existe");
    }

    @Override
    public TipoContribuyenteDto actualizarTipoContribuyente(TipoContribuyenteDto tipoContribuyenteDto) {
        Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByUuid(tipoContribuyenteDto.getUuid());
        if(tipoContribuyenteOptional.isPresent()) {
            if (!tipoContribuyenteRepository.exitsTipoContribuyenteLikeDescripcion(tipoContribuyenteDto.getDescripcion().toLowerCase(), tipoContribuyenteDto.getUuid())) {
                TipoContribuyente updateTipoContribuyente = TipoContribuyenteMapper.toTipoContribuyente(tipoContribuyenteDto);
                updateTipoContribuyente.setIdTipoContribuyente(tipoContribuyenteOptional.get().getIdTipoContribuyente());
                return TipoContribuyenteMapper.toTipoContribuyenteDto(tipoContribuyenteRepository.save(updateTipoContribuyente));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el TipovContribuyente ya existe");
            }
        }
        throw new ResourceNotFoundException("Tipo Contribuyente", "uuid",tipoContribuyenteDto.getUuid());
    }

    @Override
    public TipoContribuyenteDto eliminarTipoContribuyente(String uuid) {
        TipoContribuyente tipoContribuyenteQBE = new TipoContribuyente(uuid);
        Optional<TipoContribuyente> optionalTipoContribuyente = tipoContribuyenteRepository.findOne(Example.of(tipoContribuyenteQBE));
        if(optionalTipoContribuyente.isPresent()){
            TipoContribuyente tipoContribuyente = optionalTipoContribuyente.get();
           /* if(!tipoContribuyente.getTipoContribuyenteInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el TipoContribuyente ya esta siendo usado por las inspecciones");
            }*/
            tipoContribuyenteRepository.delete(tipoContribuyente);
            return TipoContribuyenteMapper.toTipoContribuyenteDto(tipoContribuyente);
        }
        throw new ResourceNotFoundException("Tipo Contribuyente","uuid", uuid);
    }
}
