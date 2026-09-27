package com.example.beachsafety.service;

import com.example.beachsafety.dto.PurchaseOrderRequest;
import com.example.beachsafety.entity.*;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.ContactRepository;
import com.example.beachsafety.repository.ProductRepository;
import com.example.beachsafety.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PurchaseOrderService {

    private final PurchaseOrderRepository poRepository;
    private final ContactRepository contactRepository;
    private final ProductRepository productRepository;

    public PurchaseOrderService(PurchaseOrderRepository poRepository, ContactRepository contactRepository, ProductRepository productRepository) {
        this.poRepository = poRepository;
        this.contactRepository = contactRepository;
        this.productRepository = productRepository;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return poRepository.findAllByOrderByOrderDateDesc();
    }

    public PurchaseOrder getPurchaseOrderById(Long id) {
        return poRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found with ID: " + id));
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrderRequest request) {
        Contact vendor = contactRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor contact not found with ID: " + request.getVendorId()));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.getProductId()));

        int qty = (request.getQuantity() != null && request.getQuantity() > 0) ? request.getQuantity() : 1;
        double price = (request.getUnitPrice() != null && request.getUnitPrice() > 0)
                ? request.getUnitPrice()
                : product.getUnitPrice();
        double total = qty * price;

        String poNum = request.getPoNumber();
        if (poNum == null || poNum.trim().isEmpty()) {
            poNum = "PO-2026-" + String.format("%04d", poRepository.count() + 1);
        }

        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(poNum);
        po.setVendor(vendor);
        po.setProduct(product);
        po.setQuantity(qty);
        po.setUnitPrice(price);
        po.setTotalAmount(total);
        po.setOrderDate(request.getOrderDate() != null ? request.getOrderDate() : LocalDate.now());
        po.setStatus(OrderStatus.CREATED);

        return poRepository.save(po);
    }

    public PurchaseOrder updateStatus(Long id, OrderStatus status) {
        PurchaseOrder po = getPurchaseOrderById(id);
        po.setStatus(status);
        return poRepository.save(po);
    }
}
