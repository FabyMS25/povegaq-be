package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.TipoInfraccionDto;
import com.gamq.ambiente.dto.mapper.TipoInfraccionMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Reglamento;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.TipoInfraccion;
import com.gamq.ambiente.repository.ReglamentoRepository;
import com.gamq.ambiente.repository.TipoContribuyenteRepository;
import com.gamq.ambiente.repository.TipoInfraccionRepository;
import com.gamq.ambiente.service.TipoInfraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TipoInfraccionServiceImpl implements TipoInfraccionService {
    @Autowired
    TipoInfraccionRepository tipoInfraccionRepository;
    @Autowired
    TipoContribuyenteRepository tipoContribuyenteRepository;
    @Autowired
    ReglamentoRepository reglamentoRepository;

    @Override
    public TipoInfraccionDto obtenerTipoInfraccionPorUuid(String uuid) {
        Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findByUuid(uuid);
        if(tipoInfraccionOptional.isPresent()){
            return TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccionOptional.get());
        }
        throw new ResourceNotFoundException("Tipo Infraccion", "uuid", uuid);
    }

    @Override
    public TipoInfraccionDto obtenerTipoInfraccionPorGrado(String grado) {
        Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findByGrado(grado);
        if(tipoInfraccionOptional.isPresent()){
            return TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccionOptional.get());
        }
        throw new ResourceNotFoundException("Tipo Infraccion", "grado", grado);
    }

    @Override
    public List<TipoInfraccionDto> obtenerTipoInfracciones() {
        List<TipoInfraccion> tipoInfraccionList = tipoInfraccionRepository.findAll();
        return  tipoInfraccionList.stream().map( tipoInfraccion -> {
            return  TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccion);
        }).collect(Collectors.toList());
    }

    @Override
    public TipoInfraccionDto crearTipoInfraccion(TipoInfraccionDto tipoInfraccionDto) {
        if (tipoInfraccionDto.getTipoContribuyenteDto() == null || tipoInfraccionDto.getTipoContribuyenteDto().getUuid() == null) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "El uuid de tipoContribuyenteDto no puede ser vacío");
        }
        if (tipoInfraccionDto.getReglamentoDto() == null || tipoInfraccionDto.getReglamentoDto().getUuid() == null){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "El uuid de reglamentoDto no puede ser vacío");
        }

        String grado = tipoInfraccionDto.getGrado();
        Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findTipoInfraccionByUuidTipoContribuyenteAndGrado(tipoInfraccionDto.getTipoContribuyenteDto().getUuid(), grado);
        if(tipoInfraccionOptional.isEmpty()){
            Optional<TipoContribuyente> tipoContribuyenteOptional = tipoContribuyenteRepository.findByUuid(tipoInfraccionDto.getTipoContribuyenteDto().getUuid());
            Optional<Reglamento> reglamentoOptional = reglamentoRepository.findByUuid(tipoInfraccionDto.getReglamentoDto().getUuid());
            if(tipoContribuyenteOptional.isPresent()) {
                if(reglamentoOptional.isPresent()) {
                    TipoInfraccion nuevoTipoInfraccion = TipoInfraccionMapper.toTipoInfraccion(tipoInfraccionDto);
                    nuevoTipoInfraccion.setTipoContribuyente(tipoContribuyenteOptional.get());
                    nuevoTipoInfraccion.setReglamento(reglamentoOptional.get());
                    return TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccionRepository.save(nuevoTipoInfraccion));
                }
                else {
                    throw new ResourceNotFoundException("reglamento", "uuid", tipoInfraccionDto.getReglamentoDto().getUuid());
                }
            }
            else {
                throw new ResourceNotFoundException("Tipo Contribuyente", "uuid", tipoInfraccionDto.getTipoContribuyenteDto().getUuid());
            }
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el Tipo de Infraccion ya existe");
    }

    private TipoContribuyente obtenerTipoContribuyente(String uuid) {
        return tipoContribuyenteRepository.findByUuid(uuid)
                .orElseThrow(() -> new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "El uuid de tipo de contribuyente no existe"));
    }

    @Override
    public TipoInfraccionDto actualizarTipoInfraccion(TipoInfraccionDto tipoInfraccionDto) {
        if (tipoInfraccionDto.getTipoContribuyenteDto() == null || tipoInfraccionDto.getTipoContribuyenteDto().getUuid() == null) {
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "El uuid en tipoContribuyenteDto no puede ser vacío");
        }
        if (tipoInfraccionDto.getReglamentoDto() == null || tipoInfraccionDto.getReglamentoDto().getUuid() == null){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "El uuid de reglamentoDto no puede ser vacío");
        }

        TipoContribuyente tipoContribuyente = obtenerTipoContribuyente(tipoInfraccionDto.getTipoContribuyenteDto().getUuid());

        if (tipoInfraccionRepository.existsTipoInfraccionLikeUuidTipoContribuyenteAndGrado(tipoInfraccionDto.getGrado(),tipoContribuyente.getUuid(),tipoInfraccionDto.getUuid())){
            throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "Ya existe un tipo infraccion para la fecha de aplicación del tipo de infraccion");
        }

        Optional<TipoInfraccion> tipoInfraccionOptional = tipoInfraccionRepository.findByUuid(tipoInfraccionDto.getUuid());
        if (tipoInfraccionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Tipo Infraccion", "uuid",tipoInfraccionDto.getUuid());
        }
        Optional<Reglamento> reglamentoOptional = reglamentoRepository.findByUuid(tipoInfraccionDto.getReglamentoDto().getUuid());
        if (reglamentoOptional.isEmpty()) {
            throw new ResourceNotFoundException("Reglamento", "uuid",tipoInfraccionDto.getReglamentoDto().getUuid());
        }


        TipoInfraccion updateTipoInfraccion = TipoInfraccionMapper.toTipoInfraccion(tipoInfraccionDto);
        updateTipoInfraccion.setIdTipoInfraccion(tipoInfraccionOptional.get().getIdTipoInfraccion());
        updateTipoInfraccion.setTipoContribuyente(tipoContribuyente);
        updateTipoInfraccion.setReglamento(reglamentoOptional.get());
        return TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccionRepository.save(updateTipoInfraccion));
    }

    @Override
    public TipoInfraccionDto eliminarTipoInfraccion(String uuid) {
        TipoInfraccion tipoInfraccionQBE = new TipoInfraccion(uuid);
        Optional<TipoInfraccion> optionalTipoInfraccion = tipoInfraccionRepository.findOne(Example.of(tipoInfraccionQBE));
        if(optionalTipoInfraccion.isPresent()){
            TipoInfraccion tipoInfraccion = optionalTipoInfraccion.get();
           /* if(!tipoInfraccion.getTipoInfraccionInspeccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "el TipoInfraccion ya esta siendo usado por las inspecciones");
            }*/
            tipoInfraccionRepository.delete(tipoInfraccion);
            return TipoInfraccionMapper.toTipoInfraccionDto(tipoInfraccion);
        }
        throw new ResourceNotFoundException("Tipo Infraccion","uuid", uuid);
    }

}
