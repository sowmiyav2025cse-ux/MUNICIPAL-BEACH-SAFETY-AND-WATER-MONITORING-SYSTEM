package com.example.beachsafety.repository;

import com.example.beachsafety.entity.BeachSector;
import com.example.beachsafety.entity.WaterQualityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeachSectorRepository extends JpaRepository<BeachSector, Long> {
    List<BeachSector> findByActiveTrue();
    long countByCurrentStatus(WaterQualityStatus status);
}
