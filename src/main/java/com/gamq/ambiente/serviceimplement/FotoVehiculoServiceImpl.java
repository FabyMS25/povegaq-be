package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.dto.FotoVehiculoDto;
import com.gamq.ambiente.dto.mapper.FotoVehiculoMapper;
import com.gamq.ambiente.exceptions.BlogAPIException;
import com.gamq.ambiente.model.FotoVehiculo;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.repository.FotoVehiculoRepository;
import com.gamq.ambiente.repository.VehiculoRepository;
import com.gamq.ambiente.service.FotoVehiculoService;
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
public class FotoVehiculoServiceImpl implements FotoVehiculoService {
    @Autowired
    private FotoVehiculoRepository fotoVehiculoRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private UploadFileService uploadFileService;

    @Value("${files.path}/fotos")
    private String filePath;

    @Override
    public FotoVehiculoDto obtenerFotoVehiculoPorUuid(String uuid) {
        Optional<FotoVehiculo> fotoVehiculoOptional = fotoVehiculoRepository.findByUuid(uuid);
        if(fotoVehiculoOptional.isPresent())
        {
            return FotoVehiculoMapper.toFotoVehiculoDto(fotoVehiculoOptional.get());
        }
        throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "el uuid de la Foto del Vehiculo no existe");
    }

    @Override
    public List<FotoVehiculoDto> obtenerFotoVehiculos() {
        List<FotoVehiculo> fotoVehiculoList = fotoVehiculoRepository.findAll();
        return fotoVehiculoList.stream().map( fotoVehiculo -> {
            return FotoVehiculoMapper.toFotoVehiculoDto(fotoVehiculo);
        }).collect(Collectors.toList());
    }

    @Override
    public FotoVehiculoDto crearFotoVehiculo(FotoVehiculoDto fotoVehiculoDto) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByUuid(fotoVehiculoDto.getVehiculoDto().getUuid());
        if(vehiculoOptional.isPresent()) {
            FotoVehiculo nuevoFotoVehiculo = FotoVehiculoMapper.toFotoVehiculo(fotoVehiculoDto);
            nuevoFotoVehiculo.setVehiculo(vehiculoOptional.get());
            try {
                String codigoUuid = UUID.randomUUID().toString();
                String filenombre = uploadFileService.saveFile(fotoVehiculoDto.getArchivoFile(), codigoUuid, filePath);
                nuevoFotoVehiculo.setRuta(filePath);
                nuevoFotoVehiculo.setNombre(filenombre);
                return FotoVehiculoMapper.toFotoVehiculoDto(fotoVehiculoRepository.save(nuevoFotoVehiculo));
            } catch (IOException ioException) {
                throw new BlogAPIException("500-INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "al cargar la foto del vehiculo");
            }
        }
        else
        {
            throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND,"el uuid  de vehiculo no existe");
        }
    }

    @Override
    public FotoVehiculoDto actualizarFotoVehiculo(FotoVehiculoDto fotoVehiculoDto) {
        FotoVehiculo fotoVehiculoQBE = new FotoVehiculo(fotoVehiculoDto.getUuid());
        Optional<FotoVehiculo> fotoVehiculoOptional = fotoVehiculoRepository.findOne(Example.of(fotoVehiculoQBE));
        if (fotoVehiculoOptional.isPresent()) {
            FotoVehiculo fotoVehiculo = FotoVehiculoMapper.toFotoVehiculo(fotoVehiculoDto);
            fotoVehiculo.setIdFotoVehiculo(fotoVehiculoOptional.get().getIdFotoVehiculo());
            fotoVehiculo.setVehiculo(fotoVehiculoOptional.get().getVehiculo());
            Vehiculo vehiculo = fotoVehiculoOptional.get().getVehiculo();
            try {
                boolean eliminado = uploadFileService.deleteFile(fotoVehiculoOptional.get().getNombre(), filePath);
                String codigoUuid = UUID.randomUUID().toString();
                String filename = uploadFileService.saveFile(fotoVehiculoDto.getArchivoFile(), codigoUuid, filePath);
                fotoVehiculo.setNombre(filename);
                fotoVehiculo.setRuta(filePath);
                fotoVehiculo.setVehiculo(vehiculo);
                return FotoVehiculoMapper.toFotoVehiculoDto(fotoVehiculoRepository.save(fotoVehiculo));
            } catch (IOException ioe) {
                throw new BlogAPIException("500-INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "no logro modificar el archivo");
            }
        }
        throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "no existe la foto del Vehiculo");
    }

    @Override
    public FotoVehiculoDto eliminarFotoVehiculo(String uuid) {
        FotoVehiculo fotoVehiculoQBE = new FotoVehiculo(uuid);
        Optional<FotoVehiculo> fotoVehiculoOptional = fotoVehiculoRepository.findOne(Example.of(fotoVehiculoQBE));
        if( fotoVehiculoOptional.isPresent()) {
            FotoVehiculo fotoVehiculo = fotoVehiculoOptional.get();
            try {
                boolean eliminado = uploadFileService.deleteFile(fotoVehiculo.getNombre(), filePath);
                fotoVehiculoRepository.delete(fotoVehiculo);
                return FotoVehiculoMapper.toFotoVehiculoDto(fotoVehiculo);
            } catch (IOException aa)
            {
                throw new BlogAPIException("500-INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR,"no logro eliminar la Foto del Vehiculo");
            }
        }
        throw new BlogAPIException("404-NOT_FOUND", HttpStatus.NOT_FOUND, "no existe la Foto del Vehiculo");
    }

    @Override
    public Resource descargarArchivo(String nombreFile) {
        return uploadFileService.loadFile(nombreFile,filePath);
    }
}
