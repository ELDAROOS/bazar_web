package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
