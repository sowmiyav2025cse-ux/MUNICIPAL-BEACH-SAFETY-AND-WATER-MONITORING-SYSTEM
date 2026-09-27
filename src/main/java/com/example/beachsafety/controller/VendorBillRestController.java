package com.example.beachsafety.controller;

import com.example.beachsafety.dto.VendorBillRequest;
import com.example.beachsafety.entity.VendorBill;
import com.example.beachsafety.service.VendorBillService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-bills")
public class VendorBillRestController {

    private final VendorBillService billService;

    public VendorBillRestController(VendorBillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<VendorBill>> getAllVendorBills() {
        return ResponseEntity.ok(billService.getAllVendorBills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorBill> getVendorBillById(@PathVariable Long id) {
        return ResponseEntity.ok(billService.getVendorBillById(id));
    }

    @PostMapping
    public ResponseEntity<VendorBill> createVendorBill(@RequestBody VendorBillRequest request) {
        VendorBill created = billService.createVendorBill(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<VendorBill> payVendorBill(@PathVariable Long id) {
        VendorBill paid = billService.payVendorBill(id);
        return ResponseEntity.ok(paid);
    }
}
