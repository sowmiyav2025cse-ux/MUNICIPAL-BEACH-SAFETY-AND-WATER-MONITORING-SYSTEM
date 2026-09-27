package com.example.beachsafety.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "beach_sectors")
public class BeachSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sector_name", nullable = false, length = 100)
    private String sectorName;

    @Column(name = "location", length = 150)
    private String location;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "safety_threshold", nullable = false)
    private Double safetyThreshold = 100.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false, length = 20)
    private WaterQualityStatus currentStatus = WaterQualityStatus.GREEN;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    public BeachSector() {
    }

    public BeachSector(Long id, String sectorName, String location, String description, Double safetyThreshold, WaterQualityStatus currentStatus, Boolean active) {
        this.id = id;
        this.sectorName = sectorName;
        this.location = location;
        this.description = description;
        this.safetyThreshold = safetyThreshold;
        this.currentStatus = currentStatus != null ? currentStatus : WaterQualityStatus.GREEN;
        this.active = active != null ? active : true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSafetyThreshold() {
        return safetyThreshold;
    }

    public void setSafetyThreshold(Double safetyThreshold) {
        this.safetyThreshold = safetyThreshold;
    }

    public WaterQualityStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(WaterQualityStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeachSector that = (BeachSector) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
