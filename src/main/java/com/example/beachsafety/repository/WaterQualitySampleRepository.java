package com.example.beachsafety.repository;

import com.example.beachsafety.entity.BeachSector;
import com.example.beachsafety.entity.WaterQualitySample;
import com.example.beachsafety.entity.WaterQualityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterQualitySampleRepository extends JpaRepository<WaterQualitySample, Long> {
    List<WaterQualitySample> findBySectorOrderBySampleDateDesc(BeachSector sector);
    List<WaterQualitySample> findAllByOrderBySampleDateDesc();
    List<WaterQualitySample> findByStatus(WaterQualityStatus status);
}
