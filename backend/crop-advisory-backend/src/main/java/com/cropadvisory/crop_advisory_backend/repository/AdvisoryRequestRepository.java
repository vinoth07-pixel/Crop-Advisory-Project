package com.cropadvisory.crop_advisory_backend.repository;

import com.cropadvisory.crop_advisory_backend.entity.AdvisoryRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisoryRequestRepository extends JpaRepository<AdvisoryRequest, Integer> {
}