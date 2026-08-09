package com.cropadvisory.crop_advisory_backend.controller;

import com.cropadvisory.crop_advisory_backend.entity.FarmerProfile;
import com.cropadvisory.crop_advisory_backend.service.FarmerProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmer-profiles")
public class FarmerProfileController {

    private final FarmerProfileService farmerProfileService;

    public FarmerProfileController(FarmerProfileService farmerProfileService) {
        this.farmerProfileService = farmerProfileService;
    }

    @PostMapping
    public FarmerProfile createProfile(@RequestBody FarmerProfile profile) {
        return farmerProfileService.saveFarmerProfile(profile);
    }

    @GetMapping
    public List<FarmerProfile> getAllProfiles() {
        return farmerProfileService.getAllFarmerProfiles();
    }

    @GetMapping("/{id}")
    public FarmerProfile getProfileById(@PathVariable int id) {
        return farmerProfileService.getFarmerProfileById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable int id) {
        farmerProfileService.deleteFarmerProfile(id);
    }
}