package com.bazarweb.bazarweb.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bazarweb.bazarweb.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private int id;
    private LocalDateTime date;
    private OrderStatus status;
    private BigDecimal total;
    private boolean executed;
}
