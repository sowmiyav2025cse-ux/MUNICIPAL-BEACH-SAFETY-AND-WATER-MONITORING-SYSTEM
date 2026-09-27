package com.example.beachsafety.service;

import com.example.beachsafety.dto.SalesOrderRequest;
import com.example.beachsafety.entity.*;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.ContactRepository;
import com.example.beachsafety.repository.ProductRepository;
import com.example.beachsafety.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ContactRepository contactRepository;
    private final ProductRepository productRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository, ContactRepository contactRepository, ProductRepository productRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.contactRepository = contactRepository;
        this.productRepository = productRepository;
    }

    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAllByOrderByOrderDateDesc();
    }

    public SalesOrder getSalesOrderById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found with ID: " + id));
    }

    public SalesOrder createSalesOrder(SalesOrderRequest request) {
        Contact customer = contactRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer contact not found with ID: " + request.getCustomerId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.getProductId()));

        int qty = (request.getQuantity() != null && request.getQuantity() > 0) ? request.getQuantity() : 1;
        double price = (request.getUnitPrice() != null && request.getUnitPrice() > 0)
                ? request.getUnitPrice()
                : product.getUnitPrice();
        double total = qty * price;

        String soNum = request.getOrderNumber();
        if (soNum == null || soNum.trim().isEmpty()) {
            soNum = "SO-2026-" + String.format("%04d", salesOrderRepository.count() + 1);
        }

        SalesOrder so = new SalesOrder();
        so.setOrderNumber(soNum);
        so.setCustomer(customer);
        so.setProduct(product);
        so.setQuantity(qty);
        so.setUnitPrice(price);
        so.setTotalAmount(total);
        so.setOrderDate(request.getOrderDate() != null ? request.getOrderDate() : LocalDate.now());
        so.setStatus(OrderStatus.CREATED);

        return salesOrderRepository.save(so);
    }

    public SalesOrder updateStatus(Long id, OrderStatus status) {
        SalesOrder so = getSalesOrderById(id);
        so.setStatus(status);
        return salesOrderRepository.save(so);
    }
}
