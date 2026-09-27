package com.example.beachsafety.repository;

import com.example.beachsafety.entity.OrderStatus;
import com.example.beachsafety.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    Optional<SalesOrder> findByOrderNumber(String orderNumber);
    List<SalesOrder> findByStatus(OrderStatus status);
    List<SalesOrder> findAllByOrderByOrderDateDesc();
}
