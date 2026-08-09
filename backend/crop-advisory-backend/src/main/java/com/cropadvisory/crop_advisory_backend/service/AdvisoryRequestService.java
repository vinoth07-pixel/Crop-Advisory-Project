package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.AdvisoryRequest;
import com.cropadvisory.crop_advisory_backend.repository.AdvisoryRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvisoryRequestService {

    private final AdvisoryRequestRepository advisoryRequestRepository;

    public AdvisoryRequestService(AdvisoryRequestRepository advisoryRequestRepository) {
        this.advisoryRequestRepository = advisoryRequestRepository;
    }

    public AdvisoryRequest saveAdvisoryRequest(AdvisoryRequest request) {
        return advisoryRequestRepository.save(request);
    }

    public List<AdvisoryRequest> getAllAdvisoryRequests() {
        return advisoryRequestRepository.findAll();
    }

    public AdvisoryRequest getAdvisoryRequestById(int id) {
        return advisoryRequestRepository.findById(id).orElse(null);
    }

    public void deleteAdvisoryRequest(int id) {
        advisoryRequestRepository.deleteById(id);
    }
}