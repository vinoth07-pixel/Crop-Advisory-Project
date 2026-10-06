package com.cropadvisory.crop_advisory_backend;

import com.cropadvisory.crop_advisory_backend.entity.Farm;
import com.cropadvisory.crop_advisory_backend.entity.WeatherData;
import com.cropadvisory.crop_advisory_backend.repository.WeatherDataRepository;
import com.cropadvisory.crop_advisory_backend.service.WeatherDataService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WeatherDataServiceTest {

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private WeatherDataService weatherDataService;

    @Test
    void saveWeatherDataShouldSaveValidData() {

        WeatherData weatherData = new WeatherData();

        weatherData.setTemperature(30.5);
        weatherData.setHumidity(70);
        weatherData.setRainfall(10.5);
        weatherData.setRecordedAt(LocalDateTime.now());

        when(weatherDataRepository.save(weatherData))
                .thenReturn(weatherData);

        WeatherData result =
                weatherDataService.saveWeatherData(weatherData);

        assertEquals(30.5, result.getTemperature());
        assertEquals(70, result.getHumidity());

        verify(weatherDataRepository).save(weatherData);
    }

    @Test
    void saveWeatherDataShouldRejectNegativeHumidity() {

        WeatherData weatherData = new WeatherData();

        weatherData.setHumidity(-1);

        assertThrows(
                IllegalArgumentException.class,
                () -> weatherDataService.saveWeatherData(weatherData)
        );

        verify(weatherDataRepository, never())
                .save(weatherData);
    }

    @Test
    void saveWeatherDataShouldRejectHumidityAbove100() {

        WeatherData weatherData = new WeatherData();

        weatherData.setHumidity(101);

        assertThrows(
                IllegalArgumentException.class,
                () -> weatherDataService.saveWeatherData(weatherData)
        );

        verify(weatherDataRepository, never())
                .save(weatherData);
    }

    @Test
    void getWeatherDataByIdShouldReturnWeatherData() {

        WeatherData weatherData = new WeatherData();

        weatherData.setTemperature(28.5);
        weatherData.setHumidity(65);

        when(weatherDataRepository.findById(1))
                .thenReturn(Optional.of(weatherData));

        WeatherData result =
                weatherDataService.getWeatherDataById(1);

        assertNotNull(result);
        assertEquals(28.5, result.getTemperature());
        assertEquals(65, result.getHumidity());

        verify(weatherDataRepository).findById(1);
    }

    @Test
    void updateWeatherDataShouldUpdateValidData() {

        WeatherData existingWeatherData =
                new WeatherData();

        existingWeatherData.setTemperature(28);
        existingWeatherData.setHumidity(60);
        existingWeatherData.setRainfall(5);

        WeatherData updatedWeatherData =
                new WeatherData();

        updatedWeatherData.setTemperature(32);
        updatedWeatherData.setHumidity(75);
        updatedWeatherData.setRainfall(15);
        updatedWeatherData.setRecordedAt(LocalDateTime.now());

        when(weatherDataRepository.findById(1))
                .thenReturn(Optional.of(existingWeatherData));

        when(weatherDataRepository.save(existingWeatherData))
                .thenReturn(existingWeatherData);

        WeatherData result =
                weatherDataService.updateWeatherData(
                        1,
                        updatedWeatherData
                );

        assertEquals(32, result.getTemperature());
        assertEquals(75, result.getHumidity());
        assertEquals(15, result.getRainfall());

        verify(weatherDataRepository)
                .save(existingWeatherData);
    }

    @Test
    void updateWeatherDataShouldRejectInvalidHumidity() {

        WeatherData weatherData = new WeatherData();

        weatherData.setHumidity(101);

        assertThrows(
                IllegalArgumentException.class,
                () -> weatherDataService.updateWeatherData(
                        1,
                        weatherData
                )
        );

        verify(weatherDataRepository, never())
                .save(any(WeatherData.class));
    }

    @Test
    void deleteWeatherDataShouldDeleteWeatherData() {

        weatherDataService.deleteWeatherData(1);

        verify(weatherDataRepository).deleteById(1);
    }
}