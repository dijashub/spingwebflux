package com.maersk.containerservice.service;

import com.maersk.containerservice.model.BookingRequest;
import com.maersk.containerservice.model.ContainerBooking;
import com.maersk.containerservice.model.ContainerType;
import com.maersk.containerservice.repository.ContainerRepository;
import com.maersk.containerservice.service.impl.DefaultContainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultContainerServiceTest {

    @Mock
    private ContainerRepository containerRepository;

    @Mock
    private MContainerAvailClient mContainerAvailClient;

    private DefaultContainerService defaultContainerService;

    @BeforeEach
    public void setUp () {
        defaultContainerService = new DefaultContainerService(containerRepository, mContainerAvailClient);
    }

    @Test
    void testCreateBooking(){
        var containerBooking = BookingRequest.builder()
                        .containerType(ContainerType.REEFER.name())
                                .bookingDate(new Date())
                                        .destination("SOUTHHAMPTON")
                                                .quantity(100)
                                                    .containerSize(24)
                                                        .origin("PORTSMOUTH");

        when(containerRepository.findMaxBookingCounter()).thenReturn(5);

    }
}
