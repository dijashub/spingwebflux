package com.maersk.containerservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ContainerServiceException extends ResponseStatusException {

    private final ErrorCodes errorCode;

    public ContainerServiceException(HttpStatus status, String reason, ErrorCodes errorCode) {
        super(status, reason);
        this.errorCode = errorCode;
    }
}
