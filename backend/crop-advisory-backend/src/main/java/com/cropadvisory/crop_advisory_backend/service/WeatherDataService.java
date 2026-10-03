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

    public WeatherData updateWeatherData(
            int id,
            WeatherData weatherData) {

        WeatherData existingWeatherData =
                weatherDataRepository.findById(id).orElse(null);

        if (existingWeatherData == null) {
            return null;
        }

        existingWeatherData.setFarm(weatherData.getFarm());
        existingWeatherData.setTemperature(weatherData.getTemperature());
        existingWeatherData.setHumidity(weatherData.getHumidity());
        existingWeatherData.setRainfall(weatherData.getRainfall());
        existingWeatherData.setRecordedAt(weatherData.getRecordedAt());

        return weatherDataRepository.save(existingWeatherData);
    }

    public void deleteWeatherData(int id) {
        weatherDataRepository.deleteById(id);
    }
}