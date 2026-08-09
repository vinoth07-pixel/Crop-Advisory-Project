package com.cropadvisory.crop_advisory_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crop")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cropId;

    private String cropName;

    private String season;

    private String soilRequirement;

    private String description;

    public Crop() {
    }

    public int getCropId() {
        return cropId;
    }

    public void setCropId(int cropId) {
        this.cropId = cropId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSoilRequirement() {
        return soilRequirement;
    }

    public void setSoilRequirement(String soilRequirement) {
        this.soilRequirement = soilRequirement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}