package com.bazarweb.bazarweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rating", nullable = false)
    private int rating; // Рейтинг (1-5)

    @Column(name = "comment", length = 1000)
    private String comment; // Текст отзыва

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Ссылка на товар

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Автор отзыва

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // Дата создания отзыва
}
