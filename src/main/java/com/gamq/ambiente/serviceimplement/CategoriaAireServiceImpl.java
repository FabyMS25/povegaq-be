package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.CategoriaAireDto;
import com.gamq.ambiente.dto.mapper.CategoriaAireMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.exceptions.ResourceNotFoundException;
import com.gamq.ambiente.model.CategoriaAire;
import com.gamq.ambiente.repository.CategoriaAireRepository;
import com.gamq.ambiente.service.CategoriaAireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoriaAireServiceImpl implements CategoriaAireService {
    @Autowired
    CategoriaAireRepository categoriaAireRepository;

    @Override
    public CategoriaAireDto obtenerCategoriaAirePorUuid(String uuid) {
        CategoriaAire categoriaAire = obtenerCategoriaAirePorUuidOThrow(uuid);
        return CategoriaAireMapper.toCategoriaAireDto(categoriaAire);
    }

    @Override
    public CategoriaAireDto obtenerCategoriaAirePorCategoria(String categoria) {
        CategoriaAire categoriaAire = categoriaAireRepository.findByCategoria(categoria)
                .orElseThrow(()-> new ResourceNotFoundException("CategoriaAire","categoria", categoria));
        return CategoriaAireMapper.toCategoriaAireDto(categoriaAire);
    }

    @Override
    public List<CategoriaAireDto> obtenerCategoriasAire() {
        List<CategoriaAire> categoriaAireList = categoriaAireRepository.findAll();
        return  categoriaAireList.stream().map( categoriaAire -> {
            return  CategoriaAireMapper.toCategoriaAireDto(categoriaAire);
        }).collect(Collectors.toList());
    }

    @Override
    public CategoriaAireDto crearCategoriaAire(CategoriaAireDto categoriaAireDto) {
        String categoria = categoriaAireDto.getCategoria();
        if(categoria==null || categoria.isBlank()){ throw new ResourceNotFoundException("CategoriaAire","categoria", categoria);}
        if (categoriaAireRepository.findByCategoria(categoria).isPresent()) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El categoria ya existe");
        }
        String color = categoriaAireDto.getColor();
        if (color == null || color.isBlank()){ throw new ResourceNotFoundException("CategoriaAire", "color", color);}
        if (categoriaAireRepository.findByColor(color).isPresent()){
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "El color ya existe");
        }

        CategoriaAire nuevoCategoriaAire = CategoriaAireMapper.toCategoriaAire(categoriaAireDto);
        return CategoriaAireMapper.toCategoriaAireDto(categoriaAireRepository.save(nuevoCategoriaAire));
    }

    @Override
    public CategoriaAireDto actualizarCategoriaAire(CategoriaAireDto categoriaAireDto) {
        CategoriaAire categoriaAire = obtenerCategoriaAirePorUuidOThrow(categoriaAireDto.getUuid());
        if (categoriaAireRepository.exitsCategoriaAireLikeCategoriaAndColor(categoriaAireDto.getCategoria().toLowerCase(), categoriaAireDto.getColor().toLowerCase(), categoriaAireDto.getUuid())) {
            throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el CategoriaAire ya existe");
        }
        CategoriaAire updateCategoriaAire = CategoriaAireMapper.toCategoriaAire(categoriaAireDto);
        updateCategoriaAire.setIdCategoriaAire(categoriaAire.getIdCategoriaAire());
        return CategoriaAireMapper.toCategoriaAireDto(categoriaAireRepository.save(updateCategoriaAire));
    }

    @Override
    public CategoriaAireDto eliminarCategoriaAire(String uuid) {
        CategoriaAire categoriaAire = obtenerCategoriaAirePorUuidOThrow(uuid);
        categoriaAireRepository.delete(categoriaAire);
        return CategoriaAireMapper.toCategoriaAireDto(categoriaAire);
    }

    @Override
    public List<CategoriaAireDto> obtenerCategoriasAireActivas() {
        List<CategoriaAire> categoriaAireList = categoriaAireRepository.findByActivoTrue();
        return categoriaAireList.stream().map(categoriaAire -> {
            return CategoriaAireMapper.toCategoriaAireDto(categoriaAire);
        }).collect(Collectors.toList());
    }

    @Override
    public CategoriaAireDto actualizarCategoriaAireActivo(String uuidCategoriaAire, boolean nuevoActivo) {
        CategoriaAire categoriaAire = categoriaAireRepository.findByUuid(uuidCategoriaAire)
                .orElseThrow(()-> new ResourceNotFoundException("CategoriaAire", "uuid", uuidCategoriaAire));
        categoriaAire.setActivo(nuevoActivo);
        categoriaAireRepository.save(categoriaAire);
        return CategoriaAireMapper.toCategoriaAireDto(categoriaAire);
    }

    private CategoriaAire obtenerCategoriaAirePorUuidOThrow(String uuid){
        return categoriaAireRepository.findByUuid(uuid)
                .orElseThrow(()-> new ResourceNotFoundException("CategoriaAire", "uuid",uuid));
    }
}
