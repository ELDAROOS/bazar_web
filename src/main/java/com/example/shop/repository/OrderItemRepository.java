package com.example.shop.repository;

import com.example.shop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Address, Long> {

}
