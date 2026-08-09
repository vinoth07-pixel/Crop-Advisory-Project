package com.cropadvisory.crop_advisory_backend.repository;

import com.cropadvisory.crop_advisory_backend.entity.Advisory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvisoryRepository extends JpaRepository<Advisory, Integer> {
}