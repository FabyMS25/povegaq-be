package com.gamq.ambiente.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    String saveFile(MultipartFile multipartFile, String uuid, String rootPath) throws IOException;
    boolean deleteFile(String nombreFile, String rootPath) throws IOException;
    Resource loadFile(String fileName, String rootPath);
}
