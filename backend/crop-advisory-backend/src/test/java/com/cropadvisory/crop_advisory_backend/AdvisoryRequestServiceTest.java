package com.cropadvisory.crop_advisory_backend;

import com.cropadvisory.crop_advisory_backend.entity.Advisory;
import com.cropadvisory.crop_advisory_backend.entity.AdvisoryRequest;
import com.cropadvisory.crop_advisory_backend.entity.Crop;
import com.cropadvisory.crop_advisory_backend.entity.User;
import com.cropadvisory.crop_advisory_backend.repository.AdvisoryRequestRepository;
import com.cropadvisory.crop_advisory_backend.service.AdvisoryRequestService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AdvisoryRequestServiceTest {

    @Mock
    private AdvisoryRequestRepository advisoryRequestRepository;

    @InjectMocks
    private AdvisoryRequestService advisoryRequestService;

    @Test
    void saveAdvisoryRequestShouldSetPendingStatus() {

        AdvisoryRequest request = new AdvisoryRequest();

        User farmer = new User();
        Crop crop = new Crop();

        request.setFarmer(farmer);
        request.setCrop(crop);
        request.setQuestion("What fertilizer should I use?");

        when(advisoryRequestRepository.save(request))
                .thenReturn(request);

        AdvisoryRequest result =
                advisoryRequestService.saveAdvisoryRequest(request);

        assertEquals("PENDING", result.getStatus());
        assertNotNull(result.getCreatedAt());

        verify(advisoryRequestRepository).save(request);
    }

    @Test
    void saveAdvisoryRequestShouldRejectEmptyQuestion() {

        AdvisoryRequest request = new AdvisoryRequest();

        User farmer = new User();
        Crop crop = new Crop();

        request.setFarmer(farmer);
        request.setCrop(crop);
        request.setQuestion("");

        assertThrows(
                IllegalArgumentException.class,
                () -> advisoryRequestService.saveAdvisoryRequest(request)
        );

        verify(advisoryRequestRepository, never()).save(request);
    }

    @Test
    void saveAdvisoryRequestShouldRejectMissingFarmer() {

        AdvisoryRequest request = new AdvisoryRequest();

        Crop crop = new Crop();

        request.setCrop(crop);
        request.setQuestion("Need advice");

        assertThrows(
                IllegalArgumentException.class,
                () -> advisoryRequestService.saveAdvisoryRequest(request)
        );

        verify(advisoryRequestRepository, never()).save(request);
    }

    @Test
    void saveAdvisoryRequestShouldRejectMissingCrop() {

        AdvisoryRequest request = new AdvisoryRequest();

        User farmer = new User();

        request.setFarmer(farmer);
        request.setQuestion("Need advice");

        assertThrows(
                IllegalArgumentException.class,
                () -> advisoryRequestService.saveAdvisoryRequest(request)
        );

        verify(advisoryRequestRepository, never()).save(request);
    }

    @Test
    void updateAdvisoryRequestShouldSetResolvedStatus() {

        int requestId = 1;

        AdvisoryRequest existingRequest = new AdvisoryRequest();

        User farmer = new User();
        Crop crop = new Crop();
        Advisory advisory = new Advisory();

        existingRequest.setFarmer(farmer);
        existingRequest.setCrop(crop);

        AdvisoryRequest request = new AdvisoryRequest();

        request.setFarmer(farmer);
        request.setCrop(crop);
        request.setAdvisory(advisory);
        request.setQuestion("What fertilizer should I use?");

        when(advisoryRequestRepository.findById(requestId))
                .thenReturn(Optional.of(existingRequest));

        when(advisoryRequestRepository.save(existingRequest))
                .thenReturn(existingRequest);

        AdvisoryRequest result =
                advisoryRequestService.updateAdvisoryRequest(
                        requestId,
                        request
                );

        assertEquals("RESOLVED", result.getStatus());
        assertEquals(advisory, result.getAdvisory());

        verify(advisoryRequestRepository).save(existingRequest);
    }
}