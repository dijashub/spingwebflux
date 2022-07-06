package com.maersk.containerservice.handler;


import com.maersk.containerservice.model.BookingRequest;
import com.maersk.containerservice.model.ContainerRequest;
import com.maersk.containerservice.service.impl.DefaultContainerService;
import com.maersk.containerservice.service.impl.DefaultValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.marker.ObjectAppendingMarker;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContainerHandler {

    private final DefaultContainerService defaultContainerService;

    private final DefaultValidationService defaultValidationService;

    public Mono<ServerResponse> checkAvailable(ServerRequest  request) {
        return
                request.bodyToMono(ContainerRequest.class)
                        .doOnSuccess(defaultValidationService::validate)
                        .doOnSuccess(containerRequest -> log.info(new ObjectAppendingMarker("request", containerRequest), "Incoming request to check available of the container"))
                        .flatMap(defaultContainerService::checkContainerAvailable)
                        .doOnSuccess(containerResponse -> log.info("Container available is obtained"))
                        .flatMap(containerResponse -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(containerResponse)));
    }

    public Mono<ServerResponse> bookContainer(ServerRequest request) {
        return
                request.bodyToMono(BookingRequest.class)
                        .doOnSuccess(defaultValidationService::validate)
                        .doOnSuccess(bookingRequest -> log.info(new ObjectAppendingMarker("request", bookingRequest), "Incoming request to create container booking"))
                        .flatMap(defaultContainerService::createContainerBooking)
                        .doOnSuccess(bookingResponse -> log.info("Container booking created successfully"))
                        .flatMap(bookingResponse -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(bookingResponse)));
    }
}
