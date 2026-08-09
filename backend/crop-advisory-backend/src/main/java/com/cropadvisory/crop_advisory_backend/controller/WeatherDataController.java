package com.cropadvisory.crop_advisory_backend.controller;

import com.cropadvisory.crop_advisory_backend.entity.WeatherData;
import com.cropadvisory.crop_advisory_backend.service.WeatherDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    public WeatherDataController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @PostMapping
    public WeatherData createWeatherData(@RequestBody WeatherData weatherData) {
        return weatherDataService.saveWeatherData(weatherData);
    }

    @GetMapping
    public List<WeatherData> getAllWeatherData() {
        return weatherDataService.getAllWeatherData();
    }

    @GetMapping("/{id}")
    public WeatherData getWeatherDataById(@PathVariable int id) {
        return weatherDataService.getWeatherDataById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteWeatherData(@PathVariable int id) {
        weatherDataService.deleteWeatherData(id);
    }
}