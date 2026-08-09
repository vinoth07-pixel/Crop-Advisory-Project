package com.cropadvisory.crop_advisory_backend.repository;

import com.cropadvisory.crop_advisory_backend.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Integer> {
}