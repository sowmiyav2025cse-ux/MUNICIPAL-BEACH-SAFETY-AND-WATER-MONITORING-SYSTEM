package com.example.beachsafety.service;

import com.example.beachsafety.dto.BudgetRequest;
import com.example.beachsafety.entity.BeachSector;
import com.example.beachsafety.entity.Budget;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.BeachSectorRepository;
import com.example.beachsafety.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final BeachSectorRepository sectorRepository;

    public BudgetService(BudgetRepository budgetRepository, BeachSectorRepository sectorRepository) {
        this.budgetRepository = budgetRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public List<Budget> getBudgetsBySectorId(Long sectorId) {
        BeachSector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFoundException("Beach sector not found with ID: " + sectorId));
        return budgetRepository.findBySector(sector);
    }

    public Budget createOrUpdateBudget(BudgetRequest request) {
        BeachSector sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Beach sector not found with ID: " + request.getSectorId()));

        String fy = (request.getFinancialYear() != null && !request.getFinancialYear().trim().isEmpty())
                ? request.getFinancialYear()
                : "2026-2027";

        Budget budget = budgetRepository.findBySectorAndFinancialYear(sector, fy)
                .orElse(new Budget());

        budget.setSector(sector);
        budget.setFinancialYear(fy);
        if (request.getPlannedAmount() != null) {
            budget.setPlannedAmount(request.getPlannedAmount());
        }
        if (request.getActualAmount() != null) {
            budget.setActualAmount(request.getActualAmount());
        }
        budget.calculateVariance();

        return budgetRepository.save(budget);
    }
}
