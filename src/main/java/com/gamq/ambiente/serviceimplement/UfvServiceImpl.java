package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.UfvDto;
import com.gamq.ambiente.dto.mapper.UfvMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.Ufv;
import com.gamq.ambiente.repository.UfvRepository;
import com.gamq.ambiente.service.UfvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UfvServiceImpl implements UfvService {
    @Autowired
    UfvRepository ufvRepository;

    @Override
    public UfvDto obtenerUfvPorUuid(String uuid) {
        Optional<Ufv> ufvOptional = ufvRepository.findByUuid(uuid);
        if(ufvOptional.isPresent()){
            return UfvMapper.toUfvDto(ufvOptional.get());
        }
        throw new ResourceNotFoundException("Ufv", "uuid", uuid);
    }

    @Override
    public UfvDto obtenerUfvPorFecha(Date fecha) {
        Optional<Ufv> ufvOptional = ufvRepository.findByFecha(fecha);
        if(ufvOptional.isPresent()){
            return UfvMapper.toUfvDto(ufvOptional.get());
        }
        throw new ResourceNotFoundException("ufv", "fecha", fecha.toString());
    }

    @Override
    public List<UfvDto> obtenerUfves() {
        List<Ufv> ufvList = ufvRepository.findAll();
        return  ufvList.stream().map( ufv -> {
            return  UfvMapper.toUfvDto(ufv);
        }).collect(Collectors.toList());
    }

    @Override
    public UfvDto crearUfv(UfvDto ufvDto) {
        Date fecha = ufvDto.getFecha();
        if(fecha==null){ throw new ResourceNotFoundException("Ufv","fecha", fecha.toString());}
        Optional<Ufv> ufvOptional = ufvRepository.findByFecha(fecha);
        if(ufvOptional.isEmpty()){
            Ufv nuevoUfv = UfvMapper.toUfv(ufvDto);
            return UfvMapper.toUfvDto(ufvRepository.save(nuevoUfv));
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la Ufv de fecha ya existe");
    }

    @Override
    public UfvDto actualizarUfv(UfvDto ufvDto) {
        Optional<Ufv> ufvOptional = ufvRepository.findByUuid(ufvDto.getUuid());
        if(ufvOptional.isPresent()) {
            if (!ufvRepository.exitsUfvLikeFecha(ufvDto.getFecha(), ufvDto.getUuid())) {
                Ufv updateUfv = UfvMapper.toUfv(ufvDto);
                updateUfv.setIdUfv(ufvOptional.get().getIdUfv());
                return UfvMapper.toUfvDto(ufvRepository.save(updateUfv));
            } else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "la Ufv de fecha ya existe");
            }
        }
        throw new ResourceNotFoundException("ufv", "uuid",ufvDto.getUuid());
    }

    @Override
    public UfvDto eliminarUfv(String uuid) {
        Ufv ufvQBE = new Ufv(uuid);
        Optional<Ufv> optionalUfv = ufvRepository.findOne(Example.of(ufvQBE));
        if(optionalUfv.isPresent()){
            Ufv ufv = optionalUfv.get();
         /*   if(!Ufv.getInsumoBaseList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "esta tipo de parametro ya esta siendo usado por los insumos");
            }
            if(!Ufv.getInsumoList().isEmpty()){
                throw new BlogAPIException("400-BAD_REQUEST", HttpStatus.BAD_REQUEST, "esta tipo de parametro ya esta siendo usado por los proyectos");
            }*/
            ufvRepository.delete(ufv);
            return UfvMapper.toUfvDto(ufv);
        }
        throw new ResourceNotFoundException("Ufv","uuid", uuid);
    }

}
