package com.example.beachsafety.controller;

import com.example.beachsafety.dto.BudgetRequest;
import com.example.beachsafety.entity.Budget;
import com.example.beachsafety.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetRestController {

    private final BudgetService budgetService;

    public BudgetRestController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public ResponseEntity<List<Budget>> getAllBudgets() {
        return ResponseEntity.ok(budgetService.getAllBudgets());
    }

    @GetMapping("/sector/{sectorId}")
    public ResponseEntity<List<Budget>> getBudgetsBySector(@PathVariable Long sectorId) {
        return ResponseEntity.ok(budgetService.getBudgetsBySectorId(sectorId));
    }

    @PostMapping
    public ResponseEntity<Budget> createOrUpdateBudget(@RequestBody BudgetRequest request) {
        Budget saved = budgetService.createOrUpdateBudget(request);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
