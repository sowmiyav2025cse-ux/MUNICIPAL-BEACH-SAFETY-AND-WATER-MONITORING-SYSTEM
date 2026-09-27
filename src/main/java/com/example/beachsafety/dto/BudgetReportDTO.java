package com.example.beachsafety.dto;

public class BudgetReportDTO {
    private Long sectorId;
    private String beachZone;
    private String financialYear;
    private Double plannedBudget;
    private Double actualSpending;
    private Double variance;

    public BudgetReportDTO() {
    }

    public BudgetReportDTO(Long sectorId, String beachZone, String financialYear, Double plannedBudget, Double actualSpending, Double variance) {
        this.sectorId = sectorId;
        this.beachZone = beachZone;
        this.financialYear = financialYear;
        this.plannedBudget = plannedBudget;
        this.actualSpending = actualSpending;
        this.variance = variance;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
    }

    public String getBeachZone() {
        return beachZone;
    }

    public void setBeachZone(String beachZone) {
        this.beachZone = beachZone;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Double getPlannedBudget() {
        return plannedBudget;
    }

    public void setPlannedBudget(Double plannedBudget) {
        this.plannedBudget = plannedBudget;
    }

    public Double getActualSpending() {
        return actualSpending;
    }

    public void setActualSpending(Double actualSpending) {
        this.actualSpending = actualSpending;
    }

    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }
}
