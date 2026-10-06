package com.cropadvisory.crop_advisory_backend;

import com.cropadvisory.crop_advisory_backend.entity.Farm;
import com.cropadvisory.crop_advisory_backend.repository.FarmRepository;
import com.cropadvisory.crop_advisory_backend.service.FarmService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FarmServiceTest {

    @Mock
    private FarmRepository farmRepository;

    @InjectMocks
    private FarmService farmService;

    @Test
    void saveFarmShouldSaveValidFarm() {

        Farm farm = new Farm();

        farm.setLocation("Karaikudi");
        farm.setArea(2.5);
        farm.setSoilType("Clay");

        when(farmRepository.save(farm))
                .thenReturn(farm);

        Farm result = farmService.saveFarm(farm);

        assertEquals(2.5, result.getArea());
        assertEquals("Karaikudi", result.getLocation());

        verify(farmRepository).save(farm);
    }

    @Test
    void saveFarmShouldRejectZeroArea() {

        Farm farm = new Farm();

        farm.setArea(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> farmService.saveFarm(farm)
        );

        verify(farmRepository, never()).save(farm);
    }

    @Test
    void saveFarmShouldRejectNegativeArea() {

        Farm farm = new Farm();

        farm.setArea(-2);

        assertThrows(
                IllegalArgumentException.class,
                () -> farmService.saveFarm(farm)
        );

        verify(farmRepository, never()).save(farm);
    }

    @Test
    void getFarmByIdShouldReturnFarm() {

        Farm farm = new Farm();

        farm.setLocation("Karaikudi");
        farm.setArea(3.0);

        when(farmRepository.findById(1))
                .thenReturn(Optional.of(farm));

        Farm result = farmService.getFarmById(1);

        assertNotNull(result);
        assertEquals("Karaikudi", result.getLocation());
        assertEquals(3.0, result.getArea());

        verify(farmRepository).findById(1);
    }

    @Test
    void updateFarmShouldUpdateValidFarm() {

        Farm existingFarm = new Farm();

        existingFarm.setLocation("Karaikudi");
        existingFarm.setArea(2.0);
        existingFarm.setSoilType("Clay");

        Farm updatedFarm = new Farm();

        updatedFarm.setLocation("Madurai");
        updatedFarm.setArea(4.0);
        updatedFarm.setSoilType("Loamy");

        when(farmRepository.findById(1))
                .thenReturn(Optional.of(existingFarm));

        when(farmRepository.save(existingFarm))
                .thenReturn(existingFarm);

        Farm result =
                farmService.updateFarm(1, updatedFarm);

        assertEquals("Madurai", result.getLocation());
        assertEquals(4.0, result.getArea());
        assertEquals("Loamy", result.getSoilType());

        verify(farmRepository).save(existingFarm);
    }

    @Test
    void updateFarmShouldRejectInvalidArea() {

        Farm farm = new Farm();

        farm.setArea(0);

        assertThrows(
                IllegalArgumentException.class,
                () -> farmService.updateFarm(1, farm)
        );

        verify(farmRepository, never()).save(any(Farm.class));
    }

    @Test
    void deleteFarmShouldDeleteFarm() {

        farmService.deleteFarm(1);

        verify(farmRepository).deleteById(1);
    }
}
