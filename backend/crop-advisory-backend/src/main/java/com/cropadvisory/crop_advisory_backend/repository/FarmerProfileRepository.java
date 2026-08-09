package com.cropadvisory.crop_advisory_backend.repository;

import com.cropadvisory.crop_advisory_backend.entity.FarmerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerProfileRepository extends JpaRepository<FarmerProfile, Integer> {
}
