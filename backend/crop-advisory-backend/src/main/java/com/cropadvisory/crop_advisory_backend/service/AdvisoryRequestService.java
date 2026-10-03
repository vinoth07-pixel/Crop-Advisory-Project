package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.AdvisoryRequest;
import com.cropadvisory.crop_advisory_backend.repository.AdvisoryRequestRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdvisoryRequestService {

    private final AdvisoryRequestRepository advisoryRequestRepository;

    public AdvisoryRequestService(AdvisoryRequestRepository advisoryRequestRepository) {
        this.advisoryRequestRepository = advisoryRequestRepository;
    }

   public AdvisoryRequest saveAdvisoryRequest(AdvisoryRequest request) {

    if (request.getFarmer() == null) {
        throw new IllegalArgumentException("Farmer is required");
    }

    if (request.getCrop() == null) {
        throw new IllegalArgumentException("Crop is required");
    }

    if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
        throw new IllegalArgumentException("Question cannot be empty");
    }

    request.setStatus("PENDING");
    request.setCreatedAt(LocalDateTime.now());

    return advisoryRequestRepository.save(request);
   }

    public List<AdvisoryRequest> getAllAdvisoryRequests() {
        return advisoryRequestRepository.findAll();
    }

    public AdvisoryRequest getAdvisoryRequestById(int id) {
        return advisoryRequestRepository.findById(id).orElse(null);
    }

    public AdvisoryRequest updateAdvisoryRequest(
        int id,
        AdvisoryRequest request) {

    AdvisoryRequest existing =
            advisoryRequestRepository.findById(id).orElse(null);

    if (existing == null) {
        return null;
    }

    existing.setFarmer(request.getFarmer());
    existing.setCrop(request.getCrop());
    existing.setAdvisory(request.getAdvisory());
    existing.setQuestion(request.getQuestion());

    if (request.getAdvisory() != null) {
        existing.setStatus("RESOLVED");
    } else {
        existing.setStatus(request.getStatus());
    }

    return advisoryRequestRepository.save(existing);
   }
    public void deleteAdvisoryRequest(int id) {
        advisoryRequestRepository.deleteById(id);
    }
}