package com.maersk.containerservice.service.impl;


import com.maersk.containerservice.exception.ContainerServiceException;
import com.maersk.containerservice.exception.ErrorCodes;
import com.maersk.containerservice.model.*;
import com.maersk.containerservice.repository.ContainerRepository;
import com.maersk.containerservice.service.MContainerAvailClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultContainerService {

    @Autowired
    private final ContainerRepository containerRepository;

    private final MContainerAvailClient containerClient;

    public Mono<ContainerResponse> checkContainerAvailable(ContainerRequest containerRequest) {
       return containerClient.getContainterAvailabile(containerRequest)
               .onErrorMap(res -> {
                    return  new ContainerServiceException(HttpStatus.GATEWAY_TIMEOUT, "Unable to process as it is dummy URL", ErrorCodes.UNABLE_TO_PROCESS_REQUEST);})
               .flatMap(containerAvailRes -> {
                   return Mono.just(createContainerAvailReponse(containerAvailRes.getAvailableSpace()));
               });
    }

    public Mono<BookingResponse> createContainerBooking(BookingRequest bookingRequest) {
        return Mono.just(
                new BookingResponse(
                        containerRepository.save(ContainerBookingObj(bookingRequest))
                                .getId().toString()));
    }

    private ContainerBooking ContainerBookingObj(BookingRequest bookingRequest) {
        return ContainerBooking
                .builder()
                .Id((containerRepository.findMaxBookingCounter() == null ? 957000000 : containerRepository.findMaxBookingCounter() + 1))
                .containerType(bookingRequest.getContainerType())
                .origin(bookingRequest.getOrigin())
                .quantity(bookingRequest.getQuantity())
                .destination(bookingRequest.getDestination())
                .bookingDate(bookingRequest.getBookingDate())
                .build();
    }

    private ContainerResponse createContainerAvailReponse(Integer containerAvailSpace) {
        return ContainerResponse
                .builder()
                .isAvailable(containerAvailSpace > 0 ? true : false)
                .build();
    }
}
