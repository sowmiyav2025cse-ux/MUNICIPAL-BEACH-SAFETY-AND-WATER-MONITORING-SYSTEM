package com.example.beachsafety.repository;

import com.example.beachsafety.entity.BeachSector;
import com.example.beachsafety.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findBySector(BeachSector sector);
    Optional<Budget> findBySectorAndFinancialYear(BeachSector sector, String financialYear);

    @Query("SELECT COALESCE(SUM(b.variance), 0.0) FROM Budget b")
    Double sumTotalVariance();
}
