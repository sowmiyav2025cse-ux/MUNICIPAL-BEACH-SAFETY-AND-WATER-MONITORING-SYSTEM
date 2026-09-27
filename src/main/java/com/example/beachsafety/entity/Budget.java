package com.example.beachsafety.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sector_id", nullable = false)
    private BeachSector sector;

    @Column(name = "financial_year", nullable = false, length = 20)
    private String financialYear;

    @Column(name = "planned_amount", nullable = false)
    private Double plannedAmount = 0.0;

    @Column(name = "actual_amount", nullable = false)
    private Double actualAmount = 0.0;

    @Column(name = "variance", nullable = false)
    private Double variance = 0.0;

    public Budget() {
    }

    public Budget(Long id, BeachSector sector, String financialYear, Double plannedAmount, Double actualAmount) {
        this.id = id;
        this.sector = sector;
        this.financialYear = financialYear;
        this.plannedAmount = plannedAmount != null ? plannedAmount : 0.0;
        this.actualAmount = actualAmount != null ? actualAmount : 0.0;
        this.variance = this.plannedAmount - this.actualAmount;
    }

    @PrePersist
    @PreUpdate
    public void calculateVariance() {
        if (plannedAmount == null) plannedAmount = 0.0;
        if (actualAmount == null) actualAmount = 0.0;
        this.variance = this.plannedAmount - this.actualAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BeachSector getSector() {
        return sector;
    }

    public void setSector(BeachSector sector) {
        this.sector = sector;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public Double getPlannedAmount() {
        return plannedAmount;
    }

    public void setPlannedAmount(Double plannedAmount) {
        this.plannedAmount = plannedAmount;
        calculateVariance();
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
        calculateVariance();
    }

    public Double getVariance() {
        return variance;
    }

    public void setVariance(Double variance) {
        this.variance = variance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Budget budget = (Budget) o;
        return Objects.equals(id, budget.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
