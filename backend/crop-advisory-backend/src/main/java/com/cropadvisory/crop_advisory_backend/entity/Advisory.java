package com.cropadvisory.crop_advisory_backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "advisory")
public class Advisory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int advisoryId;

    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @ManyToOne
    @JoinColumn(name = "officer_id", nullable = false)
    private User officer;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    public Advisory() {
    }

    public int getAdvisoryId() {
        return advisoryId;
    }

    public void setAdvisoryId(int advisoryId) {
        this.advisoryId = advisoryId;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public User getOfficer() {
        return officer;
    }

    public void setOfficer(User officer) {
        this.officer = officer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}