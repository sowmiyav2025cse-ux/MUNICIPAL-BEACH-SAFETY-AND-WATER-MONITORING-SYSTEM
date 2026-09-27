package com.example.beachsafety.repository;

import com.example.beachsafety.entity.BillStatus;
import com.example.beachsafety.entity.VendorBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorBillRepository extends JpaRepository<VendorBill, Long> {
    Optional<VendorBill> findByBillNumber(String billNumber);
    List<VendorBill> findByStatus(BillStatus status);
    List<VendorBill> findAllByOrderByBillDateDesc();
    long countByStatus(BillStatus status);

    @Query("SELECT COALESCE(SUM(b.amount), 0.0) FROM VendorBill b WHERE b.status = :status")
    Double sumAmountByStatus(BillStatus status);
}
