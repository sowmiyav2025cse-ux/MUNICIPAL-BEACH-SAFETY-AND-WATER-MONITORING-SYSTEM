package com.example.beachsafety.controller;

import com.example.beachsafety.dto.CustomerInvoiceRequest;
import com.example.beachsafety.entity.CustomerInvoice;
import com.example.beachsafety.service.CustomerInvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-invoices")
public class CustomerInvoiceRestController {

    private final CustomerInvoiceService invoiceService;

    public CustomerInvoiceRestController(CustomerInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerInvoice>> getAllCustomerInvoices() {
        return ResponseEntity.ok(invoiceService.getAllCustomerInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerInvoice> getCustomerInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getCustomerInvoiceById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerInvoice> createCustomerInvoice(@RequestBody CustomerInvoiceRequest request) {
        CustomerInvoice created = invoiceService.createCustomerInvoice(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<CustomerInvoice> payCustomerInvoice(@PathVariable Long id) {
        CustomerInvoice paid = invoiceService.payCustomerInvoice(id);
        return ResponseEntity.ok(paid);
    }
}
