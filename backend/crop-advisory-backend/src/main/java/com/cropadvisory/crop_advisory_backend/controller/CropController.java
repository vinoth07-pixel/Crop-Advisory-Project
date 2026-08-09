package com.cropadvisory.crop_advisory_backend.controller;

import com.cropadvisory.crop_advisory_backend.entity.Crop;
import com.cropadvisory.crop_advisory_backend.service.CropService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @PostMapping
    public Crop createCrop(@RequestBody Crop crop) {
        return cropService.saveCrop(crop);
    }

    @GetMapping
    public List<Crop> getAllCrops() {
        return cropService.getAllCrops();
    }

    @GetMapping("/{id}")
    public Crop getCropById(@PathVariable int id) {
        return cropService.getCropById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCrop(@PathVariable int id) {
        cropService.deleteCrop(id);
    }
}