package com.example.beachsafety.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class WaterQualityReportDTO {
    private Long id;
    private String sectorName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate sampleDate;
    private Double bacterialCount;
    private Double pH;
    private Double temperature;
    private Double turbidity;
    private String status;
    private String safetyAlertMessage;
    private String remarks;

    public WaterQualityReportDTO() {
    }

    public WaterQualityReportDTO(Long id, String sectorName, LocalDate sampleDate, Double bacterialCount, Double pH, Double temperature, Double turbidity, String status, String safetyAlertMessage, String remarks) {
        this.id = id;
        this.sectorName = sectorName;
        this.sampleDate = sampleDate;
        this.bacterialCount = bacterialCount;
        this.pH = pH;
        this.temperature = temperature;
        this.turbidity = turbidity;
        this.status = status;
        this.safetyAlertMessage = safetyAlertMessage;
        this.remarks = remarks;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSafetyAlertMessage() {
        return safetyAlertMessage;
    }

    public void setSafetyAlertMessage(String safetyAlertMessage) {
        this.safetyAlertMessage = safetyAlertMessage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
