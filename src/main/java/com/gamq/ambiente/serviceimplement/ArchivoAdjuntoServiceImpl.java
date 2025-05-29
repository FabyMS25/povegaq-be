package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.ArchivoAdjuntoDto;
import com.gamq.ambiente.dto.mapper.ArchivoAdjuntoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.model.ArchivoAdjunto;
import com.gamq.ambiente.model.RequisitoInspeccion;
import com.gamq.ambiente.repository.ArchivoAdjuntoRepository;
import com.gamq.ambiente.repository.RequisitoInspeccionRepository;
import com.gamq.ambiente.service.ArchivoAdjuntoService;
import com.gamq.ambiente.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ArchivoAdjuntoServiceImpl implements ArchivoAdjuntoService {
    @Autowired
    private ArchivoAdjuntoRepository archivoAdjuntoRepository;
    @Autowired
    private RequisitoInspeccionRepository requisitoInspeccionRepository;
    @Autowired
    private UploadFileService uploadFileService;

    @Value("${files.path}/adjuntos")
    private String filePath;

    @Override
    public ArchivoAdjuntoDto obtenerArchivoAdjuntoPorUuid(String uuid) {
        Optional<ArchivoAdjunto> archivoAdjuntoOptional = archivoAdjuntoRepository.findByUuid(uuid);
        if (archivoAdjuntoOptional.isPresent()) {
            return ArchivoAdjuntoMapper.toArchivoAdjuntoDto(archivoAdjuntoOptional.get());
        }
        throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND,"no existe archivo adjunto");
    }

    @Override
    public List<ArchivoAdjuntoDto> obtenerArchivosAdjuntos(String uuidRequisitoInspeccion) {
        List<ArchivoAdjunto> archivoAdjuntoList = archivoAdjuntoRepository.findByUuidRequisitoInspeccion(uuidRequisitoInspeccion);
        return archivoAdjuntoList.stream().map( archivoAdjunto -> {
            return ArchivoAdjuntoMapper.toArchivoAdjuntoDto( archivoAdjunto);
        }).collect(Collectors.toList());
    }


    @Override
    public ArchivoAdjuntoDto crearArchivoAdjunto(ArchivoAdjuntoDto archivoAdjuntoDto) {
        String nombre = archivoAdjuntoDto.getNombre();
        Optional<ArchivoAdjunto> archivoAdjuntoOptional = archivoAdjuntoRepository.findByNombre(nombre);
        if (archivoAdjuntoOptional.isEmpty())
        {
            ArchivoAdjunto nuevoArchivoAdjunto = ArchivoAdjuntoMapper.toArchivoAdjunto(archivoAdjuntoDto);
            Optional<RequisitoInspeccion> requisitoInspeccionOptional = requisitoInspeccionRepository.findByUuid(archivoAdjuntoDto.getRequisitoInspeccionDto().getUuid());


            if (requisitoInspeccionOptional.isPresent()) {

                nuevoArchivoAdjunto.setRequisitoInspeccion(requisitoInspeccionOptional.get());
                if (archivoAdjuntoDto.getArchivoFile() != null) {
                    try {
                        String codigoUuid = UUID.randomUUID().toString();
                        String filenombre = uploadFileService.saveFile(archivoAdjuntoDto.getArchivoFile(), codigoUuid, filePath);
                        nuevoArchivoAdjunto.setUuid(codigoUuid);
                        nuevoArchivoAdjunto.setRutaArchivo(filePath);
                        nuevoArchivoAdjunto.setNombre(filenombre);
                        return ArchivoAdjuntoMapper.toArchivoAdjuntoDto(archivoAdjuntoRepository.save(nuevoArchivoAdjunto));

                    } catch (IOException ioException) {
                        throw new BlogAPIException("500-INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "no existe");
                    }
                }
                else {
                    throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "debe adjuntar el archivo");
                }
            }
            else {
                throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND,"el uuid del requisito de inspecion no existe");
            }
        }
        throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "el nombre del archivo ya existe");
    }

    @Override
    public ArchivoAdjuntoDto actualizarArchivoAdjunto(ArchivoAdjuntoDto archivoAdjuntoDto) {
        ArchivoAdjunto archivoAdjuntoQBE = new ArchivoAdjunto(archivoAdjuntoDto.getUuid());
        Optional<ArchivoAdjunto> archivoAdjuntoOptional = archivoAdjuntoRepository.findOne(Example.of(archivoAdjuntoQBE));
        if (archivoAdjuntoOptional.isPresent())
        {
            ArchivoAdjunto archivoAdjunto = ArchivoAdjuntoMapper.toArchivoAdjunto(archivoAdjuntoDto);
            archivoAdjunto.setIdArchivoAdjunto(archivoAdjuntoOptional.get().getIdArchivoAdjunto());
            archivoAdjunto.setRequisitoInspeccion( archivoAdjuntoOptional.get().getRequisitoInspeccion());
            if (archivoAdjuntoDto.getArchivoFile() != null) {
                try {
                    boolean eliminado = uploadFileService.deleteFile(archivoAdjuntoOptional.get().getNombre(), filePath);
                    String codigoUuid = UUID.randomUUID().toString();
                    String filename = uploadFileService.saveFile(archivoAdjuntoDto.getArchivoFile(), codigoUuid, filePath);
                    archivoAdjunto.setNombre(filename);
                    archivoAdjunto.setRutaArchivo(filePath);
                    return ArchivoAdjuntoMapper.toArchivoAdjuntoDto(archivoAdjuntoRepository.save(archivoAdjunto));
                } catch (IOException ioe) {
                    throw new BlogAPIException("500-INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "no logro modificar el archivo");
                }
            }
            else {
                throw new BlogAPIException("409-CONFLICT", HttpStatus.CONFLICT, "debe adjuntar el archivo");
            }
        }
        throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "no existe el archivo adjunto");
    }

    @Override
    public ArchivoAdjuntoDto eliminarArchivoAdjunto(String uuid) {
        ArchivoAdjunto archivoAdjuntoQBE = new ArchivoAdjunto(uuid);
        Optional<ArchivoAdjunto> archivoAdjuntoOptional = archivoAdjuntoRepository.findByUuid(archivoAdjuntoQBE.getUuid());

        if(archivoAdjuntoOptional.isPresent())
        {
            ArchivoAdjunto archivoAdjunto = archivoAdjuntoOptional.get();
            archivoAdjuntoRepository.delete(archivoAdjunto);
            return ArchivoAdjuntoMapper.toArchivoAdjuntoDto(archivoAdjunto);
        }
        throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND,"no existe el archivo adjunto");
    }

    @Override
    public Resource descargarArchivo(String nombreFile) {
        return uploadFileService.loadFile(nombreFile,filePath);
    }

}
