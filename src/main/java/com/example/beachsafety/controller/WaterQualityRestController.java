package com.example.beachsafety.controller;

import com.example.beachsafety.dto.WaterQualitySampleRequest;
import com.example.beachsafety.entity.WaterQualitySample;
import com.example.beachsafety.service.WaterQualityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beach/samples")
public class WaterQualityRestController {

    private final WaterQualityService waterQualityService;

    public WaterQualityRestController(WaterQualityService waterQualityService) {
        this.waterQualityService = waterQualityService;
    }

    @GetMapping
    public ResponseEntity<List<WaterQualitySample>> getAllSamples() {
        return ResponseEntity.ok(waterQualityService.getAllSamples());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterQualitySample> getSampleById(@PathVariable Long id) {
        return ResponseEntity.ok(waterQualityService.getSampleById(id));
    }

    @GetMapping("/sector/{sectorId}")
    public ResponseEntity<List<WaterQualitySample>> getSamplesBySector(@PathVariable Long sectorId) {
        return ResponseEntity.ok(waterQualityService.getSamplesBySector(sectorId));
    }

    @PostMapping
    public ResponseEntity<WaterQualitySample> recordSample(@RequestBody WaterQualitySampleRequest request) {
        WaterQualitySample sample = waterQualityService.recordSample(request);
        return new ResponseEntity<>(sample, HttpStatus.CREATED);
    }
}
