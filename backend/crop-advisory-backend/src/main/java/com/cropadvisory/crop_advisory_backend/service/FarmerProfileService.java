package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.FarmerProfile;
import com.cropadvisory.crop_advisory_backend.repository.FarmerProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmerProfileService {

    private final FarmerProfileRepository farmerProfileRepository;

    public FarmerProfileService(FarmerProfileRepository farmerProfileRepository) {
        this.farmerProfileRepository = farmerProfileRepository;
    }

    public FarmerProfile saveFarmerProfile(FarmerProfile profile) {
        return farmerProfileRepository.save(profile);
    }

    public List<FarmerProfile> getAllFarmerProfiles() {
        return farmerProfileRepository.findAll();
    }

    public FarmerProfile getFarmerProfileById(int id) {
        return farmerProfileRepository.findById(id).orElse(null);
    }

    public FarmerProfile updateFarmerProfile(
            int id,
            FarmerProfile profile) {

        FarmerProfile existingProfile =
                farmerProfileRepository.findById(id).orElse(null);

        if (existingProfile == null) {
            return null;
        }

        existingProfile.setUser(profile.getUser());
        existingProfile.setAddress(profile.getAddress());
        existingProfile.setExperience(profile.getExperience());

        return farmerProfileRepository.save(existingProfile);
    }

    public void deleteFarmerProfile(int id) {
        farmerProfileRepository.deleteById(id);
    }
}