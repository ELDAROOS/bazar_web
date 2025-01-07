package com.bazarweb.bazarweb.repository;

import com.bazarweb.bazarweb.model.Order;
import com.bazarweb.bazarweb.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findById(int id);

    List<Order> findByUser(User user);

    List<Order> findByUserOrderByDateDesc(User user);

    @Query("SELECT o FROM Order o WHERE o.executed = :executed") // Поле executed, а не статус
    List<Order> findByExecuted(@Param("executed") boolean executed);

    @Query("SELECT o FROM Order o WHERE o.executed = :executed AND o.date > :date") // Поле date используется
    List<Order> findByExecutedAndDateAfter(@Param("executed") boolean executed, @Param("date") LocalDateTime date);

    @Query("SELECT o FROM Order o WHERE o.date > :date") // Поле date используется
    List<Order> findByDateAfter(@Param("date") LocalDateTime date);

    Page<Order> findByExecutedAndDateAfter(boolean executedState, LocalDateTime startTime, PageRequest request);

    Page<Order> findByExecuted(boolean executedState, PageRequest request);

    Page<Order> findByDateAfter(LocalDateTime startTime, PageRequest request);
}
