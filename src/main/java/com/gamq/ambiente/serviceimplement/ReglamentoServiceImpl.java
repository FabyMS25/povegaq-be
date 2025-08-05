package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.ReglamentoDto;
import com.gamq.ambiente.dto.mapper.ReglamentoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Reglamento;
import com.gamq.ambiente.repository.ReglamentoRepository;
import com.gamq.ambiente.service.ReglamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReglamentoServiceImpl implements ReglamentoService {
    @Autowired
    ReglamentoRepository reglamentoRepository;

    @Override
    public ReglamentoDto obtenerReglamentoPorUuid(String uuid) {
        Optional<Reglamento> reglamentoOptional = reglamentoRepository.findByUuid(uuid);
        if(reglamentoOptional.isPresent()){
            return ReglamentoMapper.toReglamentoDto(reglamentoOptional.get());
        }
        throw new ResourceNotFoundException("Reglamento", "uuid", uuid);
    }

    @Override
    public ReglamentoDto obtenerReglamentoPorCodigo(String codigo) {
        Optional<Reglamento> reglamentoOptional = reglamentoRepository.findByCodigo(codigo.toLowerCase());
        if(reglamentoOptional.isPresent()){
            return ReglamentoMapper.toReglamentoDto(reglamentoOptional.get());
        }
        throw new ResourceNotFoundException("Codigo", "uuid", codigo);
    }

    @Override
    public List<ReglamentoDto> obtenerReglamentos() {
        List<Reglamento> reglamentoList = reglamentoRepository.findAll();
        return  reglamentoList.stream().map( reglamento -> {
            return  ReglamentoMapper.toReglamentoDto(reglamento);
        }).collect(Collectors.toList());
    }

    @Override
    public ReglamentoDto crearReglamento(ReglamentoDto reglamentoDto) {
        String codigo = reglamentoDto.getCodigo();
        if(codigo==null){ throw new ResourceNotFoundException("Reglamento","codigo", codigo);}
        Optional<Reglamento> reglamentoOptional = reglamentoRepository.findByCodigo(codigo);
        if(reglamentoOptional.isEmpty()){
            Reglamento nuevoReglamento = ReglamentoMapper.toReglamento(reglamentoDto);
            return ReglamentoMapper.toReglamentoDto(reglamentoRepository.save(nuevoReglamento));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la Reglamento ya existe");
    }

    @Override
    public ReglamentoDto actualizarReglamento(ReglamentoDto reglamentoDto) {
        Optional<Reglamento> reglamentoOptional = reglamentoRepository.findByUuid(reglamentoDto.getUuid());
        if(reglamentoOptional.isPresent()) {
            if (!reglamentoRepository.exitsReglamentoLikeCodigo(reglamentoDto.getCodigo().toLowerCase(), reglamentoDto.getUuid())) {
                Reglamento updateReglamento = ReglamentoMapper.toReglamento(reglamentoDto);
                updateReglamento.setIdReglamento(reglamentoOptional.get().getIdReglamento());
                return ReglamentoMapper.toReglamentoDto(reglamentoRepository.save(updateReglamento));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el codigo ya existe");
            }
        }
        throw new ResourceNotFoundException("Reglamento", "uuid",reglamentoDto.getUuid());
    }

    @Override
    public ReglamentoDto eliminarReglamento(String uuid) {
        Optional<Reglamento> optionalReglamento = reglamentoRepository.findByUuid(uuid);
        if(optionalReglamento.isPresent()){
            Reglamento reglamento = optionalReglamento.get();

            if(!reglamento.getTipoInfraccionList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "este Reglamento ya tiene infracciones");
            }
            reglamentoRepository.delete(reglamento);
            return ReglamentoMapper.toReglamentoDto(reglamento);
        }
        throw new ResourceNotFoundException("Reglamento","uuid", uuid);
    }


    @Override
    public boolean actualizarReglamentoActivoToInactivo() {
        if (reglamentoRepository.updateAllToInactivo()>0){
            return true;
        }
        else {return false;}
    }
}
