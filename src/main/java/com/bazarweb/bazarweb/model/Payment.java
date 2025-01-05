package com.bazarweb.bazarweb.model;

import com.bazarweb.bazarweb.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private PaymentStatus status;
}
