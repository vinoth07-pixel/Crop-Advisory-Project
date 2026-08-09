package com.cropadvisory.crop_advisory_backend.controller;

import com.cropadvisory.crop_advisory_backend.entity.Advisory;
import com.cropadvisory.crop_advisory_backend.service.AdvisoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advisories")
public class AdvisoryController {

    private final AdvisoryService advisoryService;

    public AdvisoryController(AdvisoryService advisoryService) {
        this.advisoryService = advisoryService;
    }

    @PostMapping
    public Advisory createAdvisory(@RequestBody Advisory advisory) {
        return advisoryService.saveAdvisory(advisory);
    }

    @GetMapping
    public List<Advisory> getAllAdvisories() {
        return advisoryService.getAllAdvisories();
    }

    @GetMapping("/{id}")
    public Advisory getAdvisoryById(@PathVariable int id) {
        return advisoryService.getAdvisoryById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAdvisory(@PathVariable int id) {
        advisoryService.deleteAdvisory(id);
    }
}