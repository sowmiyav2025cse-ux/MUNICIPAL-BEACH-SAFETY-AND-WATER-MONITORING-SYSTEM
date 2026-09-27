package com.example.beachsafety.controller;

import com.example.beachsafety.dto.PurchaseOrderRequest;
import com.example.beachsafety.entity.OrderStatus;
import com.example.beachsafety.entity.PurchaseOrder;
import com.example.beachsafety.service.PurchaseOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderRestController {

    private final PurchaseOrderService poService;

    public PurchaseOrderRestController(PurchaseOrderService poService) {
        this.poService = poService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllPurchaseOrders() {
        return ResponseEntity.ok(poService.getAllPurchaseOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(poService.getPurchaseOrderById(id));
    }

    @PostMapping
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@RequestBody PurchaseOrderRequest request) {
        PurchaseOrder created = poService.createPurchaseOrder(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PurchaseOrder> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> statusPayload) {
        String statusStr = statusPayload.get("status");
        OrderStatus status = OrderStatus.valueOf(statusStr.toUpperCase());
        PurchaseOrder updated = poService.updateStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}
