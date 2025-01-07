package com.bazarweb.bazarweb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bill")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payed", nullable = false)
    private boolean payed;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "cc_number", nullable = false)
    @Pattern(regexp = "\\b(?:\\d[ -]*?){13,16}\\b")
    private String ccNumber;

    @Column(name = "date", nullable = false)
    @CreationTimestamp
    private LocalDateTime date;
}
