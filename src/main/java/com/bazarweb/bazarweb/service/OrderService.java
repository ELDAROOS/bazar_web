package com.bazarweb.bazarweb.service;

import com.bazarweb.bazarweb.DTO.OrderDTO;
import com.bazarweb.bazarweb.enums.OrderStatus;
import com.bazarweb.bazarweb.exception.EmptyCartException;
import com.bazarweb.bazarweb.model.*;
import com.bazarweb.bazarweb.repository.CartRepository;
import com.bazarweb.bazarweb.repository.OrderRepository;
import com.bazarweb.bazarweb.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderService(CartRepository cartRepository, OrderRepository orderRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
    
    public List<OrderDTO> getUserOrders(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(u -> orderRepository.findByUser(u)
            .stream()
            .map(this::toOrderDto)
            .toList())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private OrderDTO toOrderDto(Order order) {
        return OrderDTO.builder()
            .id(order.getId())
            .date(order.getDate())
            .status(order.getStatus())
            .total(order.getTotal())
            .executed(order.isExecuted())
            .build();
    }

    public Optional<Order> getOrder(int id) {
        return orderRepository.findById(id);
    }

    public Page<Order> fetchFiltered(String executed, String orderAgeInDays, PageRequest request) {
        LocalDateTime startTime = LocalDateTime.now();
        if (!"all".equals(orderAgeInDays)) {
            int days = Integer.parseInt(orderAgeInDays);
            startTime = startTime.minusDays(days);
        }

        if (!"all".equals(executed) && !"all".equals(orderAgeInDays)) {
            boolean executedState = Boolean.parseBoolean(executed);
            return orderRepository.findByExecutedAndDateAfter(executedState, startTime, request);
        } else if (!"all".equals(executed)) {
            boolean executedState = Boolean.parseBoolean(executed);
            return orderRepository.findByExecuted(executedState, request);
        } else if (!"all".equals(orderAgeInDays)) {
            return orderRepository.findByDateAfter(startTime, request);
        } else {
            return orderRepository.findAll(request);
        }
    }

    public Order createUserOrder(String username, String cardNumber) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EmptyCartException("Cart is empty"));

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException("Cart is empty");
        }

        Order order = createNewOrder(user, cart);
        Bill bill = createBill(order, cardNumber);
        order.setBill(bill);
        orderRepository.save(order);

        fillOrderItems(cart, order);
        cartRepository.delete(cart);

        return order;
    }

    public void updateOrderStatus(int orderId, boolean executed) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setExecuted(executed);
        orderRepository.save(order);
    }

    private Order createNewOrder(User user, Cart cart) {
        return Order.builder()
                .user(user)
                .date(LocalDateTime.now())
                .status(OrderStatus.IN_PROGRESS)
                .total(cart.getTotalPrice())
                .orderItems(null) // Items will be filled later
                .build();
    }

    private Bill createBill(Order order, String cardNumber) {
        return Bill.builder()
                .order(order)
                .number(new Random().nextInt(999999999))
                .total(order.getTotal())
                .payed(true)
                .ccNumber(cardNumber)
                .date(order.getDate())
                .build();
    }

    private void fillOrderItems(Cart cart, Order order) {
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(item -> OrderItem.builder()
                        .order(order)
                        .product(item.getProduct())
                        .quantity(item.getQuantity())
                        .price(item.getProduct().getPrice())
                        .build())
                .toList();
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }
}
