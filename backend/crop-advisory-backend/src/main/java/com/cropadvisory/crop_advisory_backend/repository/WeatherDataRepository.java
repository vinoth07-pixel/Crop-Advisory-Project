
package com.cropadvisory.crop_advisory_backend.repository;

import com.cropadvisory.crop_advisory_backend.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Integer> {
}