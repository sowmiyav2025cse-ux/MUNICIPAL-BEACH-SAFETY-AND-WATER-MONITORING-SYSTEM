package com.example.beachsafety.service;

import com.example.beachsafety.dto.WaterQualitySampleRequest;
import com.example.beachsafety.entity.BeachSector;
import com.example.beachsafety.entity.WaterQualitySample;
import com.example.beachsafety.entity.WaterQualityStatus;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.BeachSectorRepository;
import com.example.beachsafety.repository.WaterQualitySampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class WaterQualityService {

    private final WaterQualitySampleRepository sampleRepository;
    private final BeachSectorRepository sectorRepository;

    public WaterQualityService(WaterQualitySampleRepository sampleRepository, BeachSectorRepository sectorRepository) {
        this.sampleRepository = sampleRepository;
        this.sectorRepository = sectorRepository;
    }

    public List<WaterQualitySample> getAllSamples() {
        return sampleRepository.findAllByOrderBySampleDateDesc();
    }

    public WaterQualitySample getSampleById(Long id) {
        return sampleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Water quality sample not found with ID: " + id));
    }

    public List<WaterQualitySample> getSamplesBySector(Long sectorId) {
        BeachSector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new ResourceNotFoundException("Beach sector not found with ID: " + sectorId));
        return sampleRepository.findBySectorOrderBySampleDateDesc(sector);
    }

    /**
     * Core Business Logic:
     * Evaluates bacterial count against sector safety threshold.
     * IF bacterialCount <= threshold -> GREEN
     * IF bacterialCount > threshold AND bacterialCount <= threshold * 1.5 -> YELLOW
     * IF bacterialCount > threshold * 1.5 -> RED
     * Updates sector's current status automatically!
     */
    public WaterQualitySample recordSample(WaterQualitySampleRequest request) {
        BeachSector sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new ResourceNotFoundException("Beach sector not found with ID: " + request.getSectorId()));

        double threshold = (sector.getSafetyThreshold() != null && sector.getSafetyThreshold() > 0)
                ? sector.getSafetyThreshold()
                : 100.0;

        double count = request.getBacterialCount() != null ? request.getBacterialCount() : 0.0;
        WaterQualityStatus calculatedStatus;
        String defaultRemarks;

        if (count <= threshold) {
            calculatedStatus = WaterQualityStatus.GREEN;
            defaultRemarks = "Water Quality Safe";
        } else if (count <= threshold * 1.5) {
            calculatedStatus = WaterQualityStatus.YELLOW;
            defaultRemarks = "Warning: Water Quality Requires Monitoring";
        } else {
            calculatedStatus = WaterQualityStatus.RED;
            defaultRemarks = "RED ALERT: Unsafe Water Quality Detected";
        }

        // Update the beach sector's current status in the database
        sector.setCurrentStatus(calculatedStatus);
        sectorRepository.save(sector);

        WaterQualitySample sample = new WaterQualitySample();
        sample.setSector(sector);
        sample.setSampleDate(request.getSampleDate() != null ? request.getSampleDate() : LocalDate.now());
        sample.setBacterialCount(count);
        sample.setpH(request.getpH() != null ? request.getpH() : 7.0);
        sample.setTemperature(request.getTemperature() != null ? request.getTemperature() : 25.0);
        sample.setTurbidity(request.getTurbidity() != null ? request.getTurbidity() : 1.0);
        sample.setStatus(calculatedStatus);

        String remarks = request.getRemarks();
        if (remarks == null || remarks.trim().isEmpty()) {
            sample.setRemarks(defaultRemarks);
        } else {
            sample.setRemarks(remarks + " (" + defaultRemarks + ")");
        }

        return sampleRepository.save(sample);
    }
}
