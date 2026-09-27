package com.example.beachsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class WaterQualitySampleRequest {
    private Long sectorId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sampleDate;
    private Double bacterialCount;
    private Double pH;
    private Double temperature;
    private Double turbidity;
    private String remarks;

    public WaterQualitySampleRequest() {
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
