package com.example.beachsafety.controller;

import com.example.beachsafety.dto.SalesOrderRequest;
import com.example.beachsafety.entity.OrderStatus;
import com.example.beachsafety.entity.SalesOrder;
import com.example.beachsafety.service.SalesOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales-orders")
public class SalesOrderRestController {

    private final SalesOrderService salesOrderService;

    public SalesOrderRestController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @GetMapping
    public ResponseEntity<List<SalesOrder>> getAllSalesOrders() {
        return ResponseEntity.ok(salesOrderService.getAllSalesOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrder> getSalesOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(salesOrderService.getSalesOrderById(id));
    }

    @PostMapping
    public ResponseEntity<SalesOrder> createSalesOrder(@RequestBody SalesOrderRequest request) {
        SalesOrder created = salesOrderService.createSalesOrder(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SalesOrder> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusPayload) {
        String statusStr = statusPayload.get("status");
        OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());
        SalesOrder updated = salesOrderService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}
