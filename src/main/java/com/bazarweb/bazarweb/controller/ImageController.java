package com.bazarweb.bazarweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bazarweb.bazarweb.model.Image;
import com.bazarweb.bazarweb.model.Product;
import com.bazarweb.bazarweb.repository.ImageRepository;
import com.bazarweb.bazarweb.repository.ProductRepository;
import com.bazarweb.bazarweb.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ImageController(ImageService imageService, ImageRepository imageRepository, ProductRepository productRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

        @GetMapping("/{id}")
        public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        try {
            Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found with id: " + id));

            Path filePath = Paths.get(imageService.getUploadDirectory(), image.getFilePath());
            byte[] fileContent = Files.readAllBytes(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(image.getContentType()));

            return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
        @RequestParam("file") MultipartFile file,
        @RequestParam("productId") int productId
    ) {
        try {
            // Проверка, существует ли продукт
            Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

            // Сохранение файла на диск
            String filePath = imageService.saveFile(file);

            // Сохранение данных в базе
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setFilePath(filePath);
            image.setContentType(file.getContentType());
            image.setProduct(product); // Связь с продуктом
            imageRepository.save(image);

            return ResponseEntity.ok("Image uploaded successfully: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
        }
    }

}
