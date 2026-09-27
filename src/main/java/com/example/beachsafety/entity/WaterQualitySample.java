package com.example.beachsafety.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "water_quality_samples")
public class WaterQualitySample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sector_id", nullable = false)
    private BeachSector sector;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "sample_date", nullable = false)
    private LocalDate sampleDate;

    @Column(name = "bacterial_count", nullable = false)
    private Double bacterialCount;

    @Column(name = "ph", nullable = false)
    private Double pH;

    @Column(name = "temperature", nullable = false)
    private Double temperature;

    @Column(name = "turbidity", nullable = false)
    private Double turbidity;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private WaterQualityStatus status;

    @Column(name = "remarks", length = 255)
    private String remarks;

    public WaterQualitySample() {
    }

    public WaterQualitySample(Long id, BeachSector sector, LocalDate sampleDate, Double bacterialCount, Double pH, Double temperature, Double turbidity, WaterQualityStatus status, String remarks) {
        this.id = id;
        this.sector = sector;
        this.sampleDate = sampleDate != null ? sampleDate : LocalDate.now();
        this.bacterialCount = bacterialCount;
        this.pH = pH;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.status = status;
        this.remarks = remarks;
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

    public LocalDate getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(LocalDate sampleDate) {
        this.sampleDate = sampleDate;
    }

    public Double getBacterialCount() {
        return bacterialCount;
    }

    public void setBacterialCount(Double bacterialCount) {
        this.bacterialCount = bacterialCount;
    }

    public Double getpH() {
        return pH;
    }

    public void setpH(Double pH) {
        this.pH = pH;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(Double turbidity) {
        this.turbidity = turbidity;
    }

    public WaterQualityStatus getStatus() {
        return status;
    }

    public void setStatus(WaterQualityStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterQualitySample that = (WaterQualitySample) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
