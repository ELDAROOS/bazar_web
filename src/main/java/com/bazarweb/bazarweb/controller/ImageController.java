package com.bazarweb.bazarweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bazarweb.bazarweb.model.Image;
import com.bazarweb.bazarweb.repository.ImageRepository;
import com.bazarweb.bazarweb.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public ImageController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Сохранение файла на диск
            String filePath = imageService.saveFile(file);

            // Сохранение данных в базе
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setFilePath(filePath);
            image.setContentType(file.getContentType());
            imageRepository.save(image);

            return ResponseEntity.ok("Image uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }
}
