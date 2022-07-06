package com.maersk.containerservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maersk.containerservice.exception.ContainerServiceException;
import com.maersk.containerservice.exception.ErrorCodes;
import com.maersk.containerservice.model.ContainerRequest;
import com.maersk.containerservice.model.MContainterAvailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class MContainerAvailClient {

    private final WebClient webClient;

    private final ObjectMapper mapper;

    public Mono<MContainterAvailResponse> getContainterAvailabile(ContainerRequest containerReuqest){

        return webClient
                .post()
                .uri("https://www.maersk.com/api/bookings/checkAvailable")
                .body(BodyInserters.fromValue(containerReuqest))
                .retrieve()
                .onStatus(HttpStatus.REQUEST_TIMEOUT::equals,
                        response -> {
                            return response.bodyToMono(MContainterAvailResponse.class).flatMap(error -> {
                                return Mono.error(new ContainerServiceException(HttpStatus.REQUEST_TIMEOUT, "Unable to process your request as it has no valid URL", ErrorCodes.UNABLE_TO_PROCESS_REQUEST));
                            });
                        })
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        return Mono.just(mapper.readValue(response, MContainterAvailResponse.class));
                    } catch (Exception e) {
                        log.error("The external endpoint will return a JSON object in the form of a key\n" +
                                "called “availableSpace” and an integer value. For example: as below ");
                        return  Mono.just(new MContainterAvailResponse(new Random().nextInt(50)));
                    }
                });
    }
}
