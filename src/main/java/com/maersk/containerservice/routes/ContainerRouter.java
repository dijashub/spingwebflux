package com.maersk.containerservice.routes;

import com.maersk.containerservice.handler.ContainerHandler;
import com.maersk.containerservice.model.BookingRequest;
import com.maersk.containerservice.model.BookingResponse;
import com.maersk.containerservice.model.ContainerRequest;
import com.maersk.containerservice.model.ContainerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
public class ContainerRouter {

    private static final String CONTAINER_ROUTE = "/container";

    private static final String AVAILABLE_PATH= "/available";

    private static final String BOOK_PATH = "/book";

    @RouterOperations({
            @RouterOperation(method = RequestMethod.POST, path = CONTAINER_ROUTE+AVAILABLE_PATH, operation = @Operation(tags = {"available"},
                    requestBody = @RequestBody(content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContainerRequest.class))),
                    operationId = "checkAvailable", summary = "This API will call an external service to check available space (doesn't exit for now)",
                    responses = {@ApiResponse(responseCode = "200", description = "Successfully operation", content = @Content(schema = @Schema(implementation = ContainerResponse.class))),
                    })),

            @RouterOperation(method = RequestMethod.POST, path = CONTAINER_ROUTE+BOOK_PATH, operation = @Operation(tags = {"book"},
                requestBody = @RequestBody(content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingRequest.class))),
                operationId = "bookContainer", summary = "This API will receive a booking request and store the data in a Cassandra database table for later processing by other systems.",
                responses = {@ApiResponse(responseCode = "200", description = "Successfully stored container booking", content = @Content(schema = @Schema(implementation = BookingResponse.class))),
                }))
    })


    @Bean
    public RouterFunction<ServerResponse> containerRoutes(ContainerHandler containerHandler){
        return RouterFunctions.nest(path(CONTAINER_ROUTE),
                        route(POST(AVAILABLE_PATH).and(accept(APPLICATION_JSON)), containerHandler::checkAvailable)
                                .andRoute(POST(BOOK_PATH).and(accept(APPLICATION_JSON)), containerHandler::bookContainer));
    }

}