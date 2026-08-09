package com.cropadvisory.crop_advisory_backend.service;

import com.cropadvisory.crop_advisory_backend.entity.WeatherData;
import com.cropadvisory.crop_advisory_backend.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;

    public WeatherDataService(WeatherDataRepository weatherDataRepository) {
        this.weatherDataRepository = weatherDataRepository;
    }

    public WeatherData saveWeatherData(WeatherData weatherData) {
        return weatherDataRepository.save(weatherData);
    }

    public List<WeatherData> getAllWeatherData() {
        return weatherDataRepository.findAll();
    }

    public WeatherData getWeatherDataById(int id) {
        return weatherDataRepository.findById(id).orElse(null);
    }

    public void deleteWeatherData(int id) {
        weatherDataRepository.deleteById(id);
    }
}