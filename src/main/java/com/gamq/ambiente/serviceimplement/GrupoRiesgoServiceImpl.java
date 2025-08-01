package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.GrupoRiesgoDto;
import com.gamq.ambiente.dto.mapper.GrupoRiesgoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.CategoriaAire;
import com.gamq.ambiente.model.GrupoRiesgo;
import com.gamq.ambiente.repository.CategoriaAireRepository;
import com.gamq.ambiente.repository.GrupoRiesgoRepository;
import com.gamq.ambiente.service.GrupoRiesgoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoRiesgoServiceImpl implements GrupoRiesgoService {
    @Autowired
    GrupoRiesgoRepository grupoRiesgoRepository;
    @Autowired
    CategoriaAireRepository categoriaAireRepository;

    @Override
    public GrupoRiesgoDto obtenerGrupoRiesgoPorUuid(String uuid) {
        GrupoRiesgo grupoRiesgo = obtenerGrupoRiesgoPorUuidOThrow(uuid);
        return GrupoRiesgoMapper.toGrupoRiesgoDto(grupoRiesgo);
    }

    @Override
    public GrupoRiesgoDto obtenerGrupoRiesgoPorGrupo(String grupo) {
        GrupoRiesgo grupoRiesgo = grupoRiesgoRepository.findByGrupo(grupo)
                .orElseThrow(()-> new ResourceNotFoundException("GrupoRiesgo","grupo", grupo));
        return GrupoRiesgoMapper.toGrupoRiesgoDto(grupoRiesgo);
    }

    @Override
    public List<GrupoRiesgoDto> obtenerGrupoRiesgos() {
        List<GrupoRiesgo> grupoRiesgoList = grupoRiesgoRepository.findAll();
        return  grupoRiesgoList.stream().map( grupoRiesgo -> {
            return  GrupoRiesgoMapper.toGrupoRiesgoDto(grupoRiesgo);
        }).collect(Collectors.toList());
    }

    @Override
    public List<GrupoRiesgoDto> obtenerGrupoRiesgoPorUuidCategoriaAire(String uuidCategoriaAire) {
        List<GrupoRiesgo> grupoRiesgoList = grupoRiesgoRepository.findByUuidCategoriaAire(uuidCategoriaAire);
        return grupoRiesgoList.stream().map(grupoRiesgo -> {
            return GrupoRiesgoMapper.toGrupoRiesgoDto(grupoRiesgo);
        }).collect(Collectors.toList());
    }

    @Override
    public GrupoRiesgoDto crearGrupoRiesgo(GrupoRiesgoDto grupoRiesgoDto) {
        String grupo = grupoRiesgoDto.getGrupo();
        if(grupo == null || grupo.isBlank()){ throw new ResourceNotFoundException("GrupoRiesgo","grupo", grupo);}
        if (grupoRiesgoRepository.findByGrupo(grupo).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El grupoRiesgo ya existe");
        }
        CategoriaAire categoriaAire = categoriaAireRepository.findByUuid(grupoRiesgoDto.getCategoriaAireDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Categoria", "uuid", grupoRiesgoDto.getCategoriaAireDto().getUuid()));

        GrupoRiesgo nuevoGrupoRiesgo = GrupoRiesgoMapper.toGrupoRiesgo(grupoRiesgoDto);
        nuevoGrupoRiesgo.setCategoriaAire(categoriaAire);
        return GrupoRiesgoMapper.toGrupoRiesgoDto(grupoRiesgoRepository.save(nuevoGrupoRiesgo));
    }

    @Override
    public GrupoRiesgoDto actualizarGrupoRiesgo(GrupoRiesgoDto grupoRiesgoDto) {
        GrupoRiesgo grupoRiesgo = obtenerGrupoRiesgoPorUuidOThrow(grupoRiesgoDto.getUuid());
        if (grupoRiesgoRepository.exitsGrupoRiesgoLikeGrupo(grupoRiesgoDto.getGrupo().toLowerCase(), grupoRiesgoDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el GrupoRiesgo ya existe");
        }
        CategoriaAire categoriaAire = categoriaAireRepository.findByUuid(grupoRiesgoDto.getCategoriaAireDto().getUuid())
                .orElseThrow(()-> new ResourceNotFoundException("Categoria", "uuid", grupoRiesgoDto.getCategoriaAireDto().getUuid()));

        GrupoRiesgo updateGrupoRiesgo = GrupoRiesgoMapper.toGrupoRiesgo(grupoRiesgoDto);
        updateGrupoRiesgo.setCategoriaAire(categoriaAire);
        updateGrupoRiesgo.setIdGrupoRiesgo(grupoRiesgo.getIdGrupoRiesgo());
        return GrupoRiesgoMapper.toGrupoRiesgoDto(grupoRiesgoRepository.save(updateGrupoRiesgo));
    }

    @Override
    public GrupoRiesgoDto eliminarGrupoRiesgo(String uuid) {
        GrupoRiesgo grupoRiesgo = obtenerGrupoRiesgoPorUuidOThrow(uuid);
        grupoRiesgoRepository.delete(grupoRiesgo);
        return GrupoRiesgoMapper.toGrupoRiesgoDto(grupoRiesgo);
    }

    private GrupoRiesgo obtenerGrupoRiesgoPorUuidOThrow(String uuid){
        return grupoRiesgoRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("GrupoRiesgo", "uuid", uuid));
    }
}
