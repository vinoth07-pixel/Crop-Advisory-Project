package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.Farm;
import com.cropadvisory.crop_advisory_backend.repository.FarmRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public Farm saveFarm(Farm farm) {
        return farmRepository.save(farm);
    }

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public Farm getFarmById(int id) {
        return farmRepository.findById(id).orElse(null);
    }

    public Farm updateFarm(int id, Farm farm) {

        Farm existingFarm =
                farmRepository.findById(id).orElse(null);

        if (existingFarm == null) {
            return null;
        }

        existingFarm.setUser(farm.getUser());
        existingFarm.setLocation(farm.getLocation());
        existingFarm.setArea(farm.getArea());
        existingFarm.setSoilType(farm.getSoilType());

        return farmRepository.save(existingFarm);
    }

    public void deleteFarm(int id) {
        farmRepository.deleteById(id);
    }
}