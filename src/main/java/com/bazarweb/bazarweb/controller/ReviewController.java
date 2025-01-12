package com.bazarweb.bazarweb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazarweb.bazarweb.DTO.ReviewDTO;
import com.bazarweb.bazarweb.model.Review;
import com.bazarweb.bazarweb.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProduct(@PathVariable int productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Review> addReview(
            @PathVariable int productId,
            @RequestBody ReviewDTO reviewRequestDto) {
    
        Review review = reviewService.addReview(productId, reviewRequestDto.getUserId(), reviewRequestDto.getRating(), reviewRequestDto.getComment());
        return ResponseEntity.ok(review);
    }
    
}
