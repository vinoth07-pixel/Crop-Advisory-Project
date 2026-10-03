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

    if (crop.getCropName() == null ||
            crop.getCropName().trim().isEmpty()) {

        throw new IllegalArgumentException(
                "Crop name cannot be empty");
    }

    return cropRepository.save(crop);
   }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public Crop getCropById(int id) {
        return cropRepository.findById(id).orElse(null);
    }

    public Crop updateCrop(int id, Crop crop) {

    if (crop.getCropName() == null ||
            crop.getCropName().trim().isEmpty()) {

        throw new IllegalArgumentException(
                "Crop name cannot be empty");
    }

    Crop existingCrop =
            cropRepository.findById(id).orElse(null);

    if (existingCrop == null) {
        return null;
    }

    existingCrop.setCropName(crop.getCropName());
    existingCrop.setSeason(crop.getSeason());
    existingCrop.setSoilRequirement(crop.getSoilRequirement());
    existingCrop.setDescription(crop.getDescription());

    return cropRepository.save(existingCrop);
}

    public void deleteCrop(int id) {
        cropRepository.deleteById(id);
    }
}