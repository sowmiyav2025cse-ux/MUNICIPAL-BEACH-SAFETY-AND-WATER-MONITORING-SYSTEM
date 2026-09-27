package com.example.beachsafety.controller;

import com.example.beachsafety.dto.*;
import com.example.beachsafety.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService reportService;

    public ReportRestController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/water-quality")
    public ResponseEntity<List<WaterQualityReportDTO>> getWaterQualityReport() {
        return ResponseEntity.ok(reportService.getWaterQualityReport());
    }

    @GetMapping("/profit-loss")
    public ResponseEntity<ProfitLossReportDTO> getProfitLossReport() {
        return ResponseEntity.ok(reportService.getProfitLossReport());
    }

    @GetMapping("/budget")
    public ResponseEntity<List<BudgetReportDTO>> getBudgetReport() {
        return ResponseEntity.ok(reportService.getBudgetReport());
    }

    @GetMapping("/balance-sheet")
    public ResponseEntity<BalanceSheetReportDTO> getBalanceSheetReport() {
        return ResponseEntity.ok(reportService.getBalanceSheetReport());
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(reportService.getDashboardStats());
    }
}
