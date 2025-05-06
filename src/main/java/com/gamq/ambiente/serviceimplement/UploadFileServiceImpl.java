package com.gamq.ambiente.serviceimplement;

import com.gamq.ambiente.service.UploadFileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadFileServiceImpl implements UploadFileService {
    @Override
    public String saveFile(MultipartFile multipartFile, String uuid, String rootPath)
            throws IOException {
        String nombreOriginalFile="";
        String fileName="";
        Path uploadPath = Paths.get(rootPath);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try {
            byte[] bytes = multipartFile.getBytes();
            //String extensionFile = multipartFile.getOriginalFilename().split("\\.")[1];
            //String extensionFile = multipartFile.getContentType().split("\\/")[1];
            String[] parts = multipartFile.getOriginalFilename().split("\\.");
            String extensionFile = parts[parts.length-1];
            nombreOriginalFile = multipartFile.getOriginalFilename().split("\\.")[0];
            String nuevoNombreArchivo = uuid; //+ '_' + this.getFileName() + '_' + nombreOriginalFile;
            fileName = nuevoNombreArchivo;
            nuevoNombreArchivo = '/' + nuevoNombreArchivo + '.' + extensionFile;
            Path rutaCompleta = Paths.get(uploadPath + nuevoNombreArchivo );
            Files.write(rutaCompleta, bytes);
            fileName = fileName + '.' + extensionFile;

        } catch (IOException ioe) {
            throw new IOException("no puede grabar el archivo: " + nombreOriginalFile, ioe);
        }
        return fileName;
    }

    @Override
    public boolean deleteFile(String nombreFile, String rootPath) throws IOException {
        Path uploadPath = Paths.get(rootPath);
        if (Files.exists(uploadPath)) {
            try {
                String nombreArchivo = '/' + nombreFile;
                Path rutaCompleta = Paths.get(uploadPath + nombreArchivo);
                Files.delete(rutaCompleta);
            } catch (IOException ioe) {
                throw new IOException("no puede eliminar el Archivo: " + nombreFile, ioe);
            }
            return true;
        }
        return false;
    }

    @Override
    public Resource loadFile(String fileName, String rootPath) {
        Path uploadPath = Paths.get(rootPath);
        try {
            String nombreArchivo = '/' + fileName;
            Path file = Paths.get(uploadPath + nombreArchivo);  //this.dirLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("No se puede leer el archivo");
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error: no se puede descargar el archivo " + e.getMessage());
        }
    }
}
