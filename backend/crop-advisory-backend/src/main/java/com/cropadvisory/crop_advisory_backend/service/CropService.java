package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.Crop;
import com.cropadvisory.crop_advisory_backend.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {

    private final CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public Crop saveCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public Crop getCropById(int id) {
        return cropRepository.findById(id).orElse(null);
    }

    public void deleteCrop(int id) {
        cropRepository.deleteById(id);
    }
}