package com.cropadvisory.crop_advisory_backend.controller;

import com.cropadvisory.crop_advisory_backend.entity.AdvisoryRequest;
import com.cropadvisory.crop_advisory_backend.service.AdvisoryRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advisory-requests")
public class AdvisoryRequestController {

    private final AdvisoryRequestService advisoryRequestService;

    public AdvisoryRequestController(AdvisoryRequestService advisoryRequestService) {
        this.advisoryRequestService = advisoryRequestService;
    }

    @PostMapping
    public AdvisoryRequest createRequest(@RequestBody AdvisoryRequest request) {
        return advisoryRequestService.saveAdvisoryRequest(request);
    }

    @GetMapping
    public List<AdvisoryRequest> getAllRequests() {
        return advisoryRequestService.getAllAdvisoryRequests();
    }

    @GetMapping("/{id}")
    public AdvisoryRequest getRequestById(@PathVariable int id) {
        return advisoryRequestService.getAdvisoryRequestById(id);
    }
    @PutMapping("/{id}")
    public AdvisoryRequest updateRequest(
        @PathVariable int id,
        @RequestBody AdvisoryRequest request) {
      return advisoryRequestService.updateAdvisoryRequest(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable int id) {
        advisoryRequestService.deleteAdvisoryRequest(id);
    }
}