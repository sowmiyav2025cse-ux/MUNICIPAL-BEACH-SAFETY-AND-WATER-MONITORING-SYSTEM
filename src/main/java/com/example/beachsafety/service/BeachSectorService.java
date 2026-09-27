package com.example.beachsafety.service;

import com.example.beachsafety.entity.BeachSector;
import com.example.beachsafety.entity.WaterQualityStatus;
import com.example.beachsafety.exception.ResourceNotFoundException;
import com.example.beachsafety.repository.BeachSectorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BeachSectorService {

    private final BeachSectorRepository sectorRepository;

    public BeachSectorService(BeachSectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    public List<BeachSector> getAllSectors() {
        return sectorRepository.findAll();
    }

    public List<BeachSector> getActiveSectors() {
        return sectorRepository.findByActiveTrue();
    }

    public BeachSector getSectorById(Long id) {
        return sectorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beach sector not found with ID: " + id));
    }

    public BeachSector createSector(BeachSector sector) {
        if (sector.getSafetyThreshold() == null || sector.getSafetyThreshold() <= 0) {
            sector.setSafetyThreshold(100.0);
        }
        if (sector.getCurrentStatus() == null) {
            sector.setCurrentStatus(WaterQualityStatus.GREEN);
        }
        if (sector.getActive() == null) {
            sector.setActive(true);
        }
        return sectorRepository.save(sector);
    }

    public BeachSector updateSector(Long id, BeachSector updatedSector) {
        BeachSector existing = getSectorById(id);
        existing.setSectorName(updatedSector.getSectorName());
        existing.setLocation(updatedSector.getLocation());
        existing.setDescription(updatedSector.getDescription());
        if (updatedSector.getSafetyThreshold() != null) {
            existing.setSafetyThreshold(updatedSector.getSafetyThreshold());
        }
        if (updatedSector.getCurrentStatus() != null) {
            existing.setCurrentStatus(updatedSector.getCurrentStatus());
        }
        if (updatedSector.getActive() != null) {
            existing.setActive(updatedSector.getActive());
        }
        return sectorRepository.save(existing);
    }

    public void deleteSector(Long id) {
        BeachSector existing = getSectorById(id);
        sectorRepository.delete(existing);
    }

    public BeachSector updateSectorStatus(Long id, WaterQualityStatus newStatus) {
        BeachSector sector = getSectorById(id);
        sector.setCurrentStatus(newStatus);
        return sectorRepository.save(sector);
    }
}
