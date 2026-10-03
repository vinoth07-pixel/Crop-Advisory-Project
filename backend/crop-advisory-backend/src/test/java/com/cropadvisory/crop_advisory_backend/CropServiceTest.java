package com.cropadvisory.crop_advisory_backend;

import com.cropadvisory.crop_advisory_backend.entity.Crop;
import com.cropadvisory.crop_advisory_backend.repository.CropRepository;
import com.cropadvisory.crop_advisory_backend.service.CropService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CropServiceTest {

    @Mock
    private CropRepository cropRepository;

    @InjectMocks
    private CropService cropService;

    @Test
    void saveCropShouldSaveValidCrop() {

        Crop crop = new Crop();

        crop.setCropName("Rice");
        crop.setSeason("Kharif");
        crop.setSoilRequirement("Clay");
        crop.setDescription("Rice cultivation");

        when(cropRepository.save(crop))
                .thenReturn(crop);

        Crop result = cropService.saveCrop(crop);

        assertEquals("Rice", result.getCropName());

        verify(cropRepository).save(crop);
    }

    @Test
    void saveCropShouldRejectEmptyCropName() {

        Crop crop = new Crop();

        crop.setCropName("");

        assertThrows(
                IllegalArgumentException.class,
                () -> cropService.saveCrop(crop)
        );

        verify(cropRepository, never()).save(crop);
    }

    @Test
    void getCropByIdShouldReturnCrop() {

        Crop crop = new Crop();

        crop.setCropName("Rice");

        when(cropRepository.findById(1))
                .thenReturn(Optional.of(crop));

        Crop result = cropService.getCropById(1);

        assertNotNull(result);
        assertEquals("Rice", result.getCropName());

        verify(cropRepository).findById(1);
    }

    @Test
    void updateCropShouldUpdateValidCrop() {

        Crop existingCrop = new Crop();
        existingCrop.setCropName("Rice");

        Crop updatedCrop = new Crop();
        updatedCrop.setCropName("Wheat");
        updatedCrop.setSeason("Rabi");
        updatedCrop.setSoilRequirement("Loamy");
        updatedCrop.setDescription("Wheat cultivation");

        when(cropRepository.findById(1))
                .thenReturn(Optional.of(existingCrop));

        when(cropRepository.save(existingCrop))
                .thenReturn(existingCrop);

        Crop result =
                cropService.updateCrop(1, updatedCrop);

        assertEquals("Wheat", result.getCropName());
        assertEquals("Rabi", result.getSeason());

        verify(cropRepository).save(existingCrop);
    }

    @Test
    void updateCropShouldRejectEmptyCropName() {

        Crop crop = new Crop();

        crop.setCropName("");

        assertThrows(
                IllegalArgumentException.class,
                () -> cropService.updateCrop(1, crop)
        );

        verify(cropRepository, never()).save(any(Crop.class));
    }

    @Test
    void deleteCropShouldDeleteCrop() {

        cropService.deleteCrop(1);

        verify(cropRepository).deleteById(1);
    }
}
