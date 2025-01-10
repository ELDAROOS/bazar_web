package com.bazarweb.bazarweb.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bazarweb.bazarweb.DTO.OrderDTO;
import com.bazarweb.bazarweb.DTO.OrderRequestDto;
import com.bazarweb.bazarweb.exception.EmptyCartException;
import com.bazarweb.bazarweb.model.Order;
import com.bazarweb.bazarweb.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto request) {
        try {            
            Order order = orderService.createUserOrder(
                    request.getUsername(),
                    request.getCardNumber()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (IllegalArgumentException | EmptyCartException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getUserOrders(@RequestParam String username) {
        try {
            List<OrderDTO> orders = orderService.getUserOrders(username);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Получение информации о конкретном заказе
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderId) {
        return orderService.getOrder(orderId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Обновление статуса заказа (например, выполнен или нет)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable int orderId,
            @RequestParam boolean executed) {
        try {
            orderService.updateOrderStatus(orderId, executed);
            return ResponseEntity.ok("Order status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
    }

    // Получение списка заказов с фильтрацией
    @GetMapping
    public ResponseEntity<Page<Order>> fetchFilteredOrders(
            @RequestParam(defaultValue = "all") String executed,
            @RequestParam(defaultValue = "all") String orderAgeInDays,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Order> orders = orderService.fetchFiltered(executed, orderAgeInDays, pageRequest);
        return ResponseEntity.ok(orders);
    }
    
}
