package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.Advisory;
import com.cropadvisory.crop_advisory_backend.repository.AdvisoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvisoryService {

    private final AdvisoryRepository advisoryRepository;

    public AdvisoryService(AdvisoryRepository advisoryRepository) {
        this.advisoryRepository = advisoryRepository;
    }

    public Advisory saveAdvisory(Advisory advisory) {
        return advisoryRepository.save(advisory);
    }

    public List<Advisory> getAllAdvisories() {
        return advisoryRepository.findAll();
    }

    public Advisory getAdvisoryById(int id) {
        return advisoryRepository.findById(id).orElse(null);
    }

    public Advisory updateAdvisory(
            int id,
            Advisory advisory) {

        Advisory existingAdvisory =
                advisoryRepository.findById(id).orElse(null);

        if (existingAdvisory == null) {
            return null;
        }

        existingAdvisory.setCrop(advisory.getCrop());
        existingAdvisory.setOfficer(advisory.getOfficer());
        existingAdvisory.setTitle(advisory.getTitle());
        existingAdvisory.setContent(advisory.getContent());
        existingAdvisory.setCreatedAt(advisory.getCreatedAt());

        return advisoryRepository.save(existingAdvisory);
    }

    public void deleteAdvisory(int id) {
        advisoryRepository.deleteById(id);
    }
}