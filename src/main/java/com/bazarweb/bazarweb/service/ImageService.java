package com.bazarweb.bazarweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    public String saveFile(MultipartFile file) throws IOException {
        // Проверка файла
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("File name is invalid");
        }

        // Ограничение типов файлов
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only image files are allowed.");
        }

        // Уникальное имя файла
        String sanitizedFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
        String fileName = UUID.randomUUID() + "_" + sanitizedFileName;
        Path filePath = Paths.get(uploadDirectory, fileName);

        try {
            // Создание директорий, если их нет
            Files.createDirectories(filePath.getParent());

            // Сохранение файла
            Files.write(filePath, file.getBytes());

            // Возврат относительного пути
            return fileName;
        } catch (IOException e) {
            throw e;
        }
    }

    public String getUploadDirectory() {
        return uploadDirectory;
    }    
}
