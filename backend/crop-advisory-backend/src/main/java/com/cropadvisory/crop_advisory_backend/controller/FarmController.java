package com.cropadvisory.crop_advisory_backend.controller;

import com.cropadvisory.crop_advisory_backend.entity.Farm;
import com.cropadvisory.crop_advisory_backend.service.FarmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    private final FarmService farmService;

    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }

    @PostMapping
    public Farm createFarm(@RequestBody Farm farm) {
        return farmService.saveFarm(farm);
    }

    @GetMapping
    public List<Farm> getAllFarms() {
        return farmService.getAllFarms();
    }

    @GetMapping("/{id}")
    public Farm getFarmById(@PathVariable int id) {
        return farmService.getFarmById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFarm(@PathVariable int id) {
        farmService.deleteFarm(id);
    }
}