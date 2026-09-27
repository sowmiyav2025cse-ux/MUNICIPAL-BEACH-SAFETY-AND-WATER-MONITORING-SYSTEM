package com.example.beachsafety.controller;

import com.example.beachsafety.entity.BeachSector;
import com.example.beachsafety.service.BeachSectorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beach/sectors")
public class BeachSectorRestController {

    private final BeachSectorService sectorService;

    public BeachSectorRestController(BeachSectorService sectorService) {
        this.sectorService = sectorService;
    }

    @GetMapping
    public ResponseEntity<List<BeachSector>> getAllSectors() {
        return ResponseEntity.ok(sectorService.getAllSectors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeachSector> getSectorById(@PathVariable Long id) {
        return ResponseEntity.ok(sectorService.getSectorById(id));
    }

    @PostMapping
    public ResponseEntity<BeachSector> createSector(@RequestBody BeachSector sector) {
        BeachSector created = sectorService.createSector(sector);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeachSector> updateSector(@PathVariable Long id, @RequestBody BeachSector sector) {
        return ResponseEntity.ok(sectorService.updateSector(id, sector));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        sectorService.deleteSector(id);
        return ResponseEntity.ok().build();
    }
}
