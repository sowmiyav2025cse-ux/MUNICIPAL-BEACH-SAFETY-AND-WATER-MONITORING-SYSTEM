package com.example.beachsafety.repository;

import com.example.beachsafety.entity.OrderStatus;
import com.example.beachsafety.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByPoNumber(String poNumber);
    List<PurchaseOrder> findByStatus(OrderStatus status);
    List<PurchaseOrder> findAllByOrderByOrderDateDesc();
}
