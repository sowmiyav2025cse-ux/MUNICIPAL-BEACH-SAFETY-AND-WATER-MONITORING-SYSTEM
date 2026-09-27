package com.example.beachsafety.controller;

import com.example.beachsafety.dto.FinancialTransactionRequest;
import com.example.beachsafety.entity.FinancialTransaction;
import com.example.beachsafety.entity.TransactionType;
import com.example.beachsafety.service.FinancialTransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class FinancialTransactionRestController {

    private final FinancialTransactionService transactionService;

    public FinancialTransactionRestController(FinancialTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<FinancialTransaction>> getAllTransactions(@RequestParam(required = false) TransactionType type) {
        if (type != null) {
            return ResponseEntity.ok(transactionService.getTransactionsByType(type));
        }
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    @PostMapping
    public ResponseEntity<FinancialTransaction> recordTransaction(@RequestBody FinancialTransactionRequest request) {
        FinancialTransaction tx = transactionService.recordTransaction(request);
        return new ResponseEntity<>(tx, HttpStatus.CREATED);
    }
}
